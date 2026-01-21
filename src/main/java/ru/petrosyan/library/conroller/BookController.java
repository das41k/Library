package ru.petrosyan.library.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.petrosyan.library.dao.BookDAO;
import ru.petrosyan.library.entity.Book;

import javax.validation.Valid;

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

    @PostMapping
    public String insertBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/bookAdd";
        }
        bookDAO.insertBook(book);
        return "redirect:/book";
    }
}
