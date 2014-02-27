package com.magnabyte.cfdi.portal.web.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.magnabyte.cfdi.portal.model.exception.PortalException;

@ControllerAdvice
public class GlobalHandlerExceptionController {

	private static final Logger logger = LoggerFactory.getLogger(GlobalHandlerExceptionController.class);
	
	@ExceptionHandler(PortalException.class)
	public ModelAndView handlePortalExceptions(PortalException ex) {
		ModelAndView mav = new ModelAndView("error/portalError");
		mav.addObject("errMsg", ex.getMessage());
		return mav;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllExceptions(Exception ex) {
		ModelAndView mav = new ModelAndView("error/genericError");
		logger.error("Ocurrió un error: ", ex);
		mav.addObject("errMsg", ex);
		return mav;
	}
	
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView handlerDataAccesException(DataAccessException ex) {
		ModelAndView mav = new ModelAndView("error/dataError");
		logger.error("Ocurrió un error: ", ex);
		mav.addObject("errMsg", ex);
		return mav;
	}
	
	@ExceptionHandler(HttpSessionRequiredException.class)
	public String restartFlow(HttpServletRequest request) {
		logger.debug("redireccionando session incompleta");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof AnonymousAuthenticationToken) {
			return "redirect:/portal/cfdi/menu";
		} else {
			return "redirect:/menuPage";
		}
	}
}
