package ru.petrosyan.library.entity;

import java.time.LocalDate;
import java.util.List;

public class Person {
    private Integer id;
    private String fio;
    private LocalDate dateBirth;
    private List<Book> books;

    public Person(Integer id, String fio, LocalDate dateBirth) {
        this.id = id;
        this.fio = fio;
        this.dateBirth = dateBirth;
    }

    public Person() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public Integer getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
