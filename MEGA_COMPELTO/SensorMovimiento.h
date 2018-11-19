//=========================================
//== SENSOR DE MOVIMIENTO
//=========================================

class SensorMovimiento{

  //=========================================
  //== VARIABLES
  //=========================================
  
  private:
    int state;
    int maded;
    String idSensor;
    uint8_t defSensor;

  //=========================================
  //== METODOS
  //=========================================
  
  public:
    SensorMovimiento(int newState, String newIdSensor, uint8_t newDefSensor, int newMaded){        // Constructor
      state = newState;
      idSensor = newIdSensor;
      defSensor = newDefSensor;
      maded = newMaded;
      pinMode(defSensor, INPUT);  
    }
    void setState(int newState){
      state = newState;
    }
    void setMaded(int newMaded){
       maded = newMaded;
    }
    void setIdSensor(String newIdSensor){
      idSensor = newIdSensor;
    }
    void setdefSensor(uint8_t newDefSensor){
      defSensor = newDefSensor;
    }
 
    int getState(){
      return (state);
    }
    int getMaded(){
      return maded;
    }
    String getIdSensor(){
      return(idSensor);
    }
    uint8_t getDefSensor(){
      return(defSensor);
    }
    int detectarMovimiento(){
      return digitalRead(defSensor);
    }    
    void getStateOnce(){
       if (detectarMovimiento() == HIGH) { //si está activado    
        if (state == LOW){//si previamente estaba apagado 
          Serial.println(idSensor+" activado" );  
          state = HIGH;  
        } 
      }else {//si esta desactivado  
        if (state == HIGH){ //si previamente estaba encendido         
            Serial.println(idSensor +" parado");        
            state = LOW; 
        }  
      }   
    } 

   boolean activacionSensor(){
       if(detectarMovimiento() == HIGH && maded == 0){          
            setMaded(1);
            return true;          
       }else if(maded == 1 && detectarMovimiento() == LOW){
            setMaded(0);
            return false;
       }
       return false;
    }

};
 
