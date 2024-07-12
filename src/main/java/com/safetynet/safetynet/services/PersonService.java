package com.safetynet.safetynet.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.PersonRepository;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepositoryImpl;
    public void addPerson(Person person) throws StreamReadException, DatabindException, IOException {
        
        personRepositoryImpl.postPerson(person);

    }
    public void updateFirestation(Person person) throws StreamReadException, DatabindException, IOException {
        
        personRepositoryImpl.putPerson(person);
    }
    public void deletePerson(Person person) throws StreamReadException, DatabindException, IOException {
       
        personRepositoryImpl.deletePerson(person);
    }   
}
