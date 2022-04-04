package com.codedifferently.phonebook.person.services;

import com.codedifferently.phonebook.person.exceptions.PersonNotFoundException;
import com.codedifferently.phonebook.person.models.Person;
import com.codedifferently.phonebook.person.repos.PersonRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private static Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    private PersonRepo personRepo;


    @Autowired
    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }



    @Override
    public Person create(Person person) {
        Person savedPerson = personRepo.save(person);
        return savedPerson;
    }

    @Override
    public Person getPersonById(Integer id) throws PersonNotFoundException {
        Optional<Person> personOptional = personRepo.findById(id);
        if (personOptional.isEmpty()) {
            logger.error("Person with id {} does not exist", id);
            throw new PersonNotFoundException("Person Not Found");
        }
        return personOptional.get();
    }

    @Override
    public Person getPersonByFullName(List<Person> people) {
        return null;
    }

    @Override
    public List<Person> getAllPersons() {
        return (List<Person>) personRepo.findAll();
    }

    @Override
    public Person updatePerson(Integer id, Person person) throws PersonNotFoundException {
        Optional<Person> personOptional = personRepo.findById(id);
        if (personOptional.isEmpty()) {
            throw new PersonNotFoundException("Person does not exists, can not update");
        }
        Person savedPerson = personOptional.get();
        savedPerson.setPhoneNumbers(person.getPhoneNumbers());
        savedPerson.setFirstName(person.getFirstName());
        savedPerson.setLastName(person.getLastName());


        return personRepo.save(savedPerson);
    }

    @Override
    public Boolean deletePerson(Integer id) throws PersonNotFoundException {
        Optional<Person> personOptional = personRepo.findById(id);
        if (personOptional.isEmpty()) {
            throw new PersonNotFoundException("Person does not exist, can not delete");
        }
        Person personToDelete = personOptional.get();
        personRepo.delete(personToDelete);
        return true;
    }
}