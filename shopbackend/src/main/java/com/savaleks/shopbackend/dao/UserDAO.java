package com.savaleks.shopbackend.dao;

import com.savaleks.shopbackend.dto.Address;
import com.savaleks.shopbackend.dto.Cart;
import com.savaleks.shopbackend.dto.User;

public interface UserDAO {

	// add an user
	boolean addUser(User user);

	// add an address
	boolean addAddress(Address address);

	// add a cart
	boolean addCart(Cart cart);

}
