package com.safetynet.safetynet.services;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.safetynet.repository.FirestationRepository;
import com.safetynet.safetynet.repository.MedicalrecordRepository;
import com.safetynet.safetynet.repository.impl.FirestationRepositoryImpl;
import com.safetynet.safetynet.repository.impl.MedicalrecordRepositoryImpl;
import com.safetynet.safetynet.repository.impl.PersonRepositoryImpl;
import com.safetynet.safetynet.model.Medicalrecord;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.PersonRepository;

public class GlobalService {
    private PersonRepository personRepository;
    private FirestationRepository firestationRepository;
    private MedicalrecordRepository medicalrecordRepository;
    ObjectMapper objectMapper=new ObjectMapper();
    String datafile="/src/main/resources/data.json";
    public GlobalService() {
        
    }
    public String getFirestationByFirestationNumber(String stationNumber) throws StreamReadException, DatabindException, IOException {
        personRepository=new PersonRepositoryImpl();
        firestationRepository=new FirestationRepositoryImpl();
        medicalrecordRepository=new MedicalrecordRepositoryImpl();
        Set<String> adresses=new HashSet<>(firestationRepository.getFirestationByStation(stationNumber));
        List<Person> persons=new LinkedList<>();
        for(String address:adresses)
            persons.addAll(personRepository.getPersonsByAdress(address));
        ArrayNode personsNode = objectMapper.convertValue(persons, ArrayNode.class);
        int adultcount=(int) persons.stream()
        .filter(person->medicalrecordRepository.getMedicalrecordByNames(person.firstName, person.lastName)
        .orElseThrow().getAge()<18).count();
        ObjectNode res=JsonNodeFactory.instance.objectNode();
        res.set("people",personsNode);
        res.put("child count", adultcount);
        return res.toString();
    }
    public String getChildAlert(String address) throws IOException {
        personRepository=new PersonRepositoryImpl();
        firestationRepository=new FirestationRepositoryImpl();
        medicalrecordRepository=new MedicalrecordRepositoryImpl();
        List<Person> concernedPersons=personRepository.getPersonsByAdress(address);
        concernedPersons=concernedPersons.stream()
        .filter(person->medicalrecordRepository.getMedicalrecordByNames(person.firstName,person.lastName)
        .orElseThrow().getAge()<18).toList();
        ArrayNode concernedPersonsNode = objectMapper.convertValue(concernedPersons, ArrayNode.class);
        for(int i=0;i<concernedPersonsNode.size();i++){
            ObjectNode person=(ObjectNode)concernedPersonsNode.get(i);
            String firstname=person.get("firstName").asText();
            String lastname=person.get("lastName").asText();
            int age=(int) medicalrecordRepository.getMedicalrecordByNames(firstname, lastname).orElseThrow().getAge();
            person.put("age",age);
            List<Person> persons=((PersonRepositoryImpl)personRepository).getPersonsByLastName(lastname);
            List<Person> persons2=persons.stream().filter(per->!per.firstName.equals(firstname)).toList();
            ArrayNode personsNode = objectMapper.convertValue(persons2, ArrayNode.class);
            person.set("familly members",personsNode);
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(concernedPersons);
        
    }
    public String gethPhoneAlert(String fireStationNumber) throws JsonProcessingException, IOException{
        personRepository=new PersonRepositoryImpl();
        firestationRepository=new FirestationRepositoryImpl();
        Set<String> adresses=new HashSet<>(firestationRepository.getFirestationByStation(fireStationNumber));
        List<String> concernedPhone=new LinkedList<>();
        for(String address:adresses){
            concernedPhone.addAll(personRepository.getPersonsByAdress(address).stream().map(person->person.phone).toList());
        }
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(concernedPhone);
        return str;
    }
    public String getFire(String adress) throws IOException{
        personRepository=new PersonRepositoryImpl();
        firestationRepository=new FirestationRepositoryImpl();
        medicalrecordRepository=new MedicalrecordRepositoryImpl();
        List<Person> concernedPersons=personRepository.getPersonsByAdress(adress);
        String firestation=firestationRepository.getFirestationByAdress(adress);
        ArrayNode concerned=JsonNodeFactory.instance.arrayNode();
        for(Person person:concernedPersons){
            Medicalrecord medicalrecord=medicalrecordRepository.getMedicalrecordByNames(person.firstName,person.lastName).orElseThrow();
            concerned.add(mergeMedicalrecordToPerson(person, medicalrecord));
        }
        ObjectNode res=JsonNodeFactory.instance.objectNode();
        res.set("people",concerned);
        res.put("firestation", firestation);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res);
    }
    public String getFloodStations(String[] stationNumber) throws IOException{
        personRepository=new PersonRepositoryImpl();
        firestationRepository=new FirestationRepositoryImpl();
        medicalrecordRepository=new MedicalrecordRepositoryImpl();
        Set<String> adresses=new HashSet<>();
        for(String station:stationNumber){
            adresses.addAll(firestationRepository.getFirestationByStation(station));
        }
        List<Person> concernedPersons=new LinkedList<>();
        for(String address:adresses){
            concernedPersons.addAll(personRepository.getPersonsByAdress(address));
        }
      
        JsonNode objectNode=JsonNodeFactory.instance.objectNode();
            
        for(Person person:concernedPersons){
            Medicalrecord medicalrecord=medicalrecordRepository.getMedicalrecordByNames(person.firstName, person.lastName).orElseThrow();
            JsonNode thisPerson=mergeMedicalrecordToPerson(person,medicalrecord);
            if(objectNode.get(person.address)==null){
                ArrayNode arrayNode=JsonNodeFactory.instance.arrayNode();
                arrayNode.add(thisPerson);
                ((ObjectNode)objectNode).set(person.address, arrayNode);
            }else{
                ((ArrayNode) objectNode.get(person.address)).add(thisPerson);
            }
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);
    }
    public String getPersonInfolastName(String lastName) throws IOException{
        personRepository=new PersonRepositoryImpl();
        medicalrecordRepository=new MedicalrecordRepositoryImpl();
        List<Person> persons=((PersonRepositoryImpl)personRepository).getPersonsByLastName(lastName);
        ArrayNode arrayNode=JsonNodeFactory.instance.arrayNode();
        for(Person person:persons){
            Medicalrecord medicalrecord=medicalrecordRepository.getMedicalrecordByNames(person.firstName, person.lastName).orElseThrow();
            ObjectNode objectNode=mergeMedicalrecordToPerson(person,medicalrecord);

            arrayNode.add(objectNode);
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
    }
    public String getCommunityEmail(String city) throws IOException{
        personRepository=new PersonRepositoryImpl();
        List<Person> persons=((PersonRepositoryImpl)personRepository).getPersonByCity(city);
        List<String> emails=persons.stream().map(person->person.email).toList();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(emails);
    }
    public ObjectNode mergeMedicalrecordToPerson(Person person,Medicalrecord medicalrecord){
        ObjectNode personNode=objectMapper.convertValue(person, ObjectNode.class);
        ObjectNode medicalrecordNode=objectMapper.convertValue(medicalrecord, ObjectNode.class);
        medicalrecordNode.remove("firstName");
        medicalrecordNode.remove("lastName");
        medicalrecordNode.remove("birthdate");
        personNode.put("age", medicalrecord.getAge());
        personNode.set("medicalrecord",medicalrecordNode);
        return personNode;
    }
}
