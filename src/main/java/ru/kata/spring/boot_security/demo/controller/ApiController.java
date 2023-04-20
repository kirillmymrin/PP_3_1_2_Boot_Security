package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")

public class ApiController {
    @Autowired
    private final UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<List<User>> getUsers() {
        return  ResponseEntity.ok(userService.allUsers());
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id")Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PatchMapping("/admin/users")
    public ResponseEntity<User> patchUser(@RequestBody User user, @RequestParam(required = false
            , name = "selectedRoles") String[] selectedRoles) {
        user.setRoles(userService.saveRole(selectedRoles));
        userService.update(user.getId(),user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/admin/users")
    public ResponseEntity<User> addUser(@RequestBody User user, @RequestParam(required = false
            , name = "selectedRoles") String[] selectedRoles) {
        user.setRoles(userService.saveRole(selectedRoles));
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping ("/admin/users/{deleteId}")
    public ResponseEntity<Void> delete(@PathVariable(required = true, name = "deleteId") Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

}
