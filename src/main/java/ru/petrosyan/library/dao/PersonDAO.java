package ru.petrosyan.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.petrosyan.library.entity.Book;
import ru.petrosyan.library.entity.Person;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private final DataSource dataSource;

    @Autowired
    public PersonDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Person> getAllPersons() {
        List<Person> peopleList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * from person;");
        ) {
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("person_id"));
                person.setFio(resultSet.getString("fio"));
                person.setDateBirth(resultSet.getDate("datebirth").toLocalDate());
                peopleList.add(person);
            }
        } catch (SQLException exception) {
            System.out.println("Error" + exception.getMessage());
            exception.printStackTrace();
        }
        return peopleList;
    }

    public void insertPerson(Person person) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person (fio, datebirth) values (?, ?)");
        ) {
            preparedStatement.setString(1, person.getFio());
            preparedStatement.setDate(2, Date.valueOf(person.getDateBirth()));
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Reader is added");
            }
        } catch (SQLException exception) {
            System.out.println("Error" + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public Person getPersonById(Integer personId) {
        Person person = null;
        String personSql = "SELECT * FROM person WHERE person_id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement personStmt = connection.prepareStatement(personSql)) {

            personStmt.setInt(1, personId);

            try (ResultSet rs = personStmt.executeQuery()) {
                if (rs.next()) {
                    person = new Person();
                    person.setId(rs.getInt("person_id"));
                    person.setFio(rs.getString("fio"));
                    person.setDateBirth(rs.getDate("datebirth").toLocalDate());
                    List<Book> books = getBooksByPersonId(personId, connection);
                    person.setBooks(books);
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error getting person by id: " + exception.getMessage());
            exception.printStackTrace();
        }

        return person;
    }

    private List<Book> getBooksByPersonId(Integer personId, Connection connection) {
        List<Book> books = new ArrayList<>();
        String bookSql = "SELECT * FROM book WHERE person_id = ?";

        try(PreparedStatement bookStmt = connection.prepareStatement(bookSql)) {
            bookStmt.setInt(1, personId);

            try (ResultSet rs = bookStmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setDateCreate(rs.getDate("datecreate").toLocalDate());
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting books: " + e.getMessage());
        }

        return books;
    }

    public int deletePerson(Integer personId) {
        int rowDeleted = 0;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from person where person_id = ?")
        ) {
            preparedStatement.setInt(1, personId);
            rowDeleted = preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("Error delete Person: " + exception.getMessage());
        }
        return  rowDeleted;
    }
}
