package ru.petrosyan.library.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        return "personList";
    }

    @GetMapping("/new")
    public String getFormByInsert(Model model) {
        model.addAttribute("person", new Person());
        return "personAdd";
    }

    @PostMapping
    public String insertPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error for validation");
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println("Field: " + error.getField() +
                        ", Error: " + error.getDefaultMessage());
            }
            return "personAdd";
        }
        System.out.println("Call for insert");
        personDAO.insertPerson(person);
        System.out.println("Redirect for /people");
        return "redirect:/people";
    }
}
