package com.codedifferently.phonebook.person.services;


import com.codedifferently.phonebook.person.exceptions.PersonNotFoundException;
import com.codedifferently.phonebook.person.models.Person;
import com.codedifferently.phonebook.person.models.PhoneNumbers;
import com.codedifferently.phonebook.person.repos.PersonRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    @MockBean
    private PersonRepo mockPersonRepo;

    @Autowired
    private PersonService personService;

    private Person inputPerson;
    private Person mockPerson1;
    private Person mockPerson2;

    @BeforeEach
    public void setUp() {
        List<PhoneNumbers> numbersList = new ArrayList<>();
        numbersList.add(new PhoneNumbers(1, "302.123.4567", true));
        numbersList.add(new PhoneNumbers(2, "302.345.6789", true));
        inputPerson = new Person("Will", "Smith", numbersList);

        mockPerson1 = new Person("Fresh", "Prince", numbersList);
        mockPerson1.setId(1);

        mockPerson2 = new Person("Unlce", "Phil", numbersList);
        mockPerson2.setId(2);
    }

    @Test
    @DisplayName("Person Service: Create Person - Success")
    public void createPersonTestSuccess(){
        BDDMockito.doReturn(mockPerson1).when(mockPersonRepo).save(ArgumentMatchers.any());
        Person foundPerson = personService.create(inputPerson);
        Assertions.assertNotNull(foundPerson, "Person should not be null");
        Assertions.assertEquals(foundPerson.getId(), 1);
    }

    @Test
    @DisplayName("Person Service: Get Person by Id - Success")
    public void getPersonByIdTestSuccess() throws PersonNotFoundException {
        BDDMockito.doReturn(Optional.of(mockPerson1)).when(mockPersonRepo).findById(1);
        Person foundPerson = personService.getPersonById(1);
        Assertions.assertEquals(mockPerson1.toString(), foundPerson.toString());
    }

    @Test
    @DisplayName("Person Service: Get Person by Id - Fail")
    public void getPersonByIdTestFailed() throws PersonNotFoundException {
        BDDMockito.doReturn(Optional.empty()).when(mockPersonRepo).findById(1);
        Assertions.assertThrows(PersonNotFoundException.class, () -> {
            personService.getPersonById(1);
        });
    }

    @Test
    @DisplayName("Person Service: Get All Persons - Success")
    public void getAllPersonsTestSuccess(){
        List<Person> personList = new ArrayList<>();
        personList.add(mockPerson1);
        personList.add(mockPerson2);

        BDDMockito.doReturn(personList).when(mockPersonRepo).findAll();

        List<Person> responsePersons = personService.getAllPersons();
        Assertions.assertIterableEquals(personList, responsePersons);
    }

    @Test
    @DisplayName("Person Service: Update Person - Success")
    public void updatePersonTestSuccess() throws PersonNotFoundException{
        List<PhoneNumbers> numbersList = new ArrayList<>();
        numbersList.add(new PhoneNumbers(1, "302.123.4567", true));
        numbersList.add(new PhoneNumbers(2, "302.345.6789", true));
        Person expectedPersonUpdate = new Person("After", "Update", numbersList);

        BDDMockito.doReturn(Optional.of(mockPerson1)).when(mockPersonRepo).findById(1);
        BDDMockito.doReturn(expectedPersonUpdate).when(mockPersonRepo).save(ArgumentMatchers.any());

        Person actualPerson = personService.updatePerson(1, expectedPersonUpdate);
        Assertions.assertEquals(expectedPersonUpdate.toString(), actualPerson.toString());
    }

    @Test
    @DisplayName("Person Service: Update Person - Fail")
    public void updatePersonTestFail(){
        List<PhoneNumbers> numbersList = new ArrayList<>();
        numbersList.add(new PhoneNumbers(1, "302.123.4567", true));
        numbersList.add(new PhoneNumbers(2, "302.345.6789", true));
        Person expectedPersonUpdate = new Person("After", "Update", numbersList);
        BDDMockito.doReturn(Optional.empty()).when(mockPersonRepo).findById(1);
        Assertions.assertThrows(PersonNotFoundException.class, () ->{
            personService.updatePerson(1, expectedPersonUpdate);
        });
    }

    @Test
    @DisplayName("Person Service: Delete Person - Success")
    public void deletePartTestSuccess() throws PersonNotFoundException{
        BDDMockito.doReturn(Optional.of(mockPerson1)).when(mockPersonRepo).findById(1);
        Boolean actualResponse = personService.deletePerson(1);
        Assertions.assertTrue(actualResponse);
    }

    @Test
    @DisplayName("Person Service: Delete Person - Fail")
    public void deletePersonTestFail(){
        BDDMockito.doReturn(Optional.empty()).when(mockPersonRepo).findById(1);
        Assertions.assertThrows(PersonNotFoundException.class, () ->{
            personService.deletePerson(1);
        });
    }




}
