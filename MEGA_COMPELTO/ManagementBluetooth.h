//=========================================
//== Manejo Bluetooth 
//=========================================

SoftwareSerial ModBluetooth(10,11); // RX, TX recorder que se cruzan
class ManagementBluetooth{
    //=========================================
    //== VARIABLES
    //=========================================  
    private:
        int RX;
        int TX;
        
        
    //=========================================
    //== METODOS
    //=========================================
    public:
      ManagementBluetooth(int newRX, int newTX){
         RX = newRX;
         TX = newTX;
         
      }

      void printK(){
        ModBluetooth.print("HABITACIONES&");
      }

      void beginT(){
       ModBluetooth.begin(9600);
      }

      char readT(){
        char varRead = ModBluetooth.read(); 
        return varRead;
      }

      void connectB(char varChar,DigitalWObject ledUnoObj, DigitalWObject ledDosObj,DigitalWObject ledTresObj, DigitalWObject ledCuatroObj,DigitalWObject ledCincoObj,DigitalWObject buzzer){
        switch(varChar){
          case '1':{
            updateAllHabitaciones();
            break;
          }        
          case '2':{
            mainLigths(varChar,ledUnoObj, ledDosObj, ledTresObj, ledCuatroObj, ledCincoObj);
            break;
          }
          case '3':{
            buzzer.setEstado(LOW);
            break;
          }
          default:{
            // statements        
             break;
          }
        } 
      }   
      
      void updateHabitacion(String habitacion, float temperatura, int estadoLuz){
         ModBluetooth.print("HABITACIONES&");
         ModBluetooth.print(habitacion+",");
         ModBluetooth.print("temperatura,");
         ModBluetooth.print(temperatura);
         ModBluetooth.print(",");
         ModBluetooth.print("estadoLuz,");
         ModBluetooth.print(estadoLuz);
         ModBluetooth.print("#");
      }
      
      void updateAllHabitaciones(){
         updateHabitacion("1",25, 0);
      }

      void updateLuz(String habitacion, int estadoLuz){
        ModBluetooth.print("LUZ&");
        ModBluetooth.print(habitacion+",");
        ModBluetooth.print("estadoLuz,");
        ModBluetooth.print(estadoLuz);
        ModBluetooth.print("#");
      }
      void updateTemperatura(String habitacion, float temperatura){
        ModBluetooth.print("TEMPERATURA&");
        ModBluetooth.print(habitacion+",");
        ModBluetooth.print("temperatura,");
        ModBluetooth.print(temperatura);
        ModBluetooth.print("#");
      }


      void updatePersona(String habitacion){
        ModBluetooth.print("LASTTIMEHAB&");
        ModBluetooth.print(habitacion+",");
        ModBluetooth.print("lastTimeListenM");
        ModBluetooth.print("#");
      }

      void updatePersonaAndTemp(String habitacion, float temperatura){            
        ModBluetooth.print("LASTTIMEHAB&");
        ModBluetooth.print(habitacion+",");
        ModBluetooth.print("lastTimeListenM,");
        ModBluetooth.print("temperatura,");
        ModBluetooth.print(temperatura);
        ModBluetooth.print("#");
      }

      void insertAlarm(){
       ModBluetooth.print("ALARMAS&.#");
      }

      void sendKeyPersona(String serialCard){
         ModBluetooth.print("PERSONAS&");
         ModBluetooth.print(serialCard+"!");
         ModBluetooth.print("horasEntrada#");
      }

      void mainLigths(char varChar,DigitalWObject ledUnoObj, DigitalWObject ledDosObj
      , DigitalWObject ledTresObj, DigitalWObject ledCuatroObj, DigitalWObject ledCincoObj){
          while(varChar != 'A'){
            varChar = ModBluetooth.read();   
            switch(varChar){
              case '0':{
                ledUnoObj.changeToOppositeState();  
                updateLuz("0", ledUnoObj.getEstado());
                break;
              }
              case '1':{
                ledDosObj.changeToOppositeState();
                updateLuz("1", ledDosObj.getEstado());
                break;
              }
              case '2':{
                ledTresObj.changeToOppositeState();
                updateLuz("2", ledTresObj.getEstado());
                break;
              }
              case '3':{
                 ledCuatroObj.changeToOppositeState();
                 updateLuz("3", ledCuatroObj.getEstado());
                break;
              }
              case '4':{
                 ledCincoObj.changeToOppositeState();
                 updateLuz("4", ledCincoObj.getEstado());
                break;
              }
              default:{
                break;
              }
            }      
        } 
      }

    
};
