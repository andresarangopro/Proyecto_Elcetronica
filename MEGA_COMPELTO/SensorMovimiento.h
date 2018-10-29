//=========================================
//== SENSOR DE MOVIMIENTO
//=========================================

class SensorMovimiento{

  //=========================================
  //== VARIABLES
  //=========================================
  
  private:
    int state;
    String idSensor;
    uint8_t defSensor;

  //=========================================
  //== METODOS
  //=========================================
  
  public:
    SensorMovimiento(int newState, String newIdSensor, uint8_t newDefSensor){        // Constructor
      state = newState;
      idSensor = newIdSensor;
      defSensor = newDefSensor;
      pinMode(defSensor, INPUT);  
    }
    void setState(int newState){
      state = newState;
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

};
 
