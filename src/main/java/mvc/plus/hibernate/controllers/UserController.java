package mvc.plus.hibernate.controllers;

import jakarta.validation.Valid;
import mvc.plus.hibernate.services.UserService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import mvc.plus.hibernate.models.User;
import mvc.plus.hibernate.utils.UserValidator;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final UserValidator userValidator;
    public UserController(UserService service,
                          UserValidator userValidator) {
        this.service = service;
        this.userValidator = userValidator;
    }
    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", service.getAll());
        return "users/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model) {
        model.addAttribute("user", service.get(id));
        return "users/show";
    }
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "users/new";
        }

        service.create(user);
        return "redirect:/users";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model) {
        model.addAttribute("user", service.get(id));
        return "users/edit";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        service.update(id, user);
        return "redirect:/users/" + id;
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        service.delete(id);
        return "redirect:/users";
    }
}