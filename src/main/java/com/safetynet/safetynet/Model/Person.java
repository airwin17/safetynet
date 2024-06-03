package com.safetynet.safetynet.Model;

public class Person {
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String zip;
    public String phone;
    public String email;
    public Person(
        String firstName,
        String lastName){
        this.firstName=firstName;
        this.lastName=lastName;
    }
    public Person(){

    }
    @Override
    public String toString(){
        return firstName+lastName;
    }
    @Override
    public boolean equals(Object person){
        if(person instanceof Person){
            Person person0=(Person) person;
            if(this.firstName.equals(person0.firstName)&this.lastName.equals(person0.lastName)){
                return true;
            }else return false;
        }
        else return false;
    }
    
}
