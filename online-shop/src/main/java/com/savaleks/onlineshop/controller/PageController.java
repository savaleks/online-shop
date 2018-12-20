package com.savaleks.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.savaleks.shopbackend.dao.CategoryDAO;
import com.savaleks.shopbackend.dto.Category;

@Controller
public class PageController {

	@Autowired
	private CategoryDAO categoryDAO;

	// == request adding at the end String ==
	@RequestMapping(value = { "/", "/home", "/index" })
	public ModelAndView index() {
		ModelAndView modview = new ModelAndView("page");
		modview.addObject("title", "Home");

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

	// == request without adding at the end String(default value) ==
//	@RequestMapping(value = "/test")
//	public ModelAndView index(@RequestParam(value = "greeting", required = false) String greeting) {
//		if (greeting == null) {
//			greeting = "Hello there friend!";
//		}
//		ModelAndView modview = new ModelAndView("page");
//		modview.addObject("greeting", greeting);
//		return modview;
//	}

//	// == request with adding at the address end String, dynamic request  ==
//	@RequestMapping(value = "/test/{greeting}")
//	public ModelAndView index(@PathVariable("greeting") String greeting) {
//		if (greeting == null) {
//			greeting = "Hello there friend!";
//		}
//		ModelAndView modview = new ModelAndView("page");
//		modview.addObject("greeting", greeting);
//		return modview;
//	}
}
