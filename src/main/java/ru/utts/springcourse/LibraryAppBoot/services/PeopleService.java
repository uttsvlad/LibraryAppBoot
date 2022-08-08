package ru.utts.springcourse.LibraryAppBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utts.springcourse.LibraryAppBoot.models.Book;
import ru.utts.springcourse.LibraryAppBoot.models.Person;
import ru.utts.springcourse.LibraryAppBoot.repositories.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Vlad Utts
 */
@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> findByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    public List<Book> getBooks(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
//            Hibernate.initialize(person.get().getBooks());
            List<Book> books = person.get().getBooks();

            books.forEach(book -> {
                long diffInMillis = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if (diffInMillis > 864000000)
                    book.setExpired(true);
            });
            return books;
        } else
            return Collections.emptyList();
    }
}
