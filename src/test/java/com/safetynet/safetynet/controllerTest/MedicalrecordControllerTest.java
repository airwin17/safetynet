package com.safetynet.safetynet.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.model.Medicalrecord;
import com.safetynet.safetynet.util.Data;
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MedicalrecordControllerTest {
private ObjectMapper objectMapper=new ObjectMapper();
@Autowired
private MockMvc mockMvc;
@BeforeEach
public void resetdatabase() throws IOException{
    Data data=objectMapper.readValue(new File("data2.json"), Data.class);
    String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    FileWriter fileWriter=new FileWriter("src/main/resources/data.json");
    fileWriter.write(str);
    fileWriter.close();
}
public Data getData() throws StreamReadException, DatabindException, IOException{
    Data data=objectMapper.readValue(new File("src/main/resources/data.json"), Data.class);
    return data;
}
@Test
public void postMedicalrocordTest() throws Exception{
    Medicalrecord medicalrecord=new Medicalrecord("test","test","test");
    int datasize=getData().medicalrecords.size();
    mockMvc.perform(MockMvcRequestBuilders
    .post("/medicalrecord")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(medicalrecord)));
    assertEquals(datasize+1, getData().medicalrecords.size());
}
@Test
public void putMedicalrocordTest() throws Exception{
    
    int datasize=getData().medicalrecords.size();
    Medicalrecord medicalrecord=new Medicalrecord("John","Boyd","3");
    //MedicalrecordRepository medicalrecordRepository=new MedicalrecordRepositoryImpl();
    mockMvc.perform(MockMvcRequestBuilders
    .put("/medicalrecord")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(medicalrecord)));
    assertEquals("3", getData().medicalrecords.get(datasize-1).birthdate);
}
@Test
public void deleteMedicalrocordTest() throws Exception{
    Medicalrecord medicalrecord=new Medicalrecord("John","Boyd","test");
    int datasize=getData().medicalrecords.size();
    mockMvc.perform(MockMvcRequestBuilders
    .delete("/medicalrecord")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(medicalrecord)));
    assertEquals(datasize-1, getData().medicalrecords.size());
}
}

