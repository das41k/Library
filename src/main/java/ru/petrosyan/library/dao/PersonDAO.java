package ru.petrosyan.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.petrosyan.library.entity.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
