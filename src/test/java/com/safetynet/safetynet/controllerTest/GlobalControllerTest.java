package com.safetynet.safetynet.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.safetynet.controllers.GlobalController;
import com.safetynet.safetynet.util.Data;
@WebMvcTest(GlobalController.class)
public class GlobalControllerTest {
    private static ObjectMapper objectMapper=new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
public void resetdatabase() throws IOException{
    mockMvc=MockMvcBuilders.standaloneSetup(new GlobalController()).build();
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
public void getFirestationTest() throws Exception{
    MvcResult ar=mockMvc.perform(MockMvcRequestBuilders
        .get("/firestation")
        .param("stationNumber","1")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
    String result=ar.getResponse().getContentAsString();
    System.out.println(result);
        JsonNode people=objectMapper.readTree(result);
        assertEquals(6,((ObjectNode) people).withArray("person").size());
        assertEquals("1", people.get("childcount").toString());
}
@Test
public void getChildAlertTest() throws Exception{
    MvcResult ar=mockMvc.perform(MockMvcRequestBuilders
        .get("/childAlert")
        .param("address","1509 Culver St")
        .accept(MediaType.APPLICATION_JSON)).andReturn();
    String result=ar.getResponse().getContentAsString();
        JsonNode people=objectMapper.readTree(result);
        System.out.println(people);
        assertEquals(2,((ArrayNode) people).size());
}
@Test
public void gethPhoneAlertTest() throws Exception{
    MvcResult ar=mockMvc.perform(MockMvcRequestBuilders
        .get("/phoneAlert")
        .param("fireStationNumber","1")
        .accept(MediaType.APPLICATION_JSON)).andReturn();
    String result=ar.getResponse().getContentAsString();
        JsonNode people=objectMapper.readTree(result);
        assertEquals(6,((ArrayNode) people).size());
    }

    @Test
    public void getFireTest() throws Exception{
        String response=mockMvc.perform(MockMvcRequestBuilders
            .get("/fire")
            .param("adress","1509 Culver St")
            .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse().getContentAsString();
            ObjectNode people=(ObjectNode)objectMapper.readTree(response);
            assertEquals(5,((ArrayNode)people.get("people")).size());
    }
   @Test
public void getFloodTest() throws Exception{
    MvcResult ar=mockMvc.perform(MockMvcRequestBuilders
        .get("/flood/stations")
        .param("stationNumber","1")
        .param("stationNumber","2")
        .accept(MediaType.APPLICATION_JSON)).andReturn();
    String result=ar.getResponse().getContentAsString();
        ArrayNode people=(ArrayNode)objectMapper.readTree(result);
        assertEquals(6,people.size());
}
@Test
public void getPersonInfolastNameTest() throws Exception{
    String response=mockMvc.perform(MockMvcRequestBuilders
        .get("/personInfolastName")
        .param("lastName","Cooper")
        .accept(MediaType.APPLICATION_JSON))
        .andReturn().getResponse().getContentAsString();
        ArrayNode people=(ArrayNode)objectMapper.readTree(response);
        assertEquals(2,people.size());
}
@Test
public void getCommunityEmailTest() throws Exception{
    String response=mockMvc.perform(MockMvcRequestBuilders
        .get("/communityEmail")
        .param("city","Culver")
        .accept(MediaType.APPLICATION_JSON))
        .andReturn().getResponse().getContentAsString();
        ArrayNode people=(ArrayNode)objectMapper.readTree(response);
        assertEquals(23,people.size());
}
}

