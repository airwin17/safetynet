package com.safetynet.safetynet.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.model.Medicalrecord;
import com.safetynet.safetynet.services.MedicalrecordService;



@RestController
public class MedicalrecordController {
    MedicalrecordService medicalrecordService=new MedicalrecordService();
    @PostMapping("/medicalrecord")
    public ResponseEntity<String> postMedicalrocord(@RequestBody Medicalrecord medicalrecord) throws IOException{
        try{
            medicalrecordService.addMedicalrecord(medicalrecord);
        }catch(JsonProcessingException e){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping("/medicalrecord")
    public ResponseEntity<String> putMedicalrocord(@RequestBody Medicalrecord medicalrecord) throws IOException{
        try{
            medicalrecordService.updateMedicalrecord(medicalrecord);
        }catch(JsonProcessingException e){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/medicalrecord")
    public ResponseEntity<String> deleteMedicalrocord(@RequestBody Medicalrecord medicalrecord) throws IOException{
        try{
            medicalrecordService.deleteMedicalrecord(medicalrecord);
        }catch(JsonProcessingException e){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
}
