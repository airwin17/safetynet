package com.safetynet.safetynet.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.controllers.FirestationController;
import com.safetynet.safetynet.model.Firestation;
import com.safetynet.safetynet.util.Data;

public class FirestationControllerTest {
    private static ObjectMapper objectMapper=new ObjectMapper();
    private static MockMvc mockMvc;
@BeforeEach
public void resetdatabase() throws IOException{
    mockMvc=MockMvcBuilders.standaloneSetup(new FirestationController()).build();
    Data data=objectMapper.readValue(new File("data2.json"), Data.class);
    String str=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    FileWriter fileWriter=new FileWriter("src/main/resources/data.json");
    fileWriter.write(str);
    fileWriter.close();
}
public Data getData() throws StreamReadException, DatabindException, IOException{
    ObjectMapper objectMappers=new ObjectMapper();
    Data data=objectMappers
    .readValue(new File("src/main/resources/data.json"), Data.class);
    return data;
}
@Test 
public void getFirestationTest() throws Exception{
    int datasize=getData().firestations.size();
    Firestation firestation=new Firestation("1509 Culver St","3");
    mockMvc.perform(MockMvcRequestBuilders
    .post("/firestation")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(firestation)));
    int afterdatasize=getData().firestations.size();
    assertEquals(datasize+1,afterdatasize);
}

@Test
public void putFirestationTest() throws Exception{
    Firestation firestation=new Firestation("1509 Culver St","5");
    int datasize=getData().firestations.size();
    mockMvc.perform(MockMvcRequestBuilders
    .put("/firestation")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(firestation)));
    assertEquals("5",getData().firestations.get(datasize-1).station);
}
@Test
public void deleteFirestation()throws Exception{
    int datasize=getData().firestations.size();
    mockMvc.perform(MockMvcRequestBuilders
        .delete("/firestation")
        .contentType(MediaType.TEXT_PLAIN)
        .param("adress","1509 Culver St")
    );
    assertEquals(datasize-1, getData().firestations.size());
    }
}
