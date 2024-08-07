package com.safetynet.safetynet.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.model.Firestation;
@Component
public interface FirestationRepository {
    public void postFirestation(Firestation firestation) throws JsonProcessingException, IOException;
    public void putFirestation(Firestation firestation)throws JsonProcessingException, IOException;
    public void deleteFirestation(String adress)throws JsonProcessingException, IOException;
    public String getFirestationByAdress(String adress) throws JsonProcessingException, IOException;
    public List<String> getFirestationByStation(String station) throws JsonProcessingException, IOException;
}
