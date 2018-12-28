package com.savaleks.onlineshop.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savaleks.onlineshop.model.UserModel;
import com.savaleks.shopbackend.dao.CartLineDAO;
import com.savaleks.shopbackend.dto.Cart;
import com.savaleks.shopbackend.dto.CartLine;
import com.savaleks.shopbackend.dto.Product;

@Service("cartService")
public class CartService {

	@Autowired
	private CartLineDAO cartLineDAO;

	@Autowired
	private HttpSession session;

	// return cart of user who has logged in
	private Cart getCart() {

		return ((UserModel) session.getAttribute("userModel")).getCart();
	}

	// returns entire cartLine
	public List<CartLine> getCartLines() {
		return cartLineDAO.list(this.getCart().getId());
	}

	public String updateCartLine(int cartLineId, int count) {
		// fetch the cartLine
		CartLine cartLine = cartLineDAO.get(cartLineId);

		if (cartLine == null) {
			return "result = error";
		} else {
			Product product = cartLine.getProduct();
			double oldTotal = cartLine.getTotal();

			if (product.getQuantity() <= count) {
				count = product.getQuantity();
			}

			cartLine.setProductCount(count);
			cartLine.setBuyingPrice(product.getUnitPrice());
			cartLine.setTotal(product.getUnitPrice() * count);

			cartLineDAO.update(cartLine);

			Cart cart = this.getCart();
			cart.setGrandTotal(cart.getGrandTotal() - oldTotal + cartLine.getTotal());

			cartLineDAO.updateCart(cart);

			return "result = updated";
		}
	}
}
