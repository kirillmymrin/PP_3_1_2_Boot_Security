package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PersistenceContext
    private EntityManager entityManager;


    public List<User> allUsers() {
        Session session = entityManager.unwrap(Session.class);
        Query userQuery = session.createQuery("FROM User");
        return userQuery.getResultList();
    }

    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }
    @Transactional
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public void update(Long id, User updateUser) {
        User userToUpd = getUser(id);
        userToUpd.setUsername(updateUser.getUsername());
        userToUpd.setLastName(updateUser.getLastName());
        userToUpd.setEmail(updateUser.getEmail());
        userToUpd.setAge(userToUpd.getAge());
        userToUpd.setPassword(updateUser.getPassword());
        userToUpd.setRoles(updateUser.getRoles());
        entityManager.persist(userToUpd);
    }

    @Transactional
    public void delete(Long id) {
        entityManager.createQuery("DELETE from User WHERE id =: id ").setParameter("id", id).executeUpdate();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        Hibernate.initialize(user.getRoles());

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return user;
    }
}
