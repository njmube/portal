package com.magnabyte.cfdi.portal.web.utils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.magnabyte.cfdi.portal.model.exception.PortalException;

@ControllerAdvice
public class GlobalHandlerExceptionController {

	@ExceptionHandler(PortalException.class)
	public ModelAndView handlePortalExceptions(PortalException ex) {
		ModelAndView mav = new ModelAndView("error/portalError");
		mav.addObject("errMsg", ex.getMessage());
		return mav;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllExceptions(Exception ex) {
		ModelAndView mav = new ModelAndView("error/genericError");
		mav.addObject("errMsg", ex.getMessage());
		return mav;
	}
}
