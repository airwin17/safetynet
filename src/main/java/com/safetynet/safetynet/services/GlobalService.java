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
import com.safetynet.safetynet.dto.AdressDTO;
import com.safetynet.safetynet.dto.ChildAlertDTO;
import com.safetynet.safetynet.dto.FireDTO;
import com.safetynet.safetynet.dto.PersonDTO;
import com.safetynet.safetynet.dto.PersonFirestrationDTO;
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
        /**
     * Retrieves a JSON node containing information about people living at a given fire station number,
     * including the number of children living at the station.
     *
     * @param  stationNumber   the number of the fire station to retrieve information for
     * @return                 a JSON node containing the following information:
     *                         - "people": an array of JSON objects representing each person living at the station,
     *                                    with each object containing the person's first name, last name, address,
     *                                    and age
     *                         - "child count": the number of children living at the station
     * @throws StreamReadException    if there is an error reading the data stream
     * @throws DatabindException      if there is an error binding the data to JSON
     * @throws IOException            if there is an I/O error
     */
    public PersonFirestrationDTO getFirestationByFirestationNumber(String stationNumber) throws StreamReadException, DatabindException, IOException {
        personRepository=new PersonRepositoryImpl();
        firestationRepository=new FirestationRepositoryImpl();
        medicalrecordRepository=new MedicalrecordRepositoryImpl();
        Set<String> adresses=new HashSet<>(firestationRepository.getFirestationByStation(stationNumber));
        List<Person> persons=new LinkedList<>();
        for(String address:adresses)
            persons.addAll(personRepository.getPersonsByAdress(address));
        int adultcount=(int) persons.stream()
        .filter(person->medicalrecordRepository.getMedicalrecordByNames(person.firstName, person.lastName)
        .orElseThrow().getAge()<18).count();
        return new PersonFirestrationDTO(persons,adultcount);
    }
        /**
     * Retrieves a list of children living at a given address.
     *
     * @param  address   the address to search for children
     * @return           a JSON node containing information about children living at the address
     */
    public List<ChildAlertDTO> getChildAlert(String address) throws IOException {
        personRepository=new PersonRepositoryImpl();
        firestationRepository=new FirestationRepositoryImpl();
        medicalrecordRepository=new MedicalrecordRepositoryImpl();
        List<Person> concernedPersons=personRepository.getPersonsByAdress(address);
        concernedPersons=concernedPersons.stream()
        .filter(person->medicalrecordRepository.getMedicalrecordByNames(person.firstName,person.lastName)
        .orElseThrow().getAge()<18).toList();
        List<ChildAlertDTO> children=new LinkedList<>();
        for(int i=0;i<concernedPersons.size();i++){
            Person person=concernedPersons.get(i);
            String firstname=person.firstName;
            String lastname=person.lastName;
            int age=(int) medicalrecordRepository.getMedicalrecordByNames(firstname, lastname).orElseThrow().getAge();
            List<Person> persons=((PersonRepositoryImpl)personRepository).getPersonsByLastName(lastname);
            List<Person> persons2=persons.stream().filter(per->!per.firstName.equals(firstname)).toList();
            children.add(new ChildAlertDTO(persons2,person,age));
        }
        return children;
        
    }
        /**
     * Retrieves a JSON node containing a list of phone numbers of people living at a given fire station number.
     *
     * @param  fireStationNumber   the number of the fire station to retrieve phone numbers for
     * @return                     a JSON node containing an array of phone numbers
     * @throws JsonProcessingException if there is an error processing the JSON
     * @throws IOException            if there is an I/O error
     */
    public List<String> gethPhoneAlert(String fireStationNumber) throws JsonProcessingException, IOException{
        personRepository=new PersonRepositoryImpl();
        firestationRepository=new FirestationRepositoryImpl();
        Set<String> adresses=new HashSet<>(firestationRepository.getFirestationByStation(fireStationNumber));
        List<String> concernedPhone=new LinkedList<>();
        for(String address:adresses){
            concernedPhone.addAll(personRepository.getPersonsByAdress(address).stream().map(person->person.phone).toList());
        }
        return concernedPhone;
    }
        /**
     * Retrieves a JSON node containing information about people living at a given address and the fire station at that address.
     *
     * @param  adress   the address to search for people and the fire station
     * @return           a JSON node containing the following information:
     *                   - "people": an array of JSON objects representing each person living at the address,
     *                               with each object containing the person's first name, last name, address, and medical record
     *                   - "firestation": a string representing the fire station at the address
     * @throws IOException if there is an I/O error
     */
    public FireDTO getFire(String adress) throws IOException{
        personRepository=new PersonRepositoryImpl();
        firestationRepository=new FirestationRepositoryImpl();
        medicalrecordRepository=new MedicalrecordRepositoryImpl();
        List<Person> concernedPersons=personRepository.getPersonsByAdress(adress);
        String firestation=firestationRepository.getFirestationByAdress(adress);
        List<PersonDTO> concerned=new LinkedList<>();
        for(Person person:concernedPersons){
            Medicalrecord medicalrecord=medicalrecordRepository.getMedicalrecordByNames(person.firstName,person.lastName).orElseThrow();
            concerned.add(new PersonDTO(person, medicalrecord,medicalrecord.getAge()));
        }
        return new FireDTO(firestation,concerned);
    }
        /**
     * Retrieves a JSON node containing information about people living at certain addresses based on station numbers.
     *
     * @param  stationNumber   an array of strings representing station numbers
     * @return                a JSON node containing the gathered information
     */
    public List<AdressDTO> getFloodStations(String[] stationNumber) throws IOException{
        personRepository=new PersonRepositoryImpl();
        firestationRepository=new FirestationRepositoryImpl();
        medicalrecordRepository=new MedicalrecordRepositoryImpl();
        Set<String> adresses=new HashSet<>();
        for(String station:stationNumber){
            adresses.addAll(firestationRepository.getFirestationByStation(station));
        }
        
        List<AdressDTO> concernedAdresses=new LinkedList<>();
        for(String address:adresses){
            List<PersonDTO> concernedPersons=new LinkedList<>();
            List<Person> persons=personRepository.getPersonsByAdress(address);
            for(Person person:persons){
                Medicalrecord medicalrecord=medicalrecordRepository.getMedicalrecordByNames(person.firstName, person.lastName).orElseThrow();
                concernedPersons.add(new PersonDTO(person, medicalrecord,medicalrecord.getAge()));
            }
            concernedAdresses.add(new AdressDTO(concernedPersons));
        }
        
        return concernedAdresses;
    }
        /**
     * Retrieves a JSON node containing information about people with a specific last name.
     *
     * @param  lastName   the last name to search for
     * @return             a JSON node containing information about persons with the specified last name
     * @throws IOException if there is an I/O error
     */
    public List<PersonDTO> getPersonInfolastName(String lastName) throws IOException{
        personRepository=new PersonRepositoryImpl();
        medicalrecordRepository=new MedicalrecordRepositoryImpl();
        List<Person> persons=((PersonRepositoryImpl)personRepository).getPersonsByLastName(lastName);
        List<PersonDTO> concernedPersons=new LinkedList<>();
        for(Person person:persons){
            Medicalrecord medicalrecord=medicalrecordRepository.getMedicalrecordByNames(person.firstName, person.lastName).orElseThrow();
            concernedPersons.add(new PersonDTO(person, medicalrecord, medicalrecord.getAge()));
        }
        return concernedPersons;
    }
        /**
     * Retrieves a JSON node containing email addresses of people living in the specified city.
     *
     * @param  city   the city to retrieve email addresses for
     * @return        a JSON node containing email addresses
     */
    public List<String> getCommunityEmail(String city) throws IOException{
        personRepository=new PersonRepositoryImpl();
        List<Person> persons=((PersonRepositoryImpl)personRepository).getPersonByCity(city);
        List<String> emails=persons.stream().map(person->person.email).toList();
        return emails;
    }
}
