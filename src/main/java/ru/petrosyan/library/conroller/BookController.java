package ru.petrosyan.library.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.petrosyan.library.dao.BookDAO;
import ru.petrosyan.library.entity.Book;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("bookList", bookDAO.getAllBooks());
        return "book/bookList";
    }

    @GetMapping("/new")
    public String formInsertBook(Model model) {
        model.addAttribute("book", new Book());
        return "/book/bookAdd";
    }
}
