package com.savaleks.onlineshop.exception;

import java.io.Serializable;

public class ProductNotFoundException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public String getMessage() {
		return message;
	}

	public ProductNotFoundException() {
		this("Product is not avalaible.");
	}

	public ProductNotFoundException(String message) {
		this.message = System.currentTimeMillis() + ": " + message;
	}

}
