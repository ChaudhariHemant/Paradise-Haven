package com.property.Entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Property {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int property_id;

    private String property_type;
    private String description;
    private Double price;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "property_address_id", referencedColumnName = "id")
    private Address prop_address;

    @ManyToOne 
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private Vendor vendor;
    
    @ManyToOne 
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference
    private User bookedUser;

//    @ManyToOne
//    @JoinColumn(name = "property_id", nullable = false)
//    private Property property;
    
   
   

	public Property(int property_id, String property_type, String description, Double price, Address prop_address,
		Vendor vendor, User bookedUser) {
	super();
	this.property_id = property_id;
	this.property_type = property_type;
	this.description = description;
	this.price = price;
	this.prop_address = prop_address;
	this.vendor = vendor;
	this.bookedUser = bookedUser;
}



	public Property() {
		super();
	}
	public int getId() {
	    return property_id;
	}
	// Getters and Setters
    public int getProperty_id() {
        return property_id;
    }

    public void setProperty_id(int property_id) {
        this.property_id = property_id;
    }

    public String getProperty_type() {
        return property_type;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Address getProp_address() {
        return prop_address;
    }

    public void setProp_address(Address prop_address) {
        this.prop_address = prop_address;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public User getBookedUser() {
        return bookedUser;
    }

    public void setBookedUser(User bookedUser) {
        this.bookedUser = bookedUser;
    }


	@Override
    public String toString() {
        return "Property [property_id=" + property_id + ", property_type=" + property_type + ", description="
                + description + ", price=" + price + ", prop_address=" + prop_address + ", vendor=" + vendor
                + ", bookedUser=" + bookedUser + "]";
    }
   
}
