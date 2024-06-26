package com.safetynet.safetynet.dto;

import java.util.List;

import com.safetynet.safetynet.model.Person;

public class PersonFirestrationDTO {
    private List<Person> person;
    private int childcount;
    public PersonFirestrationDTO(List<Person> person, int childcount) {
        this.person = person;
        this.childcount = childcount;
    }
    public List<Person> getPerson() {
        return person;
    }
    public void setPerson(List<Person> person) {
        this.person = person;
    }
    public int getChildcount() {
        return childcount;
    }
    public void setChildcount(int childcount) {
        this.childcount = childcount;
    }
    
}
