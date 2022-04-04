package com.codedifferently.phonebook.person.controllers;

import com.codedifferently.phonebook.person.exceptions.PersonNotFoundException;
import com.codedifferently.phonebook.person.models.Person;
import com.codedifferently.phonebook.person.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/person")
    public class PersonController {

        private final Logger logger = (Logger) LoggerFactory.getLogger(PersonController.class);
        private PersonService personService;

        @Autowired
        public PersonController(PersonService personService) {
            this.personService = personService;
        }

        @PostMapping("")
        public ResponseEntity<Person> createPersonRequest(@RequestBody Person person){
            Person savedPerson = personService.create(person);
            ResponseEntity response = new ResponseEntity(savedPerson, HttpStatus.CREATED);
            return response;
        }

        @GetMapping("")
        public ResponseEntity<List<Person>> getAllPerson(){
            List<Person> people = personService.getAllPersons();
            ResponseEntity<List<Person>> response = new ResponseEntity<>(people, HttpStatus.CREATED);
            return response;
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> getProfileById(@PathVariable Integer id){
            try{
            Person person =   personService.getPersonById(id);
            ResponseEntity<?> response = new ResponseEntity<>(person, HttpStatus.OK);
            return response;
        }catch (PersonNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

        @PutMapping("/{id}")
        public ResponseEntity<?> updateProfile(@PathVariable Integer id, @RequestBody Person person){
            try{
                Person updatedPerson = personService.updatePerson(id,person);
                ResponseEntity response = new ResponseEntity(updatedPerson, HttpStatus.OK);
                return response;
            }catch (PersonNotFoundException e) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }

        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteProfile(@PathVariable Integer id){
            try{
                personService.deletePerson(id);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .build();
            }catch (PersonNotFoundException e) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
        }
}
