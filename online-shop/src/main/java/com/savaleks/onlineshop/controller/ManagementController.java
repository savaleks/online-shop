package com.savaleks.onlineshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.savaleks.onlineshop.util.FileUploadUtility;
import com.savaleks.onlineshop.validator.ProductValidator;
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

	@RequestMapping(value = "/{id}/product", method = RequestMethod.GET)
	public ModelAndView showEditProducts(@PathVariable int id) {
		ModelAndView model = new ModelAndView("page");
		model.addObject("userClickManageProducts", true);
		model.addObject("title", "Manage Products");

		// fetch the product from the database
		Product newProduct = productDAO.get(id);
		// set the product fetch from database
		model.addObject("product", newProduct);

		return model;
	}

	// hadnling product submission
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public String handleProductSubmission(@Valid @ModelAttribute("product") Product mProduct, BindingResult results,
			Model model, HttpServletRequest request) {

		if (mProduct.getId() == 0) {
			new ProductValidator().validate(mProduct, results);
		} else {
			if (!mProduct.getFile().getOriginalFilename().equals("")) {
				new ProductValidator().validate(mProduct, results);
			}
		}

		// check if there are any errors
		if (results.hasErrors()) {

			model.addAttribute("userClickManageProducts", true);
			model.addAttribute("title", "Manage Products");
			model.addAttribute("message", "Validation failed for Product Submission");

			return "page";
		}

		LOGGER.info(mProduct.toString());

		// create a new product record if id is 0
		if (mProduct.getId() == 0) {
			productDAO.add(mProduct);
		} else {
			// update the product if id is not 0
			productDAO.update(mProduct);
		}

		if (!mProduct.getFile().getOriginalFilename().equals("")) {
			FileUploadUtility.uploadFile(request, mProduct.getFile(), mProduct.getCode());
		}

		return "redirect:/manage/products?operation=product";
	}

	@RequestMapping(value = "/product/{id}/activation", method = RequestMethod.POST)
	@ResponseBody
	public String handleProductActivation(@PathVariable int id) {

		// fetch product from the database
		Product product = productDAO.get(id);

		boolean isActive = product.isActive();

		// activating and deactinvating based on the valule of active field
		product.setActive(!product.isActive());

		// update the product
		productDAO.update(product);

		return (isActive) ? "You have deactivating the product with id " + product.getId()
				: "You have activating the product with id " + product.getId();
	}

	// returning categories for all requests mapping
	@ModelAttribute("categories")
	public List<Category> getCategories() {
		return categoryDAO.list();
	}
}
