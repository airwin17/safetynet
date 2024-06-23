package com.safetynet.safetynet.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.safetynet.services.GlobalService;
@RestController
public class GlobalController {
    private GlobalService globalService=new GlobalService();
    Logger logger = LoggerFactory.getLogger(GlobalController.class);
    @GetMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<JsonNode> getFirestation(@RequestParam("stationNumber") String stationNumber) throws IOException, JsonProcessingException{
        logger.info("getFirestation", stationNumber);
        return ResponseEntity.ok(globalService.getFirestationByFirestationNumber(stationNumber));
    }
    @GetMapping(value ="/childAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<JsonNode> getChildAlert(@RequestParam("address") String address) throws IOException{
        logger.info("getChildAlert", address);
        return ResponseEntity.ok(globalService.getChildAlert(address));
    }
    @GetMapping(value ="/phoneAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> gethPhoneAlert(@RequestParam("fireStationNumber") String fireStationNumber) throws IOException{
        logger.info("gethPhoneAlert", fireStationNumber);
        return ResponseEntity.ok(globalService.gethPhoneAlert(fireStationNumber));
    }
    @GetMapping(value ="/fire", produces = MediaType.APPLICATION_JSON_VALUE) 
    @ResponseBody
    public ResponseEntity<JsonNode> getFire(@RequestParam("adress") String adress) throws IOException{
        logger.info("getFire", adress);
        return ResponseEntity.ok(globalService.getFire(adress));
    }
    @GetMapping(value ="/flood/stations", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<JsonNode> getFloodStations(@RequestParam("stationNumber") String[] stationNumber) throws IOException{
        String str="";
        for(int i=0;i<stationNumber.length;i++)
            str+=stationNumber[i]+",";
        logger.info("getFloodStations", str);
        return ResponseEntity.ok(globalService.getFloodStations(stationNumber));
    }
    @GetMapping(value ="/personInfolastName", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<JsonNode> getPersonInfolastName(@RequestParam("lastName") String lastName) throws IOException{
        logger.info("getPersonInfolastName", lastName);
        return ResponseEntity.ok(globalService.getPersonInfolastName(lastName));
    }
    @GetMapping(value ="/communityEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<JsonNode> getCommunityEmail(@RequestParam String city) throws IOException{
        logger.info("getCommunityEmail", city);
        return ResponseEntity.ok(globalService.getCommunityEmail(city));
    }
}
