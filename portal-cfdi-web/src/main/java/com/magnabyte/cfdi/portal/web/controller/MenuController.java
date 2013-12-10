package com.magnabyte.cfdi.portal.web.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.model.sucursal.Sucursal;
import com.magnabyte.cfdi.portal.service.samba.SucursalService;

@Controller
@SessionAttributes("sucursal")
public class MenuController {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private SucursalService sucursalService;
	
	@RequestMapping("/menu")
	public String menu(ModelMap model, Principal principal) {
		logger.debug("menu sucursal");
		Sucursal sucursal = (Sucursal) sucursalService.loadUserByUsername(principal.getName().toUpperCase());
		model.put("sucursal", sucursal);
		return "menu/menu";
	}
	
	@RequestMapping("/about")
	public String about(ModelMap model) {
		logger.debug("about");
		logger.debug("suc--{}", (Sucursal)model.get("sucursal"));
		return "menu/about";
	}
	
}
