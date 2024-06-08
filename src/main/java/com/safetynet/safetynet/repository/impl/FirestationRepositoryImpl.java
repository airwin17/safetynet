package com.safetynet.safetynet.repository.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.Data.Data;
import com.safetynet.safetynet.model.Firestation;
import com.safetynet.safetynet.repository.FirestationRepository;

public class FirestationRepositoryImpl implements FirestationRepository{
    private ObjectMapper objectMapper;
    Data data;
    @SuppressWarnings("unused")
    private FileWriter fileWriter;
    public FirestationRepositoryImpl() throws StreamReadException, DatabindException, IOException{
        objectMapper=new ObjectMapper();
        data=objectMapper.readValue(new File("src/main/resources/data.json"),Data.class);
        fileWriter=new FileWriter("src/main/resources/data.json");
    }
    @Override
    public void postFirestation(Firestation firestation) throws JsonProcessingException {
        data.firestations.add(firestation);
        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    @Override
    public void putFirestation(Firestation firestation) {
        data.firestations.removeIf(i->i.equals(firestation));
        data.firestations.add(firestation);
    }

    @Override
    public void deleteFirestation(Firestation firestation) {
        data.firestations.removeIf(i->i.equals(firestation));
    }
    @Override
    public String getFirestationByAdress(String adress) throws JsonProcessingException, IOException {
        return data.firestations.stream().filter(i->i.address.equals(adress)).findFirst().get().station;
    }
    @Override
    public List<String> getFirestationByStation(String station) throws JsonProcessingException, IOException {
        return data.firestations.stream().filter(i->i.station.equals(station)).map(i-> i.address).toList();
    }

}
