package com.codedifferently.phonebook.person.exceptions;

public class PersonNotFoundException extends Exception{
    public PersonNotFoundException (String msg){
        super(msg);
    }
}
