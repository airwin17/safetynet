package com.safetynet.safetynet.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.Data.Data;
import com.safetynet.safetynet.Data.FirestationDAO;
import com.safetynet.safetynet.Data.MedicalrecordDAO;
import com.safetynet.safetynet.Data.PersonDAO;
import com.safetynet.safetynet.Model.Firestation;
import com.safetynet.safetynet.Model.Medicalrecord;
import com.safetynet.safetynet.Model.Person;

@RestController
public class Controller {
    private PersonDAO personDAO;
    private FirestationDAO firestationDAO;
    private MedicalrecordDAO medicalrecordDAO;
    ObjectMapper objectMapper=new ObjectMapper();
    String datafile="/src/main/resources/data.json";
    
    @GetMapping("/firestation")
    public ResponseEntity<String> getFirestation(@RequestParam String stationNumber) throws IOException, JsonProcessingException{
        personDAO=new PersonDAO();
        firestationDAO=new FirestationDAO();
        firestationDAO.getFirestationByStation(stationNumber);
        Set<String> concernedAdresses=new HashSet<>(firestationDAO.getFirestationByStation(stationNumber));
        List<Person> concernedPersons=new LinkedList<>();
        for(String address:concernedAdresses){
            concernedPersons.addAll(personDAO.getPersonsByAdress(address));
        }
        Long adultNumber=concernedPersons.stream().filter(i->medicalrecordDAO.getMedicalrecordByNames(i.firstName, i.lastName).orElseThrow().isAdult()).count();
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new Object[] { concernedPersons, adultNumber.intValue() });
        return ResponseEntity.ok(str);
    }
    @GetMapping("/childAlert")
    public ResponseEntity<String> getChildAlert(@RequestParam String address) throws IOException{
        personDAO=new PersonDAO();
        firestationDAO=new FirestationDAO();
        medicalrecordDAO=new MedicalrecordDAO();
        List<Person> concernedPersons=personDAO.getPersonsByAdress(address);
        concernedPersons=concernedPersons.stream()
        .filter(person->!medicalrecordDAO.getMedicalrecordByNames(person.firstName,person.lastName).orElseThrow().isAdult()).toList();
        return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(concernedPersons));
    }
    @GetMapping("/phoneAlert")
    public ResponseEntity<String> getPhoneAlert(@RequestParam String fireStationNumber) throws IOException{
        personDAO=new PersonDAO();
        firestationDAO=new FirestationDAO();
        Set<String> adresses=new HashSet<>(firestationDAO.getFirestationByStation(fireStationNumber));
        List<String> concernedPhone=new LinkedList<>();
        for(String address:adresses){
            concernedPhone.addAll(personDAO.getPersonsByAdress(address).stream().map(person->person.phone).toList());
        }
        return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(concernedPhone));
    }
    @GetMapping("/fire") 
    public ResponseEntity<String> getFire(@RequestParam String adress) throws IOException{
        personDAO=new PersonDAO();
        firestationDAO=new FirestationDAO();
        List<Person> concernedPersons=personDAO.getPersonsByAdress(adress);
        String firestation=firestationDAO.getFirestationByAdress(adress);
        List<Medicalrecord> concerned=new LinkedList<>();
        for(Person person:concernedPersons){
            concerned.add(medicalrecordDAO.getMedicalrecordByNames(person.firstName,person.lastName).orElseThrow());
        }
        return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new Object[] {concernedPersons,concerned,firestation}));
    }
    @GetMapping("/flood/stations")
    public ResponseEntity<String> getFloodStations(@RequestParam String stationNumber) throws IOException{
        personDAO=new PersonDAO();
        firestationDAO=new FirestationDAO();
        Set<String> adresses=new HashSet<>(firestationDAO.getFirestationByStation(stationNumber));
        List<Person> concernedPersons=new LinkedList<>();
        for(String address:adresses){
            concernedPersons.addAll(personDAO.getPersonsByAdress(address));
        }
        Map<String,List<Person>> map=new HashMap<>();
        for(Person person:concernedPersons){
            if(!map.containsKey(person.lastName)){
                map.put(person.lastName, personDAO.getPersonsByLastName(person.lastName));
            }
        }
        concernedPersons=new LinkedList<>(map.values());
        return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(concernedPersons));
    }
}
