class TemperaturaDTH11{
    private:
      DHT dht;

    public:
    TemperaturaDTHEleven(DHT newDht){
        dht = newDht;
    }

    uint8_t getDhtpin(){
      return dthpin;
    }

    DHTTYPE getdhtype(){
      return dhtype;
    }

    int getAvr(){
      return avr;
    }

    void setDhtpin(uint8_t newDhtpin ){
      dhtpin = newDhtpin;
    }

    void setDhtype(DHTTYPE newDhtype){
       dhtype = newDhtype;
    }

    void setAvr(newAvr){
      avr = newAvr;
    }
    
}
