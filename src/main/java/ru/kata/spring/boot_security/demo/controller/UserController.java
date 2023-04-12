package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/user")
    public String getUsers(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("users", userService.allUsers());
        modelMap.addAttribute("us", userService.loadUserByUsername(principal.getName()));
        return "user";
    }
}
