package com.property.DTO;

import com.property.Entity.Booking;

public class BookingDTO {

    private int id;
    private Integer userId;
    private Integer vendorId;
    private Integer propertyId;
    private String bookingDate;
    
    
    public BookingDTO() {
		super();
	}

	// Constructor to convert Booking to DTO
    public BookingDTO(Booking booking) {
        this.id = booking.getId();
        this.userId = (booking.getUser() != null) ? booking.getUser().getId() : null;
        this.vendorId = (booking.getVendor() != null) ? booking.getVendor().getId() : null;
        this.propertyId = (booking.getProperty() != null) ? booking.getProperty().getProperty_id() : null;
        this.bookingDate = booking.getBookingDate();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getVendorId() { return vendorId; }
    public void setVendorId(Integer vendorId) { this.vendorId = vendorId; }

    public Integer getPropertyId() { return propertyId; }
    public void setPropertyId(Integer propertyId) { this.propertyId = propertyId; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
}
