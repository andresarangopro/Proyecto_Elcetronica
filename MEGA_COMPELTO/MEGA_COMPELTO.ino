//=========================================
//== INCLUDES
//=========================================

/**RFID**/
#include <SPI.h>
#include <MFRC522.h>
/**LCD**/
#include <Wire.h> 
#include <LiquidCrystal_I2C.h>
/**SERVO**/
#include <Servo.h> 
/**SENSOR PIR**/
#include "SensorMovimiento.h"
/**SENSOR TEMPERATURA - HUMEDAD**/
#include <DHT.h>
/**Objects an ouput**/
#include "DigitalWObject.h"
/**BLUETOOTH**/
#include "ManagementBluetooth.h"
/**TFT**/
#include "TftMine.h"

//=========================================
//== DEFINES
//=========================================
/**RFID**/
#define SS_PIN 53
#define RST_PIN 49
/**SERVO**/
#define SERVO_PIN 44
/**SENSOR PIR**/
#define SENSOR_MOVIMIENTO_UNO 41//4
#define SENSOR_MOVIMIENTO_DOS 5
/**SENSOR TEMPERATURA - HUMEDAD**/
#define SENSOR_TEMPERATURA 23//46
#define DHTTYPE DHT11
/**BUZZER**/
#define BUZZER 45//48
/**LEDS**/
#define LED_SALA 22//47
#define LED_CUARTO_UNO 24//47
#define LED_CUARTO_DOS 26//47
#define LED_CUARTO_TRES 28//47
#define LED_COCINA 30//47
/**COOLER**/
#define COOLER_UNO 49

//=========================================
//== VARIABLES
//=========================================
/**RFID**/
int static KEY_MASTER_CARD[] = {131,221,251,36}; //This is the stored UID
int static KEY_MASTER_CARD2[] = {194,104,236,115};
const int arrayKeys[][4] = {{131,221,251,36},{194,104,236,115}};
int filasKeys = 2;
int columnasKeys = 4;
MFRC522 rfid(SS_PIN, RST_PIN); // Instance of the class
/**LCD**/
LiquidCrystal_I2C lcd(0x3F,16,2);  //
/**BLUETOOTH**/
ManagementBluetooth bluetooth(10,11);
/**SERVO**/
Servo ServoPuerta; 
int static CERO_GRADES = 0;
int static DELAY_UNTIL_OPEN_DOOR = 5000;
/**CLASSES VARS**/
uint8_t static ENCENDIDO = HIGH;
uint8_t static APAGADO = LOW;
/**SENSOR_PIR**/
SensorMovimiento sensorUno(HIGH,"SENSOR UNO",SENSOR_MOVIMIENTO_UNO,0);
SensorMovimiento sensorDos(HIGH,"SENSOR DOS",SENSOR_MOVIMIENTO_DOS,0);
/**SENSOR TEMPERATURA - HUMEDAD**/
DHT dht(SENSOR_TEMPERATURA, DHTTYPE);
/**BUZZER**/
DigitalWObject buzzer(BUZZER, APAGADO, "BUZZER");
/**COOLER**/
DigitalWObject cooler(COOLER_UNO, APAGADO, "COOLER_UNO");
/**LEDS**/
DigitalWObject led_cocinaD(LED_COCINA,APAGADO, "LED COCINA");
DigitalWObject led_salaD(LED_SALA,APAGADO, "LED SALA");
DigitalWObject led_cuartoUno(LED_CUARTO_UNO,APAGADO, "LED CUARTO UNO");
DigitalWObject led_cuartoDos(LED_CUARTO_DOS,APAGADO, "LED CUARTO DOS");
DigitalWObject led_cuartoTres(LED_CUARTO_TRES,APAGADO, "LED CUARTO TRES");
/**TFT**/
TftMine tft_s(led_cocinaD,led_salaD,led_cuartoUno, led_cuartoDos, led_cuartoTres);
boolean paint = true;


int state;
int alarmCount =1;
char c;
char varCharBluetooth;    
//=========================================
//== SETUP PROYECT
//=========================================

void setup() {
    Serial.begin(9600);
    
    SPI.begin(); // Init SPI bus
    bluetooth.beginT();
    tft_s.initTFT();
    initRFID();
    initi2cLCD();  
    initServo();
    dht.begin();  
}
//=========================================
//== LOOP PROYECT
//=========================================

void loop() {
   readCardRFID();  
   varCharBluetooth= bluetooth.readT();
   bluetooth.connectB(varCharBluetooth,led_salaD,led_cuartoUno,led_cuartoDos,led_cuartoTres,led_cocinaD);
   if(paint){
     tft_s.funMenu();
     paint = false;
   }
  if(tft_s.pressure(20, 220, 70, 120)){
        tft_s.light();
   }   
   // tft_s.funTFT();
  
   if(sensorUno.activacionSensor()){
    Serial.println(sensorUno.getIdSensor()+" ACTIVADO");
    float temperatura= readTemperatureAsCelcius(dht);
    bluetooth.updatePersonaAndTemp("0", temperatura);
   }
   //sensorDos.getStateOnce(); 
  
}

//=========================================
//==RFID MODULO 
//=========================================

void initRFID(){
    rfid.PCD_Init(); // Init MFRC522 
    Serial.println("block"); 
}

void readCardRFID(){
    if (rfid.PICC_IsNewCardPresent()){ 
      MFRC522::Uid cardSerialReadVar = readSerialCardRFID();
      String serialCard = serialCardToString(cardSerialReadVar);
      printSerialCard(serialCard);
      printSerialCardOnLCD(serialCard);
      boolean isMasterKey = compareCardReadWithMasterCard(cardSerialReadVar);
      openDoor(isMasterKey,serialCard);
    } 
}

MFRC522::Uid readSerialCardRFID(){    
    if ( rfid.PICC_ReadCardSerial()){
      rfid.PICC_HaltA();         
    } 
    return rfid.uid; 
}

String serialCardToString(MFRC522::Uid cardSerial){
  String a,b, uidR;
  for (byte i = 0; i < cardSerial.size; i++) { 
     a = cardSerial.uidByte[i] < 0x10 ? " 0" : " ";
     b = cardSerial.uidByte[i], HEX;   
     uidR += b+a;           
  } 
  return uidR;
}

boolean compareCardReadWithMasterCard(MFRC522::Uid cardSerial){
    boolean match = true;
  //  for (byte i = 0; i < cardSerial.size; i++) {  
    for(int i =0; i < filasKeys; i++){      
      match = true;
      for(int j =0; j < columnasKeys; j++){   
        if(!(cardSerial.uidByte[j] == arrayKeys[i][j])){      
          match = false;
        }  
      }
      if(match)return match;
     }
    return match;
}

//=========================================
//== I2C-LCD  16x2
//=========================================

void initi2cLCD(){
  // Inicializar el LCD
  lcd.init();
  //Encender la luz de fondo.
  lcd.backlight();   
  lcd.setCursor(0, 0); 
  lcd.print("block"); 
}

void printSerialCardOnLCD(String cardSerial){
   lcd.setCursor(0, 0);
   lcd.print("Card UID: ");   
   lcd.setCursor(0, 1);
   lcd.print(cardSerial);
 
}

//=========================================
//== SERVOMOTOR
//=========================================

void initServo(){  
  ServoPuerta.attach(SERVO_PIN); 
}

void moveServo(int grade){
   ServoPuerta.write(grade); 
   delay(DELAY_UNTIL_OPEN_DOOR);    
   ServoPuerta.write(90); 
}

void openDoor(int correctMasterKey,String cardSerialReadVar){
  if(correctMasterKey){
     bluetooth.sendKeyPersona(cardSerialReadVar);    
     moveServo(180); 
  }else if(alarmCount >= 3){       
    buzzer.setEstado(ENCENDIDO);
    bluetooth.insertAlarm();
    delay(2000);
    buzzer.setEstado(APAGADO);
    alarmCount = 0;
  }else{ 
     alarmCount++;
  }
}

//=========================================
//== TEMPERATURA DTH11
//=========================================

float readHumidity(DHT dht){
  return dht.readHumidity();
}

float readTemperatureAsCelcius(DHT dht){
  delay(10);
  return dht.readTemperature();
}

float readTemperatureAsFarenheit(DHT dht){
  return dht.readTemperature(true);
}

float heatIndex(DHT dht, float farenheit, float humidity){
  return dht.computeHeatIndex(farenheit, humidity);
}

boolean checkRead(float value){
  return isnan(value) ? false : true;
}

//=========================================
//== UTILS
//=========================================


void printSerialCard(String cardSerial){   
    Serial.println("Card UID: "+ cardSerial);
}
