package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;


@Controller
public class UserController {
    @Autowired
    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping(value = "/user")
    public String getUsers(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("users", userServiceImpl.allUsers());
        modelMap.addAttribute("us", userServiceImpl.loadUserByUsername(principal.getName()));
        return "user";
    }
}
