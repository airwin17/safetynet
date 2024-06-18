package com.safetynet.safetynet.controllers;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.services.GlobalService;
 
@RestController
public class GlobalController {
    private GlobalService globalService=new GlobalService();
    
    @GetMapping("/firestation")
    public ResponseEntity<String> getFirestation(@RequestParam("stationNumber") String stationNumber) throws IOException, JsonProcessingException{
        return ResponseEntity.ok(globalService.getFirestationByFirestationNumber(stationNumber));
    }
    @GetMapping("/childAlert")
    public ResponseEntity<String> getChildAlert(@RequestParam("address") String address) throws IOException{
        return ResponseEntity.ok(globalService.getChildAlert(address));
    }
    @GetMapping("/phoneAlert")
    public ResponseEntity<String> gethPhoneAlert(@RequestParam("fireStationNumber") String fireStationNumber) throws IOException{
        return ResponseEntity.ok(globalService.gethPhoneAlert(fireStationNumber));
    }
    @GetMapping("/fire") 
    public ResponseEntity<String> getFire(@RequestParam("adress") String adress) throws IOException{
        return ResponseEntity.ok(globalService.getFire(adress));
    }
    @GetMapping("/flood/stations")
    public ResponseEntity<String> getFloodStations(@RequestParam("stationNumber") String[] stationNumber) throws IOException{
        return ResponseEntity.ok(globalService.getFloodStations(stationNumber));
    }
    @GetMapping("/personInfolastName")
    public ResponseEntity<String> getPersonInfolastName(@RequestParam("lastName") String lastName) throws IOException{
        return ResponseEntity.ok(globalService.getPersonInfolastName(lastName));
    }
    @GetMapping("/communityEmail")
    public ResponseEntity<String> getCommunityEmail(@RequestParam String city) throws IOException{
        return ResponseEntity.ok(globalService.getCommunityEmail(city));
    }
}
