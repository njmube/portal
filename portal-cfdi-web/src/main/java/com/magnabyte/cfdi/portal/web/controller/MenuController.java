package com.magnabyte.cfdi.portal.web.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.model.sucursal.Sucursal;

@Controller
@SessionAttributes("sucursal")
public class MenuController {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	@RequestMapping("/menu")
	public String menu(ModelMap model, Principal principal) {
		logger.debug("menu sucursal");
		Sucursal sucursal = new Sucursal();
		sucursal.setNombre(principal.getName().toUpperCase());
		sucursal.setRutaRepositorio("smb://10.1.200.125/compartido/modatelas/");
		model.put("sucursal", sucursal);
		
		return "menu/menu";
	}
	
	@RequestMapping("/about")
	public String about(ModelMap model) {
		logger.debug("about");
		logger.debug("suc--{}", (Sucursal)model.get("sucursal"));
		return "menu/about";
	}
	
//	@RequestMapping("/cfdi/menu")
//	public String menuCliente(ModelMap model) {
//		logger.debug("menu cliente");
//		return "menu/menu";
//	}
}
