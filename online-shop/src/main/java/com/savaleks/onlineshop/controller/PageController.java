package com.savaleks.onlineshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

	// == request adding at the end String ==
	@RequestMapping(value = { "/", "/home", "/index" })
	public ModelAndView index() {
		ModelAndView modview = new ModelAndView("page");
		modview.addObject("title", "Home");
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
