package com.property.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.property.DTO.BookingDTO;
import com.property.Entity.Booking;
import com.property.Service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
//    @PostMapping("/book/{propertyId}/{userId}")
//    public ResponseEntity<String> bookProperty(@PathVariable int propertyId, @PathVariable int userId) {
//        try {
//        	bookingService.bookProperty(propertyId, userId);
//            return ResponseEntity.ok("Property booked successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }


    @PostMapping("/add")
    public ResponseEntity<String> addBooking(@RequestBody Booking booking) {
        String result = bookingService.addBooking(booking);
        if (result.contains("Error")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        if (result.contains("already booked")) {
            return new ResponseEntity<>(result, HttpStatus.OK); // Return 200 OK for informative message
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable int id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            BookingDTO bookingDTO = new BookingDTO(booking);
            return ResponseEntity.ok(bookingDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookingDTOs = bookingService.getAllBookings();
        return ResponseEntity.ok(bookingDTOs);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable int id, @RequestBody BookingDTO bookingDTO) {
        try {
            String result = bookingService.updateBookingById(id, bookingDTO);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public String deleteBooking(@PathVariable int id) {
        return bookingService.deleteBooking(id);
    }
}
