package com.safetynet.safetynet.repository.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.PersonRepository;
import com.safetynet.safetynet.util.Data;
@Repository
public class PersonRepositoryImpl implements PersonRepository{
    private ObjectMapper objectMapper;
    Data data;
    private String path="src/main/resources/data.json";
    private FileWriter fileWriter;
    public PersonRepositoryImpl() throws StreamReadException, DatabindException, IOException{
    }
    public void loadData() throws StreamReadException, DatabindException, IOException{
        objectMapper=new ObjectMapper();
        this.data=objectMapper.readValue(new File(path),Data.class);
    }

    @Override
    public void postPerson(Person person) throws IOException {
        loadData();
        fileWriter=new FileWriter(path);
        data.persons.add(person);
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter.write(str);
        fileWriter.close();
    }

    @Override
    public void putPerson(Person person) throws IOException {
        loadData();
        fileWriter=new FileWriter(path);
        data.persons.removeIf(i->i.equals(person));
        data.persons.add(person);
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter.write(str);
        fileWriter.close();
    }
    
    @Override
    public void deletePerson(Person person) throws IOException {
        loadData();
        fileWriter=new FileWriter(path);
        data.persons.removeIf(i->i.equals(person));
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter.write(str);
        fileWriter.close();
    }
    @Override
    public Optional<Person> getPersonByNames(String firstName, String lastName) throws JsonProcessingException, IOException {
        loadData();
        return data.persons.stream().filter(i->i.firstName.equals(firstName)&&i.lastName.equals(lastName)).findFirst();
    }
    @Override
    public List<Person> getPersonsByAdress(String address) throws JsonProcessingException, IOException {
        loadData();
        return data.persons.stream().filter(i->i.address.equals(address)).toList();
    }
    public List<Person> getAllPersons() throws JsonProcessingException, IOException{
        loadData();
        return data.persons;
    }
    public List<Person> getPersonsByLastName(String lastName) {
        return data.persons.stream().filter(i->i.lastName.equals(lastName)).toList();
    }
    public List<Person> getPersonByCity(String city) {
        System.out.println(city);
        List<Person> persons=data.persons;
        List<Person> res=new LinkedList<>();
        for(int i=0;i<persons.size();i++){
            if(persons.get(i).city.equals(city)){
                res.add(persons.get(i));
            }
        }

        return res;
    }


}
