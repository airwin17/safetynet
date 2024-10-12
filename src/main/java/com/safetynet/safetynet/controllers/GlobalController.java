package com.safetynet.safetynet.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.dto.AdressDTO;
import com.safetynet.safetynet.dto.ChildAlertDTO;
import com.safetynet.safetynet.dto.FireDTO;
import com.safetynet.safetynet.dto.PersonDTO;
import com.safetynet.safetynet.dto.PersonFirestrationDTO;
import com.safetynet.safetynet.services.GlobalService;

import io.swagger.v3.oas.annotations.Operation;
@RestController
public class GlobalController {
    @Autowired
    private GlobalService globalService;
    Logger logger = LoggerFactory.getLogger(GlobalController.class);
    @Operation(summary = "This url should return a list of people covered by the fire station\r\n" + //
                "corresponding. So, if the station number = 1, it should return the inhabitants\r\n" + //
                "covered by the station number 1. The list should include the specific information\r\n" + //
                "below: first name, last name, address, phone number. It should also provide a count\r\n" + //
                "of the number of adults and the number of children (any individual aged 18 years or\r\n" + //
                "less) in the area served.\r")
    @GetMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PersonFirestrationDTO> getFirestation(@RequestParam("stationNumber") String stationNumber) throws IOException, JsonProcessingException{
        logger.info("getFirestation", stationNumber);
        return ResponseEntity.ok(globalService.getFirestationByFirestationNumber(stationNumber));
    }
    @Operation(summary = "This url should return a list of children (any individual aged 18 years or\r\n" + //
                "less) living at this address. The list should include the first name and last name of\r\n" + //
                "each child, their age and a list of other members of the household. If there are no\r\n" + //
                "children, this url can return an empty string.")
    @GetMapping(value ="/childAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<ChildAlertDTO>> getChildAlert(@RequestParam("address") String address) throws IOException{
        logger.info("getChildAlert", address);
        return ResponseEntity.ok(globalService.getChildAlert(address));
    }
    @Operation(summary="This url should return a list of phone numbers of residents served by the fire station.")
    @GetMapping(value ="/phoneAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> gethPhoneAlert(@RequestParam("fireStationNumber") String fireStationNumber) throws IOException{
        logger.info("gethPhoneAlert", fireStationNumber);
        return ResponseEntity.ok(globalService.gethPhoneAlert(fireStationNumber));
    }
    @Operation(summary = "This url should return the list of inhabitants living at the given address, as well as the fire station serving the address.")
    @GetMapping(value ="/fire", produces = MediaType.APPLICATION_JSON_VALUE) 
    @ResponseBody
    public ResponseEntity<FireDTO> getFire(@RequestParam("adress") String adress) throws IOException{
        logger.info("getFire", adress);
        return ResponseEntity.ok(globalService.getFire(adress));
    }
    @Operation(summary = "This url should return a list of all households served by the fire station. This list should group the people by address. It should also include the name, phone number and age of the inhabitants, and list the medical records (medications, dosage and allergies) next to each name.\r")
    @GetMapping(value ="/flood/stations", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<AdressDTO>> getFloodStations(@RequestParam("stationNumber") String[] stationNumber) throws IOException{
        String str="";
        for(int i=0;i<stationNumber.length;i++)
            str+=stationNumber[i]+",";
        logger.info("getFloodStations", str);
        return ResponseEntity.ok(globalService.getFloodStations(stationNumber));
    }
    @Operation(summary="This url should return the name, address, age, email and medical records (medications, dosage and allergies) of each inhabitant. If several people have the same name, they should all appear.\r")
    @GetMapping(value ="/personInfolastName", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<PersonDTO>> getPersonInfolastName(@RequestParam("lastName") String lastName) throws IOException{
        logger.info("getPersonInfolastName", lastName);
        return ResponseEntity.ok(globalService.getPersonInfolastName(lastName));
    }
    @Operation(summary = "This url should return the email addresses of all inhabitants of the city.")
    @GetMapping(value ="/communityEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<String>> getCommunityEmail(@RequestParam String city) throws IOException{
        logger.info("getCommunityEmail", city);
        return ResponseEntity.ok(globalService.getCommunityEmail(city));
    }
}