package com.safetynet.safetynet.services;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.model.Firestation;
import com.safetynet.safetynet.repository.FirestationRepository;
import com.safetynet.safetynet.repository.impl.FirestationRepositoryImpl;

public class FirestationService {
    FirestationRepository firestationRepositoryImpl;
    public void addFirestation(Firestation firestation) throws JsonProcessingException, IOException {
        firestationRepositoryImpl=new FirestationRepositoryImpl();
        firestationRepositoryImpl.postFirestation(firestation);
    }
    public void updateFirestation(Firestation firestation) throws JsonProcessingException, IOException {
        firestationRepositoryImpl=new FirestationRepositoryImpl();
        firestationRepositoryImpl.putFirestation(firestation);
    }
    public void deleteFirestation(String adress) throws JsonProcessingException, IOException {
        firestationRepositoryImpl=new FirestationRepositoryImpl();
        firestationRepositoryImpl.deleteFirestation(adress);
    }
}
