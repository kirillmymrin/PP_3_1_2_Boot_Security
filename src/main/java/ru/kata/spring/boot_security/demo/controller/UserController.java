package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/user")
    public String getUsers(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.allUsers());
        return "user";
    }

//    @GetMapping("user/{id}")
//    public String getUser(@PathVariable("id") long id, Model model) {
//        model.addAttribute("user", userService.getUser(id));
//        return "userInfo";
//    }
//
//    @GetMapping("/new")
//    public String getNewFrom(Model model) {
//        model.addAttribute("user", new User());
//        return "newUser";
//    }
//
//    @PostMapping
//    public String save(@ModelAttribute("user") User user) {
//        userService.save(user);
//        return "redirect:/";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String getEditFrom(Model model, @PathVariable("id") long id) {
//        model.addAttribute("user", userService.getUser(id));
//        return "edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
//        userService.update(id, user);
//        return "redirect:/";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") long id) {
//        userService.delete(id);
//        return "redirect:/";
//    }
}
