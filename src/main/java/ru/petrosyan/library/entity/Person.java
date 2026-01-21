package ru.petrosyan.library.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class Person {
    private Integer id;

    @NotBlank(message = "ФИО обязательное поле!")
    @Size(message = "ФИО не может содержать больше 70 символов или меньше 10", max=70, min = 10)
    private String fio;

    @NotNull(message = "Дата обязательна для выбора!")
    @Past(message = "Дата не может быть в будущем!")
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
