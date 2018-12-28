package com.savaleks.shopbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.savaleks.shopbackend.dao.CartLineDAO;
import com.savaleks.shopbackend.dao.ProductDAO;
import com.savaleks.shopbackend.dao.UserDAO;
import com.savaleks.shopbackend.dto.Cart;
import com.savaleks.shopbackend.dto.CartLine;
import com.savaleks.shopbackend.dto.Product;
import com.savaleks.shopbackend.dto.User;

public class CartLineTestCase {

	private static AnnotationConfigApplicationContext context;

	private static CartLineDAO cartLineDAO;
	private static ProductDAO productDAO;
	private static UserDAO userDAO;

	private CartLine cartLine = null;

	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.savaleks.shopbackend");
		context.refresh();
		cartLineDAO = (CartLineDAO) context.getBean("cartLineDAO");
		productDAO = (ProductDAO) context.getBean("productDAO");
		userDAO = (UserDAO) context.getBean("userDAO");
	}

	@Test
	public void testAddNewCartLine() {

		// fetch the user and then cart of that user
		User user = userDAO.getByEmail("tom@email.com");
		Cart cart = user.getCart();

		// fetch the product
		Product product = productDAO.get(3);

		// Create a new CartLine
		cartLine = new CartLine();
		cartLine.setBuyingPrice(product.getUnitPrice());
		cartLine.setProductCount(cartLine.getProductCount() + 1);

		cartLine.setTotal(product.getUnitPrice() * cartLine.getProductCount());
		cartLine.setAvailable(true);
		cartLine.setCartId(cart.getId());
		cartLine.setProduct(product);

		cart.setCartLines(cart.getCartLines() + 1);
		cart.setGrandTotal(cart.getGrandTotal() + cartLine.getTotal());

		assertEquals("Failed to add the CartLine!", true, cartLineDAO.add(cartLine));
		assertEquals("Failed to update the cart!", true, cartLineDAO.updateCart(cart));

	}

	/*
	 * @Test public void testUpdateCartLine() {
	 * 
	 * // fetch the user and then cart of that user User user =
	 * userDAO.getByEmail("absr@gmail.com"); Cart cart = user.getCart();
	 * 
	 * cartLine = cartLineDAO.getByCartAndProduct(cart.getId(), 2);
	 * 
	 * cartLine.setProductCount(cartLine.getProductCount() + 1);
	 * 
	 * double oldTotal = cartLine.getTotal();
	 * 
	 * cartLine.setTotal(cartLine.getProduct().getUnitPrice() *
	 * cartLine.getProductCount());
	 * 
	 * cart.setGrandTotal(cart.getGrandTotal() + (cartLine.getTotal() - oldTotal));
	 * 
	 * assertEquals("Failed to update the CartLine!", true,
	 * cartLineDAO.update(cartLine));
	 * 
	 * }
	 */

}
