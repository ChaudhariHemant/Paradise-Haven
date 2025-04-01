package com.property.Entity;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "property_number")
    private String property_number; // New field for house/apartment number
    private String street;
    private String city;
    private String state;
    private String zip;
    
    @OneToOne(mappedBy = "prop_address", cascade = CascadeType.ALL,orphanRemoval = true)
    private Property property;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getProperty_number() {
        return property_number;
    }

    public void setProperty_number(String property_number) {
        this.property_number = property_number;
    }


	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zip;
	}

	public void setZipCode(String zipCode) {
		this.zip = zipCode;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", zipCode="
				+ zip + ", property=" + property + "]";
	}

    
}
