package com.safetynet.safetynet.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.model.Firestation;
import com.safetynet.safetynet.repository.FirestationRepository;
@Service
public class FirestationService {
    @Autowired
    FirestationRepository firestationRepositoryImpl;
    public void addFirestation(Firestation firestation) throws JsonProcessingException, IOException {
        
        firestationRepositoryImpl.postFirestation(firestation);
    }
    public void updateFirestation(Firestation firestation) throws JsonProcessingException, IOException {
        
        firestationRepositoryImpl.putFirestation(firestation);
    }
    public void deleteFirestation(String adress) throws JsonProcessingException, IOException {
        
        firestationRepositoryImpl.deleteFirestation(adress);
    }
}
