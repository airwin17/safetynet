package com.safetynet.safetynet.dto;

import java.util.List;

public class FireDTO {
    private String firestation;
    private List<PersonDTO> people;
    public FireDTO(String firestation, List<PersonDTO> people) {
        this.firestation = firestation;
        this.people = people;
    }
    public String getAddress() {
        return firestation;
    }
    public void setAddress(String address) {
        this.firestation = address;
    }
    public List<PersonDTO> getPeople() {
        return people;
    }
    public void setPeople(List<PersonDTO> people) {
        this.people = people;
    }
    
    
}
