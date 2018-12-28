package com.savaleks.shopbackend.dao;

import java.util.List;

import com.savaleks.shopbackend.dto.Address;
import com.savaleks.shopbackend.dto.User;

public interface UserDAO {

	// add an user
	boolean addUser(User user);

	User getByEmail(String email);

	// add an address
	boolean addAddress(Address address);

	Address getBillingAddress(User user);

	List<Address> listShippingAddresses(User user);

}
