package com.savaleks.shopbackend.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// was oneToMany User user
	@Column(name = "user_id")
	private int userId;
	@Column(name = "address_line_one")
	@NotBlank(message = "Please enter an address")
	private String addressLineOne;
	@Column(name = "address_line_two")
	private String addressLineTwo;
	@NotBlank(message = "Please enter the city")
	private String city;
	private String state;
	@NotBlank(message = "Please enter the country")
	private String country;
	@Column(name = "post_code")
	@NotBlank(message = "Please enter post code")
	private String postalCode;
	private boolean shipping;
	private boolean billing;

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAddressLineOne() {
		return addressLineOne;
	}

	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}

	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public boolean isShipping() {
		return shipping;
	}

	public void setShipping(boolean shipping) {
		this.shipping = shipping;
	}

	public boolean isBilling() {
		return billing;
	}

	public void setBilling(boolean billing) {
		this.billing = billing;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", userId=" + userId + ", addressLineOne=" + addressLineOne + ", addressLineTwo="
				+ addressLineTwo + ", city=" + city + ", state=" + state + ", country=" + country + ", postalCode="
				+ postalCode + ", shipping=" + shipping + ", billing=" + billing + "]";
	}
}
