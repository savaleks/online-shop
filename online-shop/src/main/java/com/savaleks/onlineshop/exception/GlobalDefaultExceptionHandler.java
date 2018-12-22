package com.savaleks.onlineshop.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handlerNoHandlerFoundException() {
		ModelAndView model = new ModelAndView("error");
		model.addObject("errorTitle", "The page is not constructed.");
		model.addObject("errorDescription", "The page you looking for is not available at the moment.");
		model.addObject("title", "404 Error Page");

		return model;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handlerException(Exception exc) {
		ModelAndView model = new ModelAndView("error");
		model.addObject("errorTitle", "Contact admin");

		/**
		 * Only for debugging your application
		 */
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exc.printStackTrace(pw);
		model.addObject("errorDescription", sw.toString());
		model.addObject("title", "Error");

		return model;
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handlerProductNotFoundException() {
		ModelAndView model = new ModelAndView("error");
		model.addObject("errorTitle", "Product not avalaible.");
		model.addObject("errorDescription", "The page you looking for is not available right now.");
		model.addObject("title", "Product unavailable");

		return model;
	}
}
