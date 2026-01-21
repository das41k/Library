package ru.petrosyan.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.petrosyan.library.entity.Book;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookDAO {
    private final DataSource dataSource;
    private final PersonDAO personDAO;

    @Autowired
    public BookDAO(DataSource dataSource, PersonDAO personDAO) {
        this.dataSource = dataSource;
        this.personDAO = personDAO;
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * from book;");
        ) {
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setDateCreate(resultSet.getDate("datecreate").toLocalDate());
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }

    public void insertBook(Book book) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO book (title, author, datecreate) values (?, ?, ?);");
        ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setDate(3, Date.valueOf(book.getDateCreate()));
            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("Book is append");
            }
        } catch (SQLException e) {
            System.out.println("Error database by insert Book: " + e);
            e.printStackTrace();
        }
    }

    public Book getBookById(Integer bookId) {
        Book book = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from book where book_id = ?;")
        ) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    book = new Book();
                    book.setId(resultSet.getInt("book_id"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setTitle(resultSet.getString("title"));
                    book.setDateCreate(resultSet.getDate("datecreate").toLocalDate());
                    book.setPerson(personDAO.getPersonById(resultSet.getInt("person_id")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error database by get Book: " + e);
            e.printStackTrace();
        }
        return book;
    }

    public void updateBook(Book book) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update book set title=?, author=?, datecreate=? where book_id=?;")
        ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setDate(3, Date.valueOf(book.getDateCreate()));
            preparedStatement.setInt(4, book.getId());

            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Book is update");
            }
        } catch (SQLException e) {
            System.out.println("Error database by update Book: " + e);
            e.printStackTrace();
        }
    }

    public int deleteBookById(Integer bookId) {
        int row = 0;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from book where book_id = ?");
        ) {
            preparedStatement.setInt(1, bookId);
            row = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error database by delete Book: " + e);
            e.printStackTrace();
        }
        return row;
    }

    public void assignPersonByBook(Integer personId, Integer bookId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update book set person_id=? where book_id=?;")
        ) {
            preparedStatement.setInt(1, personId);
            preparedStatement.setInt(2, bookId);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Book is assign by Person");
            }
        } catch (SQLException e) {
            System.out.println("Error database for assign Book by Person: " + e);
            e.printStackTrace();
        }
    }
}
