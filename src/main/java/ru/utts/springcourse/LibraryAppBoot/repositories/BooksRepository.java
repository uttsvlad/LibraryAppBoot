package ru.utts.springcourse.LibraryAppBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.utts.springcourse.LibraryAppBoot.models.Book;

import java.util.List;

/**
 * @author Vlad Utts
 */
@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleStartingWith(String startingWith);
}
