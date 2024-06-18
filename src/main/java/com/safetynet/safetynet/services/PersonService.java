package com.safetynet.safetynet.services;

import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.PersonRepository;
import com.safetynet.safetynet.repository.impl.PersonRepositoryImpl;

public class PersonService {
    PersonRepository personRepositoryImpl;
    public void addPerson(Person person) throws StreamReadException, DatabindException, IOException {
        personRepositoryImpl=new PersonRepositoryImpl();
        personRepositoryImpl.postPerson(person);

    }
    public void updateFirestation(Person person) throws StreamReadException, DatabindException, IOException {
        personRepositoryImpl=new PersonRepositoryImpl();
        personRepositoryImpl.putPerson(person);
    }
    public void deletePerson(Person person) throws StreamReadException, DatabindException, IOException {
        personRepositoryImpl=new PersonRepositoryImpl();
        personRepositoryImpl.deletePerson(person);
    }   
}
