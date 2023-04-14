package ru.kata.spring.boot_security.demo.controller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    final
    UserServiceImpl userServiceImpl;
    final
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public AdminController(BCryptPasswordEncoder bCryptPasswordEncoder, UserServiceImpl userServiceImpl) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping(value = "/admin")
    public String getUsers(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("users", userServiceImpl.allUsers());
        modelMap.addAttribute("us", userServiceImpl.loadUserByUsername(principal.getName()));
        return "admin";
    }

    @GetMapping("/new")
    public String getNewFrom(Model model, Principal principal) {
        model.addAttribute("user", new User());
        model.addAttribute("us", userServiceImpl.loadUserByUsername(principal.getName()));
        return "newUser";
    }

    @PostMapping
    public String save(@RequestParam("username") String name,
                       @RequestParam("lastName") String surname,
                       @RequestParam("age") Integer age,
                       @RequestParam("email") String email,
                       @RequestParam("password") String password,
                       @RequestParam("roles") String role) {

        User userInfoSave = new User();
        userInfoSave.setUsername(name);
        userInfoSave.setLastName(surname);
        userInfoSave.setAge(age);
        userInfoSave.setEmail(email);
        userInfoSave.setPassword(bCryptPasswordEncoder.encode(password));
        Set<Role> roles = new HashSet<>();
        Role nRole;
        if (role.equals("ROLE_ADMIN")) {
            nRole = new Role(2L, "ROLE_ADMIN");
        } else {
            nRole = new Role(1L, "ROLE_USER");
        }
        roles.add(nRole);
        userInfoSave.setRoles(roles);
        userServiceImpl.save(userInfoSave);
        return "redirect:/admin";
    }


    @PostMapping("/admin/{id}")
    public String update(
            @RequestParam("username") String name,
            @RequestParam("lastName") String surname,
            @RequestParam("age") Integer age,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("roles") String role,
            @PathVariable String id) {

        User userInfoUpdate = new User();
        userInfoUpdate.setUsername(name);
        userInfoUpdate.setLastName(surname);
        userInfoUpdate.setAge(age);
        userInfoUpdate.setEmail(email);
        String emptyLine = "";
        if (password.equals(emptyLine)) {
            userInfoUpdate.setPassword(userServiceImpl.loadUserByUsername(userInfoUpdate.getUsername()).getPassword());
        } else {
            userInfoUpdate.setPassword(bCryptPasswordEncoder.encode(password));
        }
        Set<Role> roles = new HashSet<>();
        Role nRole;
        if (role.equals("ROLE_ADMIN")) {
            nRole = new Role(2L, "ROLE_ADMIN");
        } else {
            nRole = new Role(1L, "ROLE_USER");
        }
        roles.add(nRole);
        userInfoUpdate.setRoles(roles);
        userServiceImpl.update(Long.valueOf(id), userInfoUpdate);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String delete(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }
}


