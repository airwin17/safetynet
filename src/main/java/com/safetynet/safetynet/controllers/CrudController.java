package com.safetynet.safetynet.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.Data.FirestationDAO;
import com.safetynet.safetynet.Data.MedicalrecordDAO;
import com.safetynet.safetynet.Data.PersonDAO;
import com.safetynet.safetynet.Model.Firestation;
import com.safetynet.safetynet.Model.Medicalrecord;
import com.safetynet.safetynet.Model.Person;


@RestController
public class CrudController {
    
    private PersonDAO personDAO;
    private FirestationDAO firestationDAO;
    private MedicalrecordDAO medicalrecordDAO;
    @PostMapping("/person") 
    public ResponseEntity<String> addPerson(@RequestBody Person person) throws IOException{
        try {
            personDAO=new PersonDAO();
            personDAO.postPerson(person);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping("/person")
    public ResponseEntity<String> putPerson(@RequestBody Person person) throws IOException{

        try {
            personDAO=new PersonDAO();
            personDAO.putPerson(person);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/person")
    public ResponseEntity<String> deletePerson(@RequestBody Person person) throws IOException{
        try {
            personDAO=new PersonDAO();
            personDAO.deletePerson(person);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/firestation")
    public ResponseEntity<String> postFirestation(@RequestBody Firestation firestation) throws IOException{
        try{
            firestationDAO=new FirestationDAO();
            firestationDAO.postFirestation(firestation);
        }catch(JsonProcessingException e){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping("/firestation")
    public ResponseEntity<String> putFirestation(@RequestBody Firestation firestation) throws IOException{
        try{
            firestationDAO=new FirestationDAO();
            firestationDAO.putFirestation(firestation);
        }catch(JsonProcessingException e){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/firestation")
    public ResponseEntity<String> deleteFirestation(@RequestBody Firestation firestation) throws IOException{
        try{
            firestationDAO=new FirestationDAO();
            firestationDAO.deleteFirestation(firestation);
        }catch(JsonProcessingException e){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/medicalrecord")
    public ResponseEntity<String> postMedicalrocord(@RequestBody Medicalrecord medicalrecord) throws IOException{
        try{
            medicalrecordDAO=new MedicalrecordDAO();
            medicalrecordDAO.postMedicalrecord(medicalrecord);
        }catch(JsonProcessingException e){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping("/medicalrecord")
    public ResponseEntity<String> putMedicalrocord(@RequestBody Medicalrecord medicalrecord) throws IOException{
        try{
            medicalrecordDAO=new MedicalrecordDAO();
            medicalrecordDAO.putMedicalrecord(medicalrecord);
        }catch(JsonProcessingException e){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/medicalrecord")
    public ResponseEntity<String> deleteMedicalrocord(@RequestBody Medicalrecord medicalrecord) throws IOException{
        try{
            medicalrecordDAO=new MedicalrecordDAO();
            medicalrecordDAO.deleteMedicalrecord(medicalrecord);
        }catch(JsonProcessingException e){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
}
