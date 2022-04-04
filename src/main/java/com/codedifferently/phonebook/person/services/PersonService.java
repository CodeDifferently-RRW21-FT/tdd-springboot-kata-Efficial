package com.codedifferently.phonebook.person.services;

import com.codedifferently.phonebook.person.exceptions.PersonNotFoundException;
import com.codedifferently.phonebook.person.models.Person;


import java.util.List;

public interface PersonService{

    Person create(Person person);
    Person getPersonById(Integer id) throws PersonNotFoundException;
    List<Person> getAllPersons();
    Person getPersonByFullName(List<Person> people);
    Person updatePerson(Integer id, Person person ) throws PersonNotFoundException;
    Boolean deletePerson(Integer id) throws PersonNotFoundException;
}
