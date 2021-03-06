package com.savaleks.onlineshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.savaleks.onlineshop.exception.ProductNotFoundException;
import com.savaleks.shopbackend.dao.CategoryDAO;
import com.savaleks.shopbackend.dao.ProductDAO;
import com.savaleks.shopbackend.dto.Category;
import com.savaleks.shopbackend.dto.Product;

@Controller
public class PageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);

	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private ProductDAO productDAO;

	// == request adding at the end String ==
	@RequestMapping(value = { "/", "/home", "/index" })
	public ModelAndView index() {
		ModelAndView modview = new ModelAndView("page");
		modview.addObject("title", "Home");

		LOGGER.info("Inside PageController index method - INFO");
		LOGGER.debug("Inside PageController index method - DEBUG");

		// passing the list of categories
		modview.addObject("categories", categoryDAO.list());

		modview.addObject("userClickHome", true);
		return modview;
	}

	@RequestMapping(value = "/about")
	public ModelAndView about() {
		ModelAndView modview = new ModelAndView("page");
		modview.addObject("title", "About us");
		modview.addObject("userClickAbout", true);
		return modview;
	}

	@RequestMapping(value = "/contact")
	public ModelAndView contact() {
		ModelAndView modview = new ModelAndView("page");
		modview.addObject("title", "Contact us");
		modview.addObject("userClickContact", true);
		return modview;
	}

	/**
	 * Methods to load all the products and base on category
	 */

	@RequestMapping(value = "/show/all/products")
	public ModelAndView showAllProducts() {
		ModelAndView modview = new ModelAndView("page");
		modview.addObject("title", "All products");

		// passing the list of categories
		modview.addObject("categories", categoryDAO.list());

		modview.addObject("userClickAllProducts", true);
		return modview;
	}

	@RequestMapping(value = "/show/category/{id}/products")
	public ModelAndView showCategoryProducts(@PathVariable("id") int id) {
		ModelAndView modview = new ModelAndView("page");

		// categoryDAO to fetch a single category
		Category category = null;

		category = categoryDAO.get(id);

		modview.addObject("title", category.getName());

		// passing the list of categories
		modview.addObject("categories", categoryDAO.list());

		// passing single category object
		modview.addObject("category", category);

		modview.addObject("userClickCategoryProducts", true);
		return modview;
	}

	/**
	 * Viewing a single product
	 */

	@RequestMapping(value = "/show/{id}/product")
	public ModelAndView showSingleProduct(@PathVariable int id) throws ProductNotFoundException {
		ModelAndView model = new ModelAndView("page");
		Product product = productDAO.get(id);
		if (product == null)
			throw new ProductNotFoundException();

		// updat the view count
		product.setViews(product.getViews() + 1);
		productDAO.update(product);

		model.addObject("title", product.getName());
		model.addObject("product", product);

		model.addObject("userClickShowProduct", true);

		return model;
	}

	// having similar mapping to our flow id
	@RequestMapping(value = "/register")
	public ModelAndView register() {
		ModelAndView modview = new ModelAndView("page");
		modview.addObject("title", "About us");
		return modview;
	}

	// for login
	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		ModelAndView modview = new ModelAndView("login");

		if (error != null) {
			modview.addObject("message", "Invalid Username or Password");
		}

		if (logout != null) {
			modview.addObject("logout", "User has logged out");
		}

		modview.addObject("title", "Login");
		return modview;
	}

	// access denied page
	@RequestMapping(value = "/access-denied")
	public ModelAndView accessDenied() {
		ModelAndView modview = new ModelAndView("error");
		modview.addObject("title", "403 - Access Denied");
		modview.addObject("errorTitle", "I Caught you redhanded");
		modview.addObject("errorDescription", "You are not authorized for this page");
		return modview;
	}

	// Logout
	@RequestMapping(value = "/perform-logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {

		// going to fetch the authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}

		return "redirect:/login?logout";
	}

}
