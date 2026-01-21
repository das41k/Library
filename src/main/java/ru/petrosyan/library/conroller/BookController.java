package ru.petrosyan.library.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.petrosyan.library.dao.BookDAO;
import ru.petrosyan.library.dao.PersonDAO;
import ru.petrosyan.library.entity.Book;
import ru.petrosyan.library.entity.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
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

    @GetMapping("/{id}/edit")
    public String formUpdateBook(@PathVariable("id") Integer bookId, Model model, RedirectAttributes redirectAttributes) {
        Book book = bookDAO.getBookById(bookId);
        if (book != null) {
            model.addAttribute("book", book);
            return "book/bookEdit";
        }
        redirectAttributes.addFlashAttribute("error", "Книга с данным id не была найдена. Возможно она была удалена");
        return "redirect:/book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/bookEdit";
        }
        bookDAO.updateBook(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") Integer bookId, RedirectAttributes redirectAttributes, Model model) {
        Book book = bookDAO.getBookById(bookId);
        if (book != null) {
            model.addAttribute("book", book);
            return "book/book";
        }
        redirectAttributes.addFlashAttribute("error", "Книга с данным id не была найдена. Возможно она была удалена");
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") Integer bookId, RedirectAttributes redirectAttributes) {
        int row = bookDAO.deleteBookById(bookId);
        if (row > 0) {
            redirectAttributes.addFlashAttribute("info", "Книга с id = " + bookId + " была удалена!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Книга с данным id не была найдена. Возможно она была удалена");
        }
        return "redirect:/book";
    }
}
