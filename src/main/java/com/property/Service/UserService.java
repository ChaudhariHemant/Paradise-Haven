package com.property.Service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.property.Dao.UserDao;
import com.property.Entity.Property;
import com.property.Entity.User;
import com.property.Entity.Vendor;



@Service
public class UserService {
    @Autowired
    UserDao userdao;

    public String addUser(User user) {
        // Check if user already exists
        User existingUser = userdao.getUserByEmail(user.getEmail());
        if (existingUser != null) {
            return "User already exists! Please log in.";
        }
        return userdao.addUser(user);
    }

    public List<User> getUser() {
        return userdao.getAllUsers();
    }
    
    @Transactional(readOnly = true)
    public User getUserById(int id) {
        User user = userdao.getUserById(id);
        if (user != null) {
            Hibernate.initialize(user.getBookedProperties()); // Force loading
        }
        return user;
    }

    public String deleteUser(int id) {
        return userdao.deleteUser(id);
    }

    public User updateUser(int id,User user ) {
        return userdao.updateUser(id,user);
    }

    public String loginUser(String email, String password) {
        User user = userdao.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return "Login successful!";
        } else {
            return "Invalid email or password!";
        }
    }
}
