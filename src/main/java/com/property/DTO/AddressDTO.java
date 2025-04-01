package com.property.DTO;

public class AddressDTO {
    private int id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String property_number; // New field

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getProperty_number() {
		return property_number;
	}

	public void setProperty_number(String property_number) {
		this.property_number = property_number;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
