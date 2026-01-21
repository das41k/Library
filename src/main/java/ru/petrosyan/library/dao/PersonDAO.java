package ru.petrosyan.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
}
