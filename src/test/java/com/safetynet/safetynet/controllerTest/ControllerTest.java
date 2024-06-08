package com.safetynet.safetynet.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.Data.Data;
import com.safetynet.safetynet.controllers.CrudController;
import com.safetynet.safetynet.model.Person;
import com.safetynet.safetynet.repository.impl.PersonRepositoryImpl;

public class ControllerTest {
    @Autowired PersonRepositoryImpl personDAO;
    @Autowired public static CrudController controller;
    private static ObjectMapper objectMapper=new ObjectMapper();
    private static MockMvc mockMvc;
@BeforeAll
public static void resetdatabase() throws IOException{
    mockMvc=MockMvcBuilders.standaloneSetup(new CrudController()).build();
    
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
public void postPersonTest() throws Exception{
    int datasize=getData().persons.size();
    Person person=new Person("rtyu","poiu");
    mockMvc.perform(MockMvcRequestBuilders
        .post("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(person))
        );
    assertEquals(datasize+1, getData().persons.size());
}
@Test
public void putPersonTest() throws Exception{
    int datasize=getData().persons.size();
    Person person=new Person("John","Boyd");
    person.address="kmin";
    mockMvc.perform(MockMvcRequestBuilders
        .put("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(person))
        );
    assertEquals("kmin", getData().persons.stream().filter(i->i.equals(person)).findFirst().get().address);
}
@Test
public void deletePersonTest() throws Exception{
    int datasize=getData().persons.size();
    Person person=new Person("John","Boyd");
    mockMvc.perform(MockMvcRequestBuilders
        .delete("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(person))
        );
    assertEquals(datasize-1, getData().persons.size());
}
}
