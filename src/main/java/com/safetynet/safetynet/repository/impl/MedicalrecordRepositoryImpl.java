package com.safetynet.safetynet.repository.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.model.Medicalrecord;
import com.safetynet.safetynet.repository.MedicalrecordRepository;
import com.safetynet.safetynet.util.Data;
@Repository
public class MedicalrecordRepositoryImpl implements MedicalrecordRepository{
    private ObjectMapper objectMapper;
    Data data;
    private String path="src/main/resources/data.json";
    private FileWriter fileWriter;
    public MedicalrecordRepositoryImpl() {
        
    }
    public void loadData() throws StreamReadException, DatabindException, IOException{
        objectMapper=new ObjectMapper();
        this.data=objectMapper.readValue(new File("src/main/resources/data.json"),Data.class);
    }
    @Override
    public void postMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException,IOException {
        loadData();
        data.medicalrecords.add(medicalrecord);
        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter=new FileWriter(path);
        fileWriter.write(str);
        fileWriter.close();
    }
    @Override
    public void putMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException,IOException {
        loadData();
        data.medicalrecords.removeIf(i->i.equals(medicalrecord));
        data.medicalrecords.add(medicalrecord);
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter=new FileWriter(path);
        fileWriter.write(str);
        fileWriter.close();
    }
    @Override
    public void deleteMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException,IOException {
        loadData();
        data.medicalrecords.removeIf(i->i.equals(medicalrecord));
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter=new FileWriter(path);
        fileWriter.write(str);
        fileWriter.close();
    }
    @Override
    public Optional<Medicalrecord> getMedicalrecordByNames(String firstName, String lastName) throws StreamReadException, DatabindException, IOException {
        loadData();
        return data.medicalrecords.stream().filter(i->i.firstName.equals(firstName)&&i.lastName.equals(lastName)).findFirst();
    }
}
