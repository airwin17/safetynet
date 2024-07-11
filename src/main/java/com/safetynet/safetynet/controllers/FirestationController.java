package com.safetynet.safetynet.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.model.Firestation;
import com.safetynet.safetynet.services.FirestationService;
@RestController
public class FirestationController {
    @Autowired
    FirestationService firestationService;
    
    @PostMapping("/firestation")
    public ResponseEntity<String> postFirestation(@RequestBody Firestation firestation) throws IOException{
        try{
            firestationService.addFirestation(firestation);
        }catch(JsonProcessingException e){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping("/firestation")
    public ResponseEntity<String> putFirestation(@RequestBody Firestation firestation) throws IOException{
        try{
            firestationService.updateFirestation(firestation);
        }catch(JsonProcessingException e){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/firestation")
    public ResponseEntity<String> deleteFirestation(@RequestParam String adress) throws IOException{
        try{
            firestationService.deleteFirestation(adress);
        }catch(JsonProcessingException e){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
}
