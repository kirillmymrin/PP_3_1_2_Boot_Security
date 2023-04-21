package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @GetMapping(value = "/admin")
    public String getUsers(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.allUsers());
        return "admin";
    }
}


