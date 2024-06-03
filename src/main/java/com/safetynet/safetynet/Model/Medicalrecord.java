package com.safetynet.safetynet.Model;

import java.util.Calendar;
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
    public boolean isAdult(){
        if(birthdate != null && !birthdate.equals("")){
            String[] str=birthdate.split("/");
            int[] abirthdate={Integer.parseInt(str[0]),Integer.parseInt(str[1]),Integer.parseInt(str[2])-1900};
            Calendar calendar = Calendar.getInstance();
            int[] adate={calendar.get(Calendar.DAY_OF_MONTH),calendar.get(calendar.get(Calendar.MONTH)),calendar.get(Calendar.YEAR)-1900};
            long age=(adate[2]*365*24*60*60*1000)+(adate[1]*30*24*60*60*1000)+(adate[0]*24*60*60*1000)-(abirthdate[2]*365*24*60*60*1000)-(abirthdate[1]*30*24*60*60*1000)-(abirthdate[0]*24*60*60*1000);
            return 1000*60*60*24*365<=age;
        }
        return false;

    }
} 
