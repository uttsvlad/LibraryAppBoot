package ru.utts.springcourse.LibraryAppBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.utts.springcourse.LibraryAppBoot.models.Person;

import java.util.Optional;


/**
 * @author Vlad Utts
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByFullName(String fullName);
}
