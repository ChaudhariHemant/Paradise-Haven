package com.property.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.property.DTO.BookingDTO;
import com.property.Dao.BookingDao;
import com.property.Dao.PropertyDao;
import com.property.Dao.UserDao;
import com.property.Dao.VendorDao;
import com.property.Entity.Booking;
import com.property.Entity.Property;
import com.property.Entity.User;
import com.property.Entity.Vendor;

@Service
public class BookingService {

    @Autowired
    private BookingDao bookingDao;
//    public String bookProperty(int propertyId, int userId) {
//    	bookingDao.bookProperty(propertyId,userId);
//        return "Property added successfully";
//    }
    @Autowired
    private UserDao UserDao;

    @Autowired
    private VendorDao VendorDao;

    @Autowired
    private PropertyDao PropertyDao;

    

    @Transactional
    public String addBooking(Booking booking) {
        return bookingDao.addBooking(booking);
    }



    @Transactional(readOnly = true)
    public Booking getBookingById(int id) {
        return bookingDao.getBookingById(id);
    }

    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingDao.getAllBookings();
        return bookings.stream().map(BookingDTO::new).toList();
    }

  

    @Transactional
    public String updateBookingById(int id, BookingDTO bookingDTO) {
        Booking booking = bookingDao.getBookingById(id);
        if (booking == null) {
            throw new RuntimeException("Booking not found with ID: " + id);
        }

        // Update Booking Date
        if (bookingDTO.getBookingDate() != null) {
            booking.setBookingDate(bookingDTO.getBookingDate());
        }

        // Update User
        if (bookingDTO.getUserId() != null) {
            User user = UserDao.getUserById(bookingDTO.getUserId());
            if (user == null) throw new RuntimeException("User not found with ID: " + bookingDTO.getUserId());
            booking.setUser(user);
        }

        // Update Vendor
        if (bookingDTO.getVendorId() != null) {
            Vendor vendor = VendorDao.getVendorById(bookingDTO.getVendorId());
            if (vendor == null) throw new RuntimeException("Vendor not found with ID: " + bookingDTO.getVendorId());
            booking.setVendor(vendor);
        }

        // Update Property
        if (bookingDTO.getPropertyId() != null) {
            Property property = PropertyDao.getPropertyById(bookingDTO.getPropertyId());
            if (property == null) throw new RuntimeException("Property not found with ID: " + bookingDTO.getPropertyId());
            booking.setProperty(property);
        }

        bookingDao.updateBooking(booking);
        return "Booking updated successfully!";
    }


    public String deleteBooking(int id) {
        bookingDao.deleteBooking(id);
        return "Booking deleted successfully";
    }
}
