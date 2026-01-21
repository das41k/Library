package ru.petrosyan.library.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.petrosyan.library.dao.PersonDAO;
import ru.petrosyan.library.entity.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonDAO personDAO;

    @Autowired
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String getAllPeoples(Model model) {
        model.addAttribute("peopleList", personDAO.getAllPersons());
        return "person/personList";
    }

    @GetMapping("/new")
    public String getFormByInsert(Model model) {
        model.addAttribute("person", new Person());
        return "person/personAdd";
    }

    @PostMapping
    public String insertPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error for validation");
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println("Field: " + error.getField() +
                        ", Error: " + error.getDefaultMessage());
            }
            return "person/personAdd";
        }
        System.out.println("Call for insert");
        personDAO.insertPerson(person);
        System.out.println("Redirect for /people");
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String getPeople(@PathVariable("id") Integer personId, RedirectAttributes redirectAttributes, Model model) {
        Person person = personDAO.getPersonById(personId);

        if (person == null) {
            redirectAttributes.addFlashAttribute("error", "Читатель с данным ID не был найден! Возможно он был уже удален");
            return "redirect:/people";
        }
        model.addAttribute("person", person);
        return "person/person";
    }
}
