package com.savaleks.onlineshop.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savaleks.onlineshop.model.UserModel;
import com.savaleks.shopbackend.dao.CartLineDAO;
import com.savaleks.shopbackend.dao.ProductDAO;
import com.savaleks.shopbackend.dto.Cart;
import com.savaleks.shopbackend.dto.CartLine;
import com.savaleks.shopbackend.dto.Product;

@Service("cartService")
public class CartService {

	@Autowired
	private CartLineDAO cartLineDAO;

	@Autowired
	private ProductDAO productDAO;

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

	public String manageCartLine(int cartLineId, int count) {
		// fetch the cartLine
		CartLine cartLine = cartLineDAO.get(cartLineId);

		if (cartLine == null) {
			return "result = error";
		} else {
			Product product = cartLine.getProduct();
			double oldTotal = cartLine.getTotal();

			// checking if product is available
			if (product.getQuantity() < count) {
				return "result = unavailable";
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

	public String deleteCartLine(int cartLineId) {
		// fetch the cartLine
		CartLine cartLine = cartLineDAO.get(cartLineId);

		if (cartLine == null) {
			return "result = error";
		} else {
			// update card
			Cart cart = this.getCart();
			cart.setGrandTotal(cart.getGrandTotal() - cartLine.getTotal());
			cart.setCartLines(cart.getCartLines() - 1);
			cartLineDAO.updateCart(cart);

			// remove card line
			cartLineDAO.delete(cartLine);

			return "result = deleted";
		}
	}

	public String addCartLine(int productId) {

		String response = null;

		Cart cart = this.getCart();
		CartLine cartLine = cartLineDAO.getByCartAndProduct(cart.getId(), productId);

		if (cartLine == null) {
			// add a new cartLine
			cartLine = new CartLine();

			// fetch the product
			Product product = productDAO.get(productId);

			cartLine.setCartId(cart.getId());
			cartLine.setProduct(product);
			cartLine.setBuyingPrice(product.getUnitPrice());
			cartLine.setProductCount(1);
			cartLine.setTotal(product.getUnitPrice());
			cartLine.setAvailable(true);

			cartLineDAO.add(cartLine);

			cart.setCartLines(cart.getCartLines() + 1);
			cart.setGrandTotal(cart.getGrandTotal() + cartLine.getTotal());

			cartLineDAO.updateCart(cart);

			response = "result = added";

		} else {

			// check if the cartLine had reached the max count
			if (cartLine.getProductCount() < 5) {

				// update the productCount for the that cartLine
				response = this.manageCartLine(cartLine.getId(), cartLine.getProductCount() + 1);
			}
		}

		return response;
	}
}
