	package com.property.Entity;
	
	import javax.persistence.*;

    import com.fasterxml.jackson.annotation.JsonBackReference;
	
	@Entity
	public class Booking {
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

		   @ManyToOne
		    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
		    @JsonBackReference
		    private User bookedUser;
	    @ManyToOne
	    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
	    private Vendor vendor;

	    @OneToOne
	    @JoinColumn(name = "property_id", referencedColumnName = "property_id", nullable = false)
	    private Property property;


	    private String bookingDate;
	
	
		public int getId() {
			return id;
		}
	
		public void setId(int id) {
			this.id = id;
		}
	
		public User getUser() {
			return bookedUser;
		}
	
		public void setUser(User user) {
			this.bookedUser = user;
		}
	
		public Vendor getVendor() {
			return vendor;
		}
	
		public void setVendor(Vendor vendor) {
			this.vendor = vendor;
		}
	
		public Property getProperty() {
			return property;
		}
	
		public void setProperty(Property property) {
			this.property = property;
		}
	
		public String getBookingDate() {
			return bookingDate;
		}
	
		public void setBookingDate(String bookingDate) {
			this.bookingDate = bookingDate;
		}
	
		@Override
		public String toString() {
			return "Booking [id=" + id + ", user=" + bookedUser+ ", vendor=" + vendor + ", property=" + property
					+ ", bookingDate=" + bookingDate + "]";
		}
	
	   
	}
