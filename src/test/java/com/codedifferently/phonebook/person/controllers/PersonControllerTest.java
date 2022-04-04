package com.codedifferently.phonebook.person.controllers;

import com.codedifferently.phonebook.BaseControllerTest;
import com.codedifferently.phonebook.person.models.Person;
import com.codedifferently.phonebook.person.models.PhoneNumbers;
import com.codedifferently.phonebook.person.services.PersonService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PersonControllerTest extends BaseControllerTest {

    @MockBean
    private PersonService mockPersonService;

    @Autowired
    private MockMvc mockMvc;

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
    @DisplayName("Person Post: /person - success")
    public void createPersonRequestSuccess() throws Exception{
        BDDMockito.doReturn(mockPerson1).when(mockPersonService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(inputPerson)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Test Person 01")));
    }


}
