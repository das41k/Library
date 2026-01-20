package ru.petrosyan.library.entity;

import java.time.LocalDate;

public class Book {
    private Integer id;
    private String title;
    private String author;
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
