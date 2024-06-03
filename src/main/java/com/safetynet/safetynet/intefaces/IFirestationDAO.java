package com.safetynet.safetynet.intefaces;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.Model.Firestation;

public interface IFirestationDAO {
    public void postFirestation(Firestation firestation) throws JsonProcessingException, IOException;
    public void putFirestation(Firestation firestation)throws JsonProcessingException, IOException;
    public void deleteFirestation(Firestation firestation)throws JsonProcessingException, IOException;
    public String getFirestationByAdress(String adress) throws JsonProcessingException, IOException;
    public List<String> getFirestationByStation(String station) throws JsonProcessingException, IOException;
}
