package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {


    List<User> allUsers();

    void save(User user);

    User getUser(long id);

    void update(Long id, User updateUser);

    void delete(Long id);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;


}
