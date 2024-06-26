package com.safetynet.safetynet.dto;

import java.util.List;

public class AdressDTO {
    private List<PersonDTO> persons;

    public AdressDTO(List<PersonDTO> persons) {
        this.persons = persons;
    }

    public List<PersonDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }

}
