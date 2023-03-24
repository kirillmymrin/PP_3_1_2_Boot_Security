package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {

@Autowired
private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/admin")
        public String getUsers(ModelMap modelMap) {
            modelMap.addAttribute("users", userService.allUsers());
            return "admin";
            //users";
        }

        @GetMapping("user/{id}")
        public String getUser(@PathVariable("id") long id, Model model) {
            model.addAttribute("user", userService.getUser(id));
            return "userInfo";
            //"userInfo";
        }

        @GetMapping("/new")
        public String getNewFrom(Model model) {
            model.addAttribute("user", new User());
            return "newUser";
        }

        @PostMapping
        public String save(@ModelAttribute("user") User user) {
            userService.save(user);
            return "redirect:/admin";
        }

        @GetMapping("/{id}/edit")
        public String getEditFrom(Model model, @PathVariable("id") long id) {
            model.addAttribute("user", userService.getUser(id));
            return "edit";
        }

        @PatchMapping(value = "/{id}")
        public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
            userService.update(id, user);
            return "redirect:/admin";
        }

        @DeleteMapping("/{id}")
        public String delete(@PathVariable("id") long id) {
            userService.delete(id);
            return "redirect:/admin";
        }
    }


