package com.codedifferently.phonebook.person.repos;

import com.codedifferently.phonebook.person.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Integer> {
    Iterable<Person> findByFullName(String getFullName);
}
