//=========================================
//== Objeto entrada - tierra
//=========================================

class DigitalWObject{

    //=========================================
    //== VARIABLES
    //=========================================  
    private:
        int pin;
        uint8_t estado;
        String dsName;

    //=========================================
    //== METODOS
    //=========================================

    public:    
    DigitalWObject(int newPin, uint8_t newEstado, String newDsName){
        pin = newPin;
        estado = newEstado;
        dsName = newDsName;
        pinMode(pin,OUTPUT);
    }

    int getPin(){
      return pin;
    }

    uint8_t getEstado(){
      return estado;    
    }

    String getDsName(){
      return dsName;
    }

    void setPin(int newPin){
      pin = newPin;
    }

    void setEstado(uint8_t newEstado){
      estado = newEstado;
      digitalWrite(pin, estado);
    }

    void setDsName(String newDsName){
      dsName = newDsName;
    }

};
