package com.savaleks.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.savaleks.onlineshop.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@RequestMapping("/show")
	public ModelAndView showCart(@RequestParam(name = "result", required = false) String result) {
		ModelAndView model = new ModelAndView("page");

		if (result != null) {
			switch (result) {
			case "updated":
				model.addObject("message", "CartLine has been updated successfully");
				break;
			case "added":
				model.addObject("message", "CartLine has been added successfully");
				break;
			case "maximum":
				model.addObject("message", "CartLine has been reached max number of product");
				break;
			case "deleted":
				model.addObject("message", "CartLine has been deleted");
				break;
			case "unavailable":
				model.addObject("message", "Product quantity is not available");
				break;
			case "error":
				model.addObject("message", "CartLine not updated");
				break;
			}
		}

		model.addObject("title", "User Cart");
		model.addObject("userClickShowCart", true);
		model.addObject("cartLines", cartService.getCartLines());

		return model;
	}

	@RequestMapping("/{cartLineId}/update")
	public String updateCart(@PathVariable int cartLineId, @RequestParam int count) {

		String response = cartService.manageCartLine(cartLineId, count);

		return "redirect:/cart/show?" + response;
	}

	@RequestMapping("/{cartLineId}/delete")
	public String updateCart(@PathVariable int cartLineId) {

		String response = cartService.deleteCartLine(cartLineId);

		return "redirect:/cart/show?" + response;
	}

	@RequestMapping("/add/{productId}/product")
	public String addCart(@PathVariable int productId) {

		String response = cartService.addCartLine(productId);

		return "redirect:/cart/show?" + response;
	}

}
