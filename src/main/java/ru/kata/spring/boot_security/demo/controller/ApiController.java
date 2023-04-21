package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")

public class ApiController {
    @Autowired
    private final UserServiceImpl userService;

    public ApiController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PatchMapping()
    public ResponseEntity<User> patchUser(
            @RequestBody User user,
            @RequestParam(required = false, name = "selectedRoles") String[] selectedRoles
    ) {
        user.setRoles(userService.saveRole(selectedRoles));
        userService.update(user.getId(), user);
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<User> addUser(
            @RequestBody User user,
            @RequestParam(required = false, name = "selectedRoles") String[] selectedRoles
    ) {
        user.setRoles(userService.saveRole(selectedRoles));
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(required = true, name = "id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

}
