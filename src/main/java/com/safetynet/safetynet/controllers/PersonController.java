package com.safetynet.safetynet.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.services.PersonService;
@RestController
public class PersonController {
    @Autowired
    PersonService personService;
    @PostMapping("/person") 
    public ResponseEntity<String> addPerson(@RequestBody Person person) throws IOException{
        try {
            personService.addPerson(person);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping("/person")
    public ResponseEntity<String> putPerson(@RequestBody Person person) throws IOException{

        try {
            personService.updateFirestation(person);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/person")
    public ResponseEntity<String> deletePerson(@RequestBody Person person) throws IOException{
        try {
            personService.deletePerson(person);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
