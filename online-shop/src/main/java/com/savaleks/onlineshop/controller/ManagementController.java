package com.savaleks.onlineshop.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.savaleks.shopbackend.dao.CategoryDAO;
import com.savaleks.shopbackend.dao.ProductDAO;
import com.savaleks.shopbackend.dto.Category;
import com.savaleks.shopbackend.dto.Product;

@Controller
@RequestMapping("/manage")
public class ManagementController {

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private ProductDAO productDAO;

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagementController.class);

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ModelAndView showManageProducts(@RequestParam(name = "opeartion", required = false) String operation) {
		ModelAndView model = new ModelAndView("page");
		model.addObject("userClickManageProducts", true);
		model.addObject("title", "Manage Products");

		Product newProduct = new Product();
		newProduct.setSupplierId(1);
		newProduct.setActive(true);

		model.addObject("product", newProduct);

		if (operation != null) {
			if (operation.equals("product")) {
				model.addObject("message", "Product submitted successfully!");
			} else if (operation.equals("category")) {
				model.addObject("message", "Category submitted successfully!");
			}
		}

		return model;

	}

	// hadnling product submission
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public String handleProductSubmission(@Valid @ModelAttribute("product") Product mProduct, BindingResult results,
			Model model) {

		// check if there are any errors
		if (results.hasErrors()) {

			model.addAttribute("userClickManageProducts", true);
			model.addAttribute("title", "Manage Products");
			model.addAttribute("message", "Validation failed for Product Submission");

			return "page";
		}

		LOGGER.info(mProduct.toString());

		// create a new product record
		productDAO.add(mProduct);

		return "redirect:/manage/products?operation=product";
	}

	// returning categories for all requests mapping
	@ModelAttribute("categories")
	public List<Category> getCategories() {
		return categoryDAO.list();
	}
}
