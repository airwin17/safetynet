package com.safetynet.safetynet.model;

public class Firestation {
    public String address;
    public String station;
    public Firestation(String address,String station){
        this.address=address;
        this.station=station;
    }
    public Firestation(){
        
    }
    @Override
    public String toString(){
        return "Address="+address+"starion number="+station;
    }
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Firestation){
            Firestation firestation= (Firestation) obj;
            if(this.address.equals(firestation.address)){
                return true;
            }else return false;
        }else return false;
    }

}
