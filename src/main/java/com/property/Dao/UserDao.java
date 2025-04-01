package com.property.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.property.Entity.Property;
import com.property.Entity.User;

@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public User getUserByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
            query.setParameter("email", email);
            return query.uniqueResult();  // Returns the user if found, otherwise null
        }
    }

    @Transactional
    public String addUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return "User added successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to add user!";
        }
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    public User getUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Transactional
    public User updateUser(int id, User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User existingUser = session.get(User.class, id);  // Fetch using 'id' from URL, not user.getId()

            if (existingUser != null) {
                existingUser.setUname(user.getUname());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                existingUser.setFirst_name(user.getFirst_name());
                existingUser.setLast_name(user.getLast_name());
                existingUser.setMobile_no(user.getMobile_no());
                existingUser.setAddress(user.getAddress());

                session.update(existingUser);
                transaction.commit();
                return existingUser;
            } else {
                return null; // User not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public String deleteUser(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Fetch the user
            User user = session.get(User.class, id);
            if (user == null) {
                return "User not found!";
            }

            // Delete all related properties first
            session.createQuery("DELETE FROM Property p WHERE p.bookedUser.id = :userId")
                    .setParameter("userId", id)
                    .executeUpdate();

            // Delete the user
            session.delete(user);
            transaction.commit();
            return "User and related properties deleted successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to delete user: " + e.getMessage();
        }
    }
}