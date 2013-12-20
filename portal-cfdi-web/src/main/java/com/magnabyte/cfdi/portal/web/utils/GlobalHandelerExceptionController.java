package com.magnabyte.cfdi.portal.web.utils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
public class GlobalHandelerExceptionController {

//	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllExceptions(Exception ex) {
		ModelAndView model = new ModelAndView("error/genericError");
		model.addObject("errMsg", "this is Exception.class");
		return model;
	}
}
