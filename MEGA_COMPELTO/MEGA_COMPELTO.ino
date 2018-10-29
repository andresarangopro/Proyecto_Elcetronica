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
//=========================================
//== DEFINES
//=========================================
/**RFID**/
#define SS_PIN 53
#define RST_PIN 49
/**SERVO**/
#define SERVO_PIN 2
/**COOLER**/
#define COOLER_UNO 49
/**SENSOR PIR**/
#define SENSOR_MOVIMIENTO_UNO 4
#define SENSOR_MOVIMIENTO_DOS 5
/**SENSOR TEMPERATURA - HUMEDAD**/
#define SENSOR_TEMPERATURA 46
#define DHTTYPE DHT11
/**BUZZER**/
#define BUZZER 48
/**LEDS**/
#define LED_COCINA 47
//=========================================
//== VARIABLES
//=========================================
/**RFID**/
int static KEY_MASTER_CARD[] = {131,221,251,36}; //This is the stored UID
MFRC522 rfid(SS_PIN, RST_PIN); // Instance of the class
/**LCD**/
LiquidCrystal_I2C lcd(0x3F,16,2);  //
/**SERVO**/
Servo ServoPuerta; 
int static CERO_GRADES = 0;
int static DELAY_UNTIL_OPEN_DOOR = 5000;
/**CLASSES VARS**/
uint8_t static ENCENDIDO = HIGH;
uint8_t static APAGADO = LOW;
/**SENSOR_PIR**/
SensorMovimiento sensorUno(HIGH,"SENSOR UNO",SENSOR_MOVIMIENTO_UNO);
SensorMovimiento sensorDos(HIGH,"SENSOR DOS",SENSOR_MOVIMIENTO_DOS);
/**SENSOR TEMPERATURA - HUMEDAD**/
DHT dht(SENSOR_TEMPERATURA, DHTTYPE);
/**BUZZER**/
DigitalWObject buzzer(BUZZER, APAGADO, "BUZZER");
/**COOLER**/
DigitalWObject cooler(COOLER_UNO, APAGADO, "COOLER_UNO");
/**LEDS**/
DigitalWObject led_cocina(LED_COCINA,APAGADO, "LED COCINA");
int state;
char c;
//=========================================
//== SETUP PROYECT
//=========================================

void setup() {
    Serial.begin(9600);
    
    SPI.begin(); // Init SPI bus
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
   sensorUno.getStateOnce();
   sensorDos.getStateOnce(); 
  
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
      openDoor(isMasterKey);
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
    for (byte i = 0; i < cardSerial.size; i++) {
      if(!(cardSerial.uidByte[i] == KEY_MASTER_CARD[i])){
        match = false;
      }  
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

void openDoor(int correctMasterKey){
  if(correctMasterKey){
     moveServo(180); 
  }else{
    buzzer.setEstado(ENCENDIDO);
    delay(2000);
    buzzer.setEstado(APAGADO);
  }
}

//=========================================
//== TEMPERATURA DTH11
//=========================================

float readHumidity(DHT dht){
  return dht.readHumidity();
}

float readTemperatureAsCelcius(DHT dht){
  delay(1000);
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
