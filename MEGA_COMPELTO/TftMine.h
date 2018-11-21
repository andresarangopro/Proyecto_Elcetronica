#include <MCUFRIEND_kbv.h>
#include <TouchScreen.h>
#include <Adafruit_GFX.h>
MCUFRIEND_kbv tft;

#define BLACK 0x0000
#define BLUE 0x001F
#define RED 0xF800 
#define GREEN 0x07E0
#define CYAN 0x07FF 
#define MAGENTA 0xF81F 
#define YELLOW 0xFFE0 
#define WHITE 0xFFFF

#define YP A2 // Pin analogico A2 para ADC 
#define XM A1 // Pin analogico A1 para ADC 
#define YM 6 
#define XP 7

#define MINPRESSURE 1 
#define MAXPRESSURE 1000

TouchScreen ts = TouchScreen(XP, YP, XM, YM, 570);

/*#define led1 22
#define led2 24
#define led3 26
#define led4 28
#define led5 30*/

/*
 * Cantidad de personas en una habitacion
 * Temperatura de cada pieza
 * Encender las luces de las piezas
*/

short TS_MINX = 200; // Coordenadas del panel tactil para delimitar 
short TS_MINY = 120; // el tamaño de la zona donde podemos presionar
short TS_MAXX = 850; // y que coincida con el tamaño del LCD 
short TS_MAXY = 891;

int X;
int Y;
int Z;
boolean fun = true;
boolean fans = true;
int x1StaticTft = 140;
int x2StaticTft = 180;

class TftMine{
    private:
      DigitalWObject ledUnoObj;
      DigitalWObject ledDosObj;
      DigitalWObject ledTresObj;
      DigitalWObject ledCuatroObj;
      DigitalWObject ledCincoObj;
      DigitalWObject fanUnoObj;
      DigitalWObject fanDosObj;
      DigitalWObject celda;
      DigitalWObject DHTSala;
      
      boolean entroFan;
      
    public: 
       boolean entroMenu;
      TftMine(DigitalWObject nledUnoObj,DigitalWObject nledDosObj,DigitalWObject nledTresObj
      ,DigitalWObject nledCuatroObj,DigitalWObject nledCincoObj, 
        DigitalWObject nfanUnoObj,DigitalWObject nfanDosObj, 
        DigitalWObject nCelda, DigitalWObject nDHTSala){    
           
        ledUnoObj = nledUnoObj;
        ledDosObj = nledDosObj;
        ledTresObj = nledTresObj;
        ledCuatroObj = nledCuatroObj;
        ledCincoObj = nledCincoObj;
        fanUnoObj = nfanUnoObj;
        fanDosObj = nfanDosObj;
        celda = nCelda;
        DHTSala = nDHTSala;
        entroMenu = true;
        entroFan = true;
      }    
      
      void initTFT(){
        tft.reset();
        uint16_t identifier = tft.readID();
        Serial.print("ID = 0x");
        Serial.println(identifier, HEX);
        if (identifier == 0xEFEF) identifier = 0x9486;
        tft.begin(identifier);
      }

      void getXYZ(){
        digitalWrite(13, HIGH); 
        TSPoint p = ts.getPoint();
        digitalWrite(13, LOW); 
        pinMode(XM, OUTPUT); 
        pinMode(YP, OUTPUT); 
        X = map(p.x, TS_MAXX, TS_MINX, tft.width(), 0);
        Y = map(p.y, TS_MINY, TS_MAXY, tft.height(), 0);
        Z = p.z;
      }
      
      void funMenu(){
        tft.fillScreen(CYAN);
      
        drawText(75, 10, "Menu", RED, 3);
  
        tft.drawRect(40, 80, 160, 50, RED);
        drawText(55, 95, "Temperatura", BLACK, 2);
      
        tft.drawRect(40, 150, 160, 50, RED);
        drawText(75, 165, "Luces", BLACK, 2);
      
        tft.drawRect(40, 220, 160, 50, RED);  
        drawText(90, 235, "Ventilador", BLACK, 2);
      }

      boolean pressure(int x1, int x2, int y1, int y2){  
        getXYZ();
        if ((X > x1 && X < x2) && (Y > y1 && Y < y2) && (Z > MINPRESSURE && Z < MAXPRESSURE)){
          return true;
        }else{
          return false;
        }
      }
      
      void drawText(int cx, int cy, String nameText, long color, long siz){
        tft.setCursor(cx, cy);
        tft.setTextColor(color);
        tft.setTextSize(siz);
        tft.print(nameText);
      }

      void temperature(){
        tft.fillScreen(BLACK);
      
        drawText(20, 5, "Temperatura", RED, 3);
        
        drawText(20, 45, "Sala: ", CYAN, 2);
        tft.print(DHTSala);
      
        /*drawText(20, 85, "Pieza 1: ", CYAN, 2);
        tft.print("31 C");
      
        drawText(20, 125, "Pieza 2: ", CYAN, 2);
        tft.print("32 C");
      
        drawText(20, 165, "Pieza 3: ", CYAN, 2);
        tft.print("31 C");
      
        drawText(20, 205, "Pieza 4: ", CYAN, 2);
        tft.print("30 C");*/
      
        tft.drawRect(20, 250, 100, 50, RED);
        drawText(45, 265, "Menu", CYAN, 2);
      
        tft.drawRect(130, 250, 100, 50, RED);
        drawText(145, 265, "Update", CYAN, 2);
      
        while(entroFan){
          if(pressure(20, 120, 20, 70)){
            funMenu();
            setStateFan(false);
          }
          if(pressure(130, 230, 20, 70)){
            temperature();
            setStateFan(false);
          }
        }
      }

      void light(){
        tft.fillScreen(BLACK);
      
        drawText(75, 5, "Luces", RED, 3);
        
        drawText(20, 45, "Sala", CYAN, 2);
        drawFillRect(40, ledDosObj);
        
        drawText(20, 85, "Pieza 1", CYAN, 2);
        drawFillRect(80, ledTresObj);
        
        drawText(20, 125, "Pieza 2", CYAN, 2);
        drawFillRect(120, ledCuatroObj);
      
        drawText(20, 165, "Pieza 3", CYAN, 2);
        drawFillRect(160, ledCincoObj);
        
        drawText(20, 205, "Cocina", CYAN, 2);
        drawFillRect(200, ledUnoObj); 
      
        tft.drawRect(40, 250, 160, 50, RED);
        drawText(90, 265, "Menu", CYAN, 2);
      
        while(entroFan){
          if(pressure(20, 220, 20, 70)){
            funMenu();
            setStateFan(false);
          }
        
          ledOn(255, 280);
          ledOn(215, 240);
          ledOn(175, 200);
          ledOn(135, 160);
          ledOn(95, 120);
        }    
      }

      void fan(){
        tft.fillScreen(BLACK);
        
        drawText(75, 5, "Aire", RED, 3);
      
        drawText(20, 60, "Caliente", CYAN, 2);
        drawFillRect(50, fanUnoObj);
        
        drawText(20, 140, "Frio", CYAN, 2);
        drawFillRect(130, fanDosObj);
      
        tft.drawRect(20, 250, 100, 50, RED);
        drawText(45, 265, "Menu", CYAN, 2);
      
        tft.drawRect(130, 250, 100, 50, RED);
        drawText(145, 265, "Update", CYAN, 2);
      
        while(entroFan){          
          if(pressure(20, 120, 20, 70)){
            funMenu();
            setStateFan(false);
          }
          if(pressure(130, 230, 20, 70)){
            fan();          
          }

          fanOn(235, 260);
          fanOn(155, 180);
        }
      }

      void fanOn(int y1, int y2){
        if(y1 >= 235 && y2 <= 260){
           if(pressure(x1StaticTft,x2StaticTft,y1, y2)){
             fanUnoObj.changeToOppositeState();       
             celda.changeToOppositeState();     
             fan();
          } 
          if(pressure(x1StaticTft+40, x2StaticTft+82, y1, y2)){
              fanUnoObj.changeToOppositeState();   
              celda.changeToOppositeState(); 
              fan();        
          }
        }
        if(y1 >= 155 && y2 <= 180){
           if(pressure(x1StaticTft,x2StaticTft,y1, y2)){
             fanDosObj.changeToOppositeState();           
             fan();
          } 
          if(pressure(x1StaticTft+40, x2StaticTft+82, y1, y2)){
            fanDosObj.changeToOppositeState();           
            fan();
          }
        }
      }
      
      void ledOn(int y1, int y2){
       if(y1 >= 255 && y2 <= 280){
             if(pressure(x1StaticTft,x2StaticTft,y1, y2)){
               ledDosObj.changeToOppositeState();           
               light();
            } 
          if(pressure(x1StaticTft+40, x2StaticTft+82, y1, y2)){
              ledDosObj.changeToOppositeState();   
              light();        
          }
        }
        if(y1 >= 215 && y2 <= 240){
             if(pressure(x1StaticTft,x2StaticTft,y1, y2)){
               ledTresObj.changeToOppositeState();           
               light();
            } 
          if(pressure(x1StaticTft+40, x2StaticTft+82, y1, y2)){
              ledTresObj.changeToOppositeState();           
              light();
          }
        }
         if(y1 >= 175 && y2 <= 200){
             if(pressure(x1StaticTft,x2StaticTft,y1, y2)){
               ledCuatroObj.changeToOppositeState();    
               light();       
            } 
          if(pressure(x1StaticTft+40, x2StaticTft+82, y1, y2)){
              ledCuatroObj.changeToOppositeState();    
              light();       
          }
        }
         if(y1 >= 135 && y2 <= 160){
             if(pressure(x1StaticTft,x2StaticTft,y1, y2)){
               ledCincoObj.changeToOppositeState();    
               light();       
            } 
          if(pressure(x1StaticTft+40, x2StaticTft+82, y1, y2)){
              ledCincoObj.changeToOppositeState();         
              light();  
          }
        }
         if(y1 >= 95 && y2 <= 120){
             if(pressure(x1StaticTft,x2StaticTft,y1, y2)){
               ledUnoObj.changeToOppositeState();          
               light(); 
            } 
          if(pressure(x1StaticTft+40, x2StaticTft+82, y1, y2)){
              ledUnoObj.changeToOppositeState();        
              light();   
          }
        }
        
      }
      void drawFillRect(int y, DigitalWObject pinLed){
        if(isLedOn(pinLed.getEstado())){
          tft.fillRect(140, y, 42, 25, RED);
          tft.setCursor(149, y+5);
          tft.setTextSize(2);
          tft.println("ON");
          tft.drawRect(180, y, 42, 25, RED);
          tft.setCursor(185, y+5);
          tft.setTextSize(2);
          tft.println("OFF");
        }else{
          tft.drawRect(140, y, 42, 25, RED);
          tft.setCursor(149, y+5);
          tft.setTextSize(2);
          tft.println("ON");
          tft.fillRect(180, y, 42, 25, RED);
          tft.setCursor(185, y+5);
          tft.setTextSize(2);
          tft.println("OFF");
        }
      }
      
      boolean isLedOn(uint8_t pinLed){
        if(pinLed == HIGH){
          return true;
        }else{
          return false;
        }
      }

      void setState(boolean nEstate){
        entroMenu = nEstate;
      }

      void setStateFan(boolean nEstate){
        entroFan= nEstate;
      }

      
};
