package ru.petrosyan.library.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Book {
    private Integer id;

    @NotBlank(message = "Название книги не может быть пустым!")
    @Size(message = "Название книги должно содержать не более 200 символов и не менее 3", min = 3, max = 200)
    private String title;

    @NotBlank(message = "ФИО автора не может быть пустым!")
    @Size(message = "ФИО автора не может содержать больше 70 символов или меньше 10", max=70, min = 10)
    private String author;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreate;
    private Person person;

    public Book(Integer id, String title, String author, LocalDate dateCreate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.dateCreate = dateCreate;
    }

    public Book() {}

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
