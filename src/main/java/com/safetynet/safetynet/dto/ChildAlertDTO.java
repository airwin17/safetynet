package com.safetynet.safetynet.dto;

import java.util.List;

import com.safetynet.safetynet.model.Person;

public class ChildAlertDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private int age;
    private List<Person> familyMembers;
    public ChildAlertDTO(List<Person> familyMembers,Person person,int age) {
        firstName = person.firstName;
        lastName = person.lastName;
        address = person.address;
        city = person.city;
        zip = person.zip;
        phone = person.phone;
        email = person.email;
        this.age = age;
        this.familyMembers = familyMembers;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public List<Person> getFamilyMembers() {
        return familyMembers;
    }
    public void setFamilyMembers(List<Person> familyMembers) {
        this.familyMembers = familyMembers;
    }
    
}
