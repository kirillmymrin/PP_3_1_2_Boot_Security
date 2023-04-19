package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")

public class ApiController {
    @Autowired
    private final UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }

    private final Logger logger = Logger.getLogger("ApiController");


    @GetMapping(value = "/currentUser")
    @ResponseBody
    public ResponseEntity<User> getAuthorizeUser(Principal principal) {
        return new ResponseEntity<>(userService.getUser(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.allUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id")Long id){
        return userService.getUser(id);
    }

    @PatchMapping("/users")
    public ResponseEntity<User> patchUser(@RequestBody User user, @RequestParam(required = false
            , name = "selectedRoles") String[] selectedRoles) {
        String userPassword;
        logger.info(user.toString());
        if (user.getPassword().isEmpty()) {
            userPassword = userService.getUser(user.getId()).getPassword();
        } else {
            userPassword = userService.encodePassword(user.getPassword());
        }
        user.setPassword(userPassword);
        HashSet<Role> editRoles = new HashSet<>();
        for (String roleName : selectedRoles) {
            if (roleName.equals("ADMIN")) {
                editRoles.add(new Role(2L, "ROLE_ADMIN"));
            } else {
                editRoles.add(new Role(1L, "ROLE_USER"));
            }
        }
        user.setRoles(editRoles);
        userService.update(user.getId(),user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user, @RequestParam(required = false
            , name = "selectedRoles") String[] selectedRoles) {
        HashSet<Role> saveUserRoles = new HashSet<>();
        for (String roleName : selectedRoles) {
            if (roleName.equals("ADMIN")) {
                saveUserRoles.add(new Role(2L, "ROLE_ADMIN"));
            } else {
                saveUserRoles.add(new Role(1L, "ROLE_USER"));
            }
        }
        user.setRoles(saveUserRoles);
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping ("/users/{deleteId}")
    public void delete(@PathVariable(required = true, name = "deleteId") Long id) {
        userService.delete(id);
    }

}
