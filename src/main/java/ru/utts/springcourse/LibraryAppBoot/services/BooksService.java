package ru.utts.springcourse.LibraryAppBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utts.springcourse.LibraryAppBoot.models.Book;
import ru.utts.springcourse.LibraryAppBoot.models.Person;
import ru.utts.springcourse.LibraryAppBoot.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Vlad Utts
 */
@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear)
            return booksRepository.findAll(Sort.by("year"));
        else
            return booksRepository.findAll();
    }

    public List<Book> findAllWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Book updatedBook, int id) {
        Book bookToBeUpdated = findOne(id);

        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());

        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Person getBookOwner(int id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void release(int id) {
        Optional<Book> bookOptional = booksRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setOwner(null);
            book.setTakenAt(null);
        }
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Optional<Book> bookOptional = booksRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setOwner(selectedPerson);
            book.setTakenAt(new Date());
        }
    }

    public List<Book> findByTitleStartingWith(String startingWith) {
        return booksRepository.findByTitleStartingWith(startingWith);
    }
}
