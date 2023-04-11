package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Controller
public class AdminController {

    @Autowired
    private final UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Logger logger = Logger.getLogger("AdminController");


    public AdminController(UserService userService) {
        this.userService = userService;

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

    @PostMapping
    public String save(@RequestParam("username") String name,
                       @RequestParam("lastName") String surname,
                       @RequestParam("age") Integer age,
                       @RequestParam("email") String email,
                       @RequestParam("password") String password,
                       @RequestParam("roles") String role) {
        logger.info("Before create");
        User userInfoUpdate = new User();
        userInfoUpdate.setUsername(name);
        logger.info("After name");
        userInfoUpdate.setLastName(surname);
        logger.info("After lastname");
        userInfoUpdate.setAge(age);
        logger.info("After age");
        userInfoUpdate.setEmail(email);
        logger.info("After email");
        userInfoUpdate.setPassword(bCryptPasswordEncoder.encode(password));
        logger.info("After pass");
        Set<Role> roles = new HashSet<>();
        Role nRole;
        if (role.equals("ROLE_ADMIN")) {
            nRole = new Role(2l, "ROLE_ADMIN");
        } else {
            nRole = new Role(1l, "ROLE_USER");
        }
        roles.add(nRole);
        userInfoUpdate.setRoles(roles);
        logger.info("After role");
        userService.save(userInfoUpdate);
        return "redirect:/admin";
    }


    @PostMapping("/admin/{id}")
    public String update(
            @RequestParam("idToUpdate") Integer id,
            @RequestParam("username") String name,
            @RequestParam("lastName") String surname,
            @RequestParam("age") Integer age,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("roles") String role) {
        User userInfoUpdate = new User();
        userInfoUpdate.setUsername(name);
        userInfoUpdate.setLastName(surname);
        userInfoUpdate.setAge(age);
        userInfoUpdate.setEmail(email);
        userInfoUpdate.setPassword(bCryptPasswordEncoder.encode(password));
        Set<Role> roles = new HashSet<>();
        Role nRole;
        if (role.equals("ROLE_ADMIN")) {
            nRole = new Role(2l, "ROLE_ADMIN");
        } else {
            nRole = new Role(1l, "ROLE_USER");
        }
        roles.add(nRole);
        userInfoUpdate.setRoles(roles);
        userService.update(Long.valueOf(id), userInfoUpdate);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String delete(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}


