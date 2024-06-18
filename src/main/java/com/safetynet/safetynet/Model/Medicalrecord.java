package com.safetynet.safetynet.model;

import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
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
    @JsonIgnore
    public int getAge(){
        Calendar now = Calendar.getInstance();
        int yearNow = now.get(Calendar.YEAR);
        int yearBirth = Integer.parseInt(birthdate.substring(6));
        int age = yearNow - yearBirth;
        if (now.get(Calendar.MONTH) < Integer.parseInt(birthdate.substring(0,2)) || (now.get(Calendar.MONTH) == Integer.parseInt(birthdate.substring(0,2)) && now.get(Calendar.DAY_OF_MONTH) < Integer.parseInt(birthdate.substring(3,5)))) {
            age--;
        }
        return age;
    }
} 
