package ru.utts.springcourse.LibraryAppBoot.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Книга не может быть без названия!")
    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов!")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Имя автора не может быть пустым!")
    @Size(min = 2, max = 100, message = "Имя автора должно быть от 2 до 100 символов!")
    @Column(name = "author")
    private String author;

    @Min(value = 0, message = "Год издания книги не может быть меньше 0!")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @ToString.Exclude
    private Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    @ToString.Exclude
    private Date takenAt;

    @Transient
    @ToString.Exclude
    private boolean expired;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && year == book.year && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, year);
    }
}
