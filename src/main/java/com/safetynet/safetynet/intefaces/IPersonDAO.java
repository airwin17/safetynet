package com.safetynet.safetynet.intefaces;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.Model.Person;

public interface IPersonDAO {
    
    public void postPerson(Person person) throws JsonProcessingException, IOException;
    public void putPerson(Person person) throws JsonProcessingException, IOException;
    public void deletePerson(Person person) throws JsonProcessingException, IOException;
    public Optional<Person> getPersonByNames(String firstName, String lastName)throws JsonProcessingException, IOException;
    public List<Person> getPersonsByAdress(String address) throws JsonProcessingException, IOException;
}
