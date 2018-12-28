package com.savaleks.shopbackend.dao;

import java.util.List;

import com.savaleks.shopbackend.dto.Cart;
import com.savaleks.shopbackend.dto.CartLine;

public interface CartLineDAO {

	// common method from previously coded one

	public CartLine get(int id);

	public boolean add(CartLine cartLine);

	public boolean update(CartLine cartLine);

	public boolean delete(CartLine cartLine);

	public List<CartLine> list(int cartId);

	// other business method related to the cart lines

	public List<CartLine> listAvailable(int cartId);

	public CartLine getByCartAndProduct(int cartId, int productId);

	// update a cart
	boolean updateCart(Cart cart);

}
