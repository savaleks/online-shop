package com.savaleks.shopbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.savaleks.shopbackend.dao.UserDAO;
import com.savaleks.shopbackend.dto.Address;
import com.savaleks.shopbackend.dto.Cart;
import com.savaleks.shopbackend.dto.User;

public class UserTestCase {

	private static AnnotationConfigApplicationContext context;
	private static UserDAO userDAO;
	private User user = null;
	private Cart cart = null;
	private Address address = null;

	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.savaleks.shopbackend");
		context.refresh();

		userDAO = (UserDAO) context.getBean("userDAO");
	}

	@Test
	public void testAdd() {
		user = new User();
		user.setFirstName("Alex");
		user.setLastName("Smith");
		user.setEmail("alex@mail.com");
		user.setContactNumber("86954123");
		user.setRole("USER");
		user.setPassword("1234");

		// add an user
		assertEquals("Failed to add an user", true, userDAO.addUser(user));

		address = new Address();
		address.setAddressLineOne("Vilniaus g.");
		address.setAddressLineTwo("2-54");
		address.setCity("Medininkai");
		address.setState("Vilniaus r.");
		address.setCountry("Lithuania");
		address.setPostalCode("LT-13569");
		address.setBilling(true);

		address.setUserId(user.getId());

		// add an address
		assertEquals("Failed to add an address", true, userDAO.addAddress(address));

		if (user.getRole().equals("USER")) {

			// create a cart for this user
			cart = new Cart();
			cart.setUser(user);

			// add an address
			assertEquals("Failed to add a cart", true, userDAO.addCart(cart));

			// add a shipping addres for this user

			address = new Address();
			address.setAddressLineOne("Sodu g.");
			address.setAddressLineTwo("1-24");
			address.setCity("Rukainiai");
			address.setState("Vilniaus r.");
			address.setCountry("Lithuania");
			address.setPostalCode("LT-13559");
			// set shipping to true
			address.setBilling(true);

			// link it with user
			address.setUserId(user.getId());

			// add the shipping address
			assertEquals("Failed to add shipping address", true, userDAO.addAddress(address));
		}

	}
}
