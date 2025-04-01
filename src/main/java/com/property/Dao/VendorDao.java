package com.property.Dao;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.property.Entity.User;
import com.property.Entity.Vendor;

@Repository
public class VendorDao {

    @Autowired
    private SessionFactory sessionfactory;

    public boolean vendorExistsByEmail(String email) {
        try (Session session = sessionfactory.openSession()) {
            Query<Vendor> query = session.createQuery("FROM Vendor WHERE email = :email", Vendor.class);
            query.setParameter("email", email);
            return query.uniqueResult() != null; // Returns true if vendor exists
        }
    }

    
    public String addVendor(Vendor vendor) {
        Transaction transaction = null;
        try (Session session = sessionfactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(vendor);
            transaction.commit();
            return "Vendor added successfully!";
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return "Failed to add vendor!";
        }
    }
    public List<Vendor> getAllVendors() {
        try (Session session = sessionfactory.openSession()) {
            List<Vendor> vendors = session.createQuery("FROM Vendor", Vendor.class).list();
            for (Vendor vendor : vendors) {
                Hibernate.initialize(vendor.getProperties());  // Ensure properties are loaded
            }
            return vendors;
        }
    }
 
    @Transactional(readOnly = true) 
    public Vendor getVendorById(int id) {
        try (Session session = sessionfactory.openSession()) {
            return session.get(Vendor.class, id);
        }
    }

    @Transactional
    public String deleteVendor(int id) {
        try (Session session = sessionfactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Fetch the user
            Vendor vendor = session.get(Vendor.class, id);
            if (vendor == null) {
                return "User not found!";
            }

            // Delete all related properties first
            session.createQuery("DELETE FROM Property p WHERE p.bookedUser.id = :vendorId")
                    .setParameter("vendorId", id)
                    .executeUpdate();

            // Delete the user
            session.delete(vendor);
            transaction.commit();
            return "User and related properties deleted successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to delete user: " + e.getMessage();
        }
    }

    public Vendor updateVendor(int id, Vendor vendor) {
        Transaction transaction = null;
        try (Session session = sessionfactory.openSession()) {
            transaction = session.beginTransaction();

            // Fetch the existing vendor by ID
            Vendor existingVendor = session.get(Vendor.class, id);
            if (existingVendor == null) {
                return null; // Return null if vendor not found
            }

            // Update the existing vendor details
            existingVendor.setEmail(vendor.getEmail());
            existingVendor.setPassword(vendor.getPassword());
            existingVendor.setFirst_name(vendor.getFirst_name());
            existingVendor.setLast_name(vendor.getLast_name());
            existingVendor.setMobile_no(vendor.getMobile_no());
            
            session.update(existingVendor);
            transaction.commit();
            return existingVendor;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }
}
