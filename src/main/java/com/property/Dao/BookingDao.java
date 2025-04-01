package com.property.Dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.property.Entity.Booking;
import com.property.Entity.Property;
import com.property.Entity.User;

@Repository
public class BookingDao {

    @Autowired
    private SessionFactory sessionFactory;
//    public void bookProperty(int propertyId, int userId) {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//
//            Property property = session.get(Property.class, propertyId);
//            User user = session.get(User.class, userId);
//
//            if (property == null || user == null) {
//                throw new RuntimeException("Property or User not found!");
//            }
//
//            property.setBookedUser(user);
//            property.setVendor(property.getVendor()); // Vendor is already linked to the property
//
//            session.update(property);
//            transaction.commit();
//        }
//    }
    public String addBooking(Booking booking) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            
            // Fetch Property and User
            Property property = session.get(Property.class, booking.getProperty().getProperty_id());
            User user = session.get(User.class, booking.getUser().getId());

            if (property == null) {
                return "Error: Invalid Property ID. Property not found.";
            }
            if (user == null) {
                return "Error: Invalid User ID. User not found.";
            }

            // Check if Property is Already Booked
            if (property.getBookedUser() != null) {
                return "Property is already booked by User ID " + property.getBookedUser().getId() +
                       ". You cannot book this property again.";
            }

            // Perform Booking
            booking.setProperty(property);
            booking.setUser(user);
            booking.setVendor(property.getVendor());
            session.save(booking);

            // Mark Property as Booked
            property.setBookedUser(user);
            session.update(property);

            tx.commit();
            return "Booking added successfully!";
        } catch (Exception e) {
            return "Error adding booking: " + e.getMessage();
        }
    }


    public Booking getBookingById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Booking.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Booking> getAllBookings() {
        Session session = sessionFactory.openSession();
        List<Booking> bookings = session.createQuery("from Booking", Booking.class).list();
        session.close();
        return bookings;
    }
    
    
    public void updateBooking(Booking booking) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.merge(booking);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Failed to update booking: " + e.getMessage());
        } finally {
            session.close();
        }
    }


    public void deleteBooking(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Booking booking = session.get(Booking.class, id);
            if (booking != null && booking.getProperty() != null) {
                Property property = booking.getProperty();
                property.setBookedUser(null); // Clear user reference
                session.update(property);
                session.delete(booking);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Failed to delete booking", e);
        } finally {
            session.close();
        }
    }
}
