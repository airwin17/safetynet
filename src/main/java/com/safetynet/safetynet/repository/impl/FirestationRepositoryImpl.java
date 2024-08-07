package com.safetynet.safetynet.repository.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.model.Firestation;
import com.safetynet.safetynet.repository.FirestationRepository;
import com.safetynet.safetynet.util.Data;
@Repository
public class FirestationRepositoryImpl implements FirestationRepository{
    private ObjectMapper objectMapper;
    Data data;
    private String path="src/main/resources/data.json";
    private FileWriter fileWriter;
    public FirestationRepositoryImpl() throws StreamReadException, DatabindException, IOException{
    }
    public void loadData() throws StreamReadException, DatabindException, IOException{
        objectMapper=new ObjectMapper();
        this.data=objectMapper.readValue(new File(path),Data.class);
    }

    @Override
    public void postFirestation(Firestation firestation) throws IOException {
        loadData();
        data.firestations.add(firestation);
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter=new FileWriter(path);
        fileWriter.write(str);
        fileWriter.close();
    }
    @Override
    public void putFirestation(Firestation firestation) throws IOException {
        loadData();
        data.firestations.removeIf(i->i.equals(firestation));
        data.firestations.add(firestation);
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter=new FileWriter(path);
        fileWriter.write(str);
        fileWriter.close();
    }

    @Override
    public void deleteFirestation(String adress) throws IOException {
        loadData();
        data.firestations.removeIf(i->i.address.equals(adress));
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter=new FileWriter(path);
        fileWriter.write(str);
        fileWriter.close();
    }
    @Override
    public String getFirestationByAdress(String adress) throws JsonProcessingException, IOException {
        loadData();
        return data.firestations.stream().filter(i->i.address.equals(adress)).findFirst().get().station;
    }
    @Override
    public List<String> getFirestationByStation(String station) throws JsonProcessingException, IOException {
        loadData();
        return data.firestations.stream().filter(i->i.station.equals(station)).map(i-> i.address).toList();
    }

}
