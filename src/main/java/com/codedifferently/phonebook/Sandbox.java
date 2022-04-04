package com.codedifferently.phonebook;

import com.codedifferently.phonebook.person.models.Person;

public class Sandbox {
    public static void main(String[] args) {
        Person person = new Person();
        person.setFirstName("Mike");
        person.setLastName("Jones");

        System.out.println(person.getFullName());
    }
}
