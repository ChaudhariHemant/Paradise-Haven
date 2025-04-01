package com.property.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.property.Entity.Property;
import com.property.Entity.User;
import com.property.Service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userservice;

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        return userservice.loginUser(user.getEmail(), user.getPassword());
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user) {
        return userservice.addUser(user);
    }

    @GetMapping("/all-User")
    public List<User> getUser() {
        return userservice.getUser();
    }

    @GetMapping("/user-by-id/{id}")
    public User getUserById(@PathVariable int id) {
        return userservice.getUserById(id);
    }

    @PutMapping("/updateuser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        User updatedUser = userservice.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // User not found
        }
    }
    @DeleteMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable int id) {
        return userservice.deleteUser(id);
    }
}

