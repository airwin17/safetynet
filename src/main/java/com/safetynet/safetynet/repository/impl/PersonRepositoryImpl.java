package com.safetynet.safetynet.repository.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.PersonRepository;
import com.safetynet.safetynet.util.Data;

public class PersonRepositoryImpl implements PersonRepository{
    private ObjectMapper objectMapper;
    Data data;
    private String path="src/main/resources/data.json";
    private FileWriter fileWriter;
    public PersonRepositoryImpl() throws StreamReadException, DatabindException, IOException{
        objectMapper=new ObjectMapper();
        data=objectMapper.readValue(new File("src/main/resources/data.json"),Data.class);
        
    }
    @Override
    public void postPerson(Person person) throws IOException {
        fileWriter=new FileWriter(path);
        data.persons.add(person);
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter.write(str);
        fileWriter.close();
    }

    @Override
    public void putPerson(Person person) throws IOException {
        fileWriter=new FileWriter(path);
        data.persons.removeIf(i->i.equals(person));
        data.persons.add(person);
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter.write(str);
        fileWriter.close();
    }

    @Override
    public void deletePerson(Person person) throws IOException {
        fileWriter=new FileWriter(path);
        data.persons.removeIf(i->i.equals(person));
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter.write(str);
        fileWriter.close();
    }
    @Override
    public Optional<Person> getPersonByNames(String firstName, String lastName) throws JsonProcessingException, IOException {
        return data.persons.stream().filter(i->i.firstName.equals(firstName)&&i.lastName.equals(lastName)).findFirst();
    }
    @Override
    public List<Person> getPersonsByAdress(String address) throws JsonProcessingException, IOException {
        return data.persons.stream().filter(i->i.address.equals(address)).toList();
    }
    public List<Person> getAllPersons() throws JsonProcessingException, IOException{
        return data.persons;
    }
    public List<Person> getPersonsByLastName(String lastName) {
        return data.persons.stream().filter(i->i.lastName.equals(lastName)).toList();
    }
    public List<Person> getPersonByCity(String city) {
        return data.persons.stream().filter(i->i.city.equals(city)).toList();
    }


}
