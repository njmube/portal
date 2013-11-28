package com.magnabyte.cfdi.portal.web.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.model.Sucursal;

@Controller
@SessionAttributes("sucursal")
public class MenuController {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	@RequestMapping("/menu")
	public String menu(ModelMap model, Principal principal) {
		logger.debug("welcome");
		Sucursal sucursal = new Sucursal();
		sucursal.setNombre(principal.getName());
		model.put("sucursal", sucursal);
		return "menu/welcome";
	}
	@RequestMapping("/about")
	public String about(ModelMap model, HttpSession session) {
		logger.debug("about");
		logger.debug("suc--{}", (Sucursal)session.getAttribute("sucursal"));
		logger.debug("sessionId={}", session.getId());
		return "menu/about";
	}
}
