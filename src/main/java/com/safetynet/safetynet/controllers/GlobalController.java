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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.safetynet.model.Medicalrecord;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.impl.FirestationRepositoryImpl;
import com.safetynet.safetynet.repository.impl.MedicalrecordRepositoryImpl;
import com.safetynet.safetynet.repository.impl.PersonRepositoryImpl;
 
@RestController
public class GlobalController {
    private PersonRepositoryImpl personDAO;
    private FirestationRepositoryImpl firestationDAO;
    private MedicalrecordRepositoryImpl medicalrecordDAO;
    ObjectMapper objectMapper=new ObjectMapper();
    String datafile="/src/main/resources/data.json";
    
    @GetMapping("/firestation")
    public ResponseEntity<String> getFirestation(@RequestParam String stationNumber) throws IOException, JsonProcessingException{
        personDAO=new PersonRepositoryImpl();
        firestationDAO=new FirestationRepositoryImpl();
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
        personDAO=new PersonRepositoryImpl();
        firestationDAO=new FirestationRepositoryImpl();
        medicalrecordDAO=new MedicalrecordRepositoryImpl();
        List<Person> concernedPersons=personDAO.getPersonsByAdress(address);
        concernedPersons=concernedPersons.stream()
        .filter(person->!medicalrecordDAO.getMedicalrecordByNames(person.firstName,person.lastName).orElseThrow().isAdult()).toList();
        return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(concernedPersons));
    }
    @GetMapping("/phoneAlert")
    public ResponseEntity<String> getPhoneAlert(@RequestParam String fireStationNumber) throws IOException{
        personDAO=new PersonRepositoryImpl();
        firestationDAO=new FirestationRepositoryImpl();
        Set<String> adresses=new HashSet<>(firestationDAO.getFirestationByStation(fireStationNumber));
        List<String> concernedPhone=new LinkedList<>();
        for(String address:adresses){
            concernedPhone.addAll(personDAO.getPersonsByAdress(address).stream().map(person->person.phone).toList());
        }
        return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(concernedPhone));
    }
    @GetMapping("/fire") 
    public ResponseEntity<String> getFire(@RequestParam String adress) throws IOException{
        personDAO=new PersonRepositoryImpl();
        firestationDAO=new FirestationRepositoryImpl();
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
        personDAO=new PersonRepositoryImpl();
        firestationDAO=new FirestationRepositoryImpl();
        Set<String> adresses=new HashSet<>(firestationDAO.getFirestationByStation(stationNumber));
        List<Person> concernedPersons=new LinkedList<>();
        for(String address:adresses){
            concernedPersons.addAll(personDAO.getPersonsByAdress(address));
        }
        Map<String,List<Person>> map=new HashMap<>();
        ObjectNode objectNode=JsonNodeFactory.instance.objectNode();
            
        for(Person person:concernedPersons){
            Medicalrecord medicalrecord=medicalrecordDAO.getMedicalrecordByNames(person.firstName, person.lastName).orElseThrow();
            ObjectNode thisPerson=mergeMedicalrecordToPerson(person,medicalrecord);
            if(objectNode.get(person.lastName)!=null){
                objectNode.set(person.lastName, thisPerson);
            }else{
                ((ArrayNode) objectNode.get(person.lastName)).add(thisPerson);
            }
        }
        return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode));
    }
    @GetMapping("/personInfolastName")
    public ResponseEntity<String> getPersonInfolastName(@RequestParam String lastName) throws IOException{
        personDAO=new PersonRepositoryImpl();
        medicalrecordDAO=new MedicalrecordRepositoryImpl();
        List<Person> persons=personDAO.getPersonsByLastName(lastName);
        ArrayNode arrayNode=JsonNodeFactory.instance.arrayNode();
        for(Person person:persons){
            Medicalrecord medicalrecord=medicalrecordDAO.getMedicalrecordByNames(person.firstName, person.lastName).orElseThrow();
            ObjectNode objectNode=mergeMedicalrecordToPerson(person,medicalrecord);
            arrayNode.add(objectNode);
        }
        return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode));
    }
    @GetMapping("/communityEmail")
    public ResponseEntity<String> getCommunityEmail(@RequestParam String city) throws IOException{
        personDAO=new PersonRepositoryImpl();
        List<Person> persons=personDAO.getPersonByCity(city);
        List<String> emails=persons.stream().map(person->person.email).toList();
        return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(emails));
    }
    public ObjectNode mergeMedicalrecordToPerson(Person person,Medicalrecord medicalrecord){
        ObjectNode personNode=objectMapper.convertValue(person, ObjectNode.class);
        ObjectNode medicalrecordNode=objectMapper.convertValue(medicalrecord, ObjectNode.class);
        medicalrecordNode.remove("firstName");
        medicalrecordNode.remove("lastName");
        personNode.set("medicalrecord",medicalrecordNode);
        return personNode;
    }
}
