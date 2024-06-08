package com.safetynet.safetynet.Data;

import java.util.List;

import com.safetynet.safetynet.model.Firestation;
import com.safetynet.safetynet.model.Medicalrecord;
import com.safetynet.safetynet.model.Person;

public class Data {
    public List<Person> persons;
    public List<Firestation> firestations;
    public List<Medicalrecord> medicalrecords;
    public Data(){
    }
    @Override
    public String toString(){
        String str="";
        str+="persons\n";
        for(int i=0;i<persons.size();i++){
            str+=persons.get(i).toString()+"\n";
        }
        str+="firestations\n";
        for(int i=0;i<firestations.size();i++){
            str+=firestations.get(i).toString()+"\n";
        }
        str+="medicalrecords\n";
        for(int i=0;i<medicalrecords.size();i++){
            str+=medicalrecords.get(i).toString()+"\n";
        }
        return str;
    }
}
