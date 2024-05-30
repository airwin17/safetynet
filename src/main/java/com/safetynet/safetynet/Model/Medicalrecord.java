package com.safetynet.safetynet.Model;

import java.util.List;

public class Medicalrecord {
    public String birthdate;
    public String firstName;
    public String lastName;
    public List<String> medications;
    public List<String> allergies;
    public Medicalrecord(
        String firstName,
        String lastName,
        String birthdate){
            this.firstName=firstName;
            this.lastName=lastName;
            this.birthdate=birthdate;
    }
    public Medicalrecord(){
        
    }
    @Override
    public String toString(){
        return firstName+lastName+birthdate;
    }
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Medicalrecord){
            Medicalrecord medicalrecord=(Medicalrecord) obj;
            if(this.firstName.equals(medicalrecord.firstName)&this.lastName.equals(medicalrecord.lastName)){
                return true;
            }else return false;
        }else return false;
    }

} 
