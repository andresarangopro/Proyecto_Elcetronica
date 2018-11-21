//=========================================
//== INCLUDES
//=========================================
#include <SoftwareSerial.h>
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
#define SENSOR_MOVIMIENTO_UNO 38
#define SENSOR_MOVIMIENTO_DOS 34
#define SENSOR_MOVIMIENTO_COCINA 40
#define SENSOR_MOVIMIENTO_TRES 36
/**SENSOR TEMPERATURA - HUMEDAD**/
#define SENSOR_TEMPERATURA 23//46
#define DHTTYPE DHT11
/**BUZZER**/
#define BUZZER 45//48
/**LEDS**/
#define LED_SALA 22
#define LED_CUARTO_UNO 24
#define LED_CUARTO_DOS 26
#define LED_CUARTO_TRES 28
#define LED_COCINA 30
/**COOLER**/
#define COOLER_UNO 49
/**VENTILADOR**/
#define FAN_UNO 32
#define FAN_DOS 33
/**CELDA**/
#define CELDA_PIN 35

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
SensorMovimiento sensorUno(LOW,"SENSOR UNO",SENSOR_MOVIMIENTO_UNO,0);
SensorMovimiento sensorDos(LOW,"SENSOR DOS",SENSOR_MOVIMIENTO_DOS,0);
SensorMovimiento sensorTres(LOW,"SENSOR TRES",SENSOR_MOVIMIENTO_TRES,0);
SensorMovimiento sensorCocina(LOW,"SENSOR COCINA",SENSOR_MOVIMIENTO_COCINA,0);
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
/**VENTILADORES**/
DigitalWObject fan_uno(FAN_UNO, APAGADO, "VENTILADOR UNO");
DigitalWObject fan_dos (FAN_DOS, APAGADO, "VENTILADOR UNO");
/**CELDA**/
DigitalWObject celda(CELDA_PIN, APAGADO, "CELDA");
/**TFT**/
TftMine tft_s(led_cocinaD,led_salaD,led_cuartoUno, led_cuartoDos, led_cuartoTres, fan_uno, fan_dos,celda);
boolean paint = true;


int state;
int alarmCount =1;
char c;
char varCharBluetooth;  

uint8_t pirUno = LOW;
uint8_t pirDos = LOW;
uint8_t pirTres = LOW;
uint8_t pirCuatro = LOW;


boolean readPirUno = true;
boolean readPirDos = true;
boolean readPirTres = true;
boolean readPirCuatro = true;
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
   bluetooth.connectB(varCharBluetooth,led_salaD,led_cuartoUno,led_cuartoDos,led_cuartoTres,led_cocinaD,buzzer,fan_uno, fan_dos, celda);
   if(tft_s.entroMenu){
     tft_s.funMenu();
     tft_s.setState(false);
   }
   if(tft_s.pressure(20, 220, 70, 120)){
        tft_s.light();
        tft_s.setStateFan(true);
   } 
   if(tft_s.pressure(20, 220, 40, 80)){
      tft_s.fan();
      tft_s.setStateFan(true);
   }  
  //moventSensors_dos();
    movementSensors();
    
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
  delay(2000);
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
//== SENSOR MOVIMIENTO
//=========================================

 void movementSensors(){
      float temperatura=0;
  if(sensorUno.activacionSensor()){
     temperatura= readTemperatureAsCelcius(dht);
    Serial.println(sensorUno.getIdSensor()+" ACTIVADO");
    bluetooth.updatePersonaAndTemp("1", temperatura);
    delay(1000);
    bluetooth.updatePersonaAndTemp("0", temperatura);
   }
   if(sensorDos.activacionSensor()){
     temperatura= readTemperatureAsCelcius(dht);
     Serial.println(sensorDos.getIdSensor()+" ACTIVADO");
     bluetooth.updatePersonaAndTemp("2", temperatura);
   }
   if(sensorTres.activacionSensor()){
     temperatura= readTemperatureAsCelcius(dht);
    Serial.println(sensorTres.getIdSensor()+" ACTIVADO");
    bluetooth.updatePersonaAndTemp("3", temperatura);
   }
   if(sensorCocina.activacionSensor()){
     temperatura= readTemperatureAsCelcius(dht);
    Serial.println(sensorCocina.getIdSensor()+" ACTIVADO");
    bluetooth.updatePersonaAndTemp("4", temperatura);
   
   } 
 }

 void moventSensors_dos(){
   float temperatura=0;
  pirUno = digitalRead(SENSOR_MOVIMIENTO_UNO);
  pirDos = digitalRead(SENSOR_MOVIMIENTO_DOS);
  pirTres = digitalRead(SENSOR_MOVIMIENTO_TRES);
  pirCuatro = digitalRead(SENSOR_MOVIMIENTO_COCINA);
  Serial.print(pirUno);
  Serial.print("--");
  Serial.print(pirDos);
  Serial.print("--");
  Serial.print(pirTres);
  Serial.print("--");
  Serial.print(pirCuatro);
  Serial.println();
  if(readPirUno){
      if(pirUno == HIGH){
         temperatura= readTemperatureAsCelcius(dht);
          Serial.println(sensorUno.getIdSensor()+" ACTIVADO");
          bluetooth.updatePersonaAndTemp("1", temperatura);
          delay(1000);
          bluetooth.updatePersonaAndTemp("0", temperatura);
        readPirUno = false;
      }
  }
  if(pirUno  == LOW ){
    readPirUno = true;
  }

   if(readPirDos){
      if(pirDos == HIGH){
         temperatura= readTemperatureAsCelcius(dht);
         Serial.println(sensorDos.getIdSensor()+" ACTIVADO");
         bluetooth.updatePersonaAndTemp("2", temperatura);
        readPirDos = false;
      }
  }
  if(pirDos  == LOW){
    readPirDos = true;
  }

   if(readPirTres ){
      if(pirTres == HIGH){
         temperatura= readTemperatureAsCelcius(dht);
         Serial.println(sensorTres.getIdSensor()+" ACTIVADO");
         bluetooth.updatePersonaAndTemp("3", temperatura);
        readPirTres = false;
      }
  }
  if(pirTres == LOW){
    readPirTres = true;
  }

   if(readPirCuatro){
      if(pirCuatro == HIGH){
           temperatura= readTemperatureAsCelcius(dht);
          Serial.println(sensorCocina.getIdSensor()+" ACTIVADO");
          bluetooth.updatePersonaAndTemp("4", temperatura);
        readPirCuatro = false;
      }
  }
  if(pirCuatro == LOW){
    readPirCuatro = true;
  }
 }

  
//=========================================
//== UTILS
//=========================================


void printSerialCard(String cardSerial){   
    Serial.println("Card UID: "+ cardSerial);
}
