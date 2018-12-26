package com.savaleks.onlineshop.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Component;

import com.savaleks.onlineshop.model.RegisterModel;
import com.savaleks.shopbackend.dao.UserDAO;
import com.savaleks.shopbackend.dto.Address;
import com.savaleks.shopbackend.dto.Cart;
import com.savaleks.shopbackend.dto.User;

@Component
public class RegisterHandler {

	@Autowired
	private UserDAO userDAO;

	public RegisterModel init() {
		return new RegisterModel();
	}

	public void addUser(RegisterModel registerModel, User user) {
		registerModel.setUser(user);
	}

	public void addBilling(RegisterModel registerModel, Address billing) {
		registerModel.setBilling(billing);
	}

	public String validateUser(User user, MessageContext error) {

		String transitionValue = "success";

		// checking if password matches confirm password

		if (!(user.getPassword().equals(user.getConfirmPassword()))) {
			error.addMessage(new MessageBuilder().error().source("confirmPassword")
					.defaultText("Password does not match the confirm password").build());

			transitionValue = "failure";
		}

		// check the uniqueness of the email id

		if (userDAO.getByEmail(user.getEmail()) != null) {

			error.addMessage(
					new MessageBuilder().error().source("email").defaultText("Email addrees already used").build());

			transitionValue = "failure";
		}

		return transitionValue;
	}

	public String saveAll(RegisterModel model) {
		String transitionValue = "success";

		// fetch the user
		User user = model.getUser();
		if (user.getRole().equals("USER")) {
			Cart cart = new Cart();
			cart.setUser(user);
			user.setCart(cart);
		}

		// save the user
		userDAO.addUser(user);

		// get the address
		Address billing = model.getBilling();
		billing.setUserId(user.getId());
		billing.setBilling(true);

		// save the address
		userDAO.addAddress(billing);
		return transitionValue;
	}
}
