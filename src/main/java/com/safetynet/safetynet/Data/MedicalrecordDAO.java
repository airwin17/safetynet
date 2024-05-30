package com.safetynet.safetynet.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.Model.Medicalrecord;
import com.safetynet.safetynet.intefaces.IMedicalrecordDAO;

public class MedicalrecordDAO implements IMedicalrecordDAO{
    private ObjectMapper objectMapper;
    Data data;
    private FileWriter fileWriter;
    public MedicalrecordDAO() throws StreamReadException, DatabindException, IOException{
        objectMapper=new ObjectMapper();
        data=objectMapper.readValue(new File("src/main/resources/data.json"),Data.class);
        fileWriter=new FileWriter("src/main/resources/data.json");
    }
    @Override
    public void postMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException,IOException {
        data.medicalrecords.add(medicalrecord);
        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter.write(str);
        fileWriter.close();
    }

    @Override
    public void putMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException,IOException {
        data.medicalrecords.removeIf(i->i.equals(medicalrecord));
        data.medicalrecords.add(medicalrecord);
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter.write(str);
        fileWriter.close();
    }

    @Override
    public void deleteMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException,IOException {
        data.medicalrecords.removeIf(i->i.equals(medicalrecord));
        String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        fileWriter.write(str);
        fileWriter.close();
    }

}
