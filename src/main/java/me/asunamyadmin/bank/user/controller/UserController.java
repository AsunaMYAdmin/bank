package me.asunamyadmin.bank.user.controller;

import me.asunamyadmin.bank.user.domain.User;
import me.asunamyadmin.bank.user.domain.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Get all users");
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        logger.info("Get user by id {}", id);
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Create user {}", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int id) {
        logger.info("Update user {}", user);
        return ResponseEntity.ok().body(userService.updateUser(user, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        logger.info("Delete user {}", id);
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
