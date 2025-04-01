package com.property.Entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
public class User {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String uname;
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private long mobile_no;
    private String address;

    @OneToMany(mappedBy = "bookedUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Property> bookedProperties;


//    
//    @OneToMany(mappedBy = "bookedUser", cascade = CascadeType.ALL, orphanRemoval = true)
//    //@JsonManagedReference
//    @JsonIgnore 
//    private List<Property> bookedProperties;


    
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public long getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(long mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Property> getBookedProperties() {
		return bookedProperties;
	}

	public void setBookedProperties(List<Property> bookedProperties) {
		this.bookedProperties = bookedProperties;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", uname=" + uname + ", email=" + email + ", password=" + password + ", first_name="
				+ first_name + ", last_name=" + last_name + ", mobile_no=" + mobile_no + ", address=" + address
				+ ", bookedProperties=" + bookedProperties + "]";
	}

    
}
