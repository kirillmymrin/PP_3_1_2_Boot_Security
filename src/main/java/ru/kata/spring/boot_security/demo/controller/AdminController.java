package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @GetMapping(value = "/admin")
    public String getUsers(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("users", userService.allUsers());
        modelMap.addAttribute("us", userService.loadUserByUsername(principal.getName()));
        return "admin";
    }

    @GetMapping("/new")
    public String getNewFrom(Model model, Principal principal) {
        model.addAttribute("user", new User());
        model.addAttribute("us", userService.loadUserByUsername(principal.getName()));
        return "newUser";
    }

}


