package com.magnabyte.cfdi.portal.web.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;

@Controller
@SessionAttributes("establecimiento")
public class MenuController {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private EstablecimientoService establecimientoService;
	
	@RequestMapping("/menu")
	public String menu(ModelMap model, Principal principal) {
		logger.debug("Obteniendo usuario logeado");
		Establecimiento establecimiento = new Establecimiento();
		establecimiento.setClave(principal.getName());
		model.put("establecimiento", establecimientoService.findByClave(establecimiento));
		return "redirect:/menuPage";
	}
	
	@RequestMapping("/menuPage")
	public String menuPage() {
		logger.debug("menuPage...");
		return "menu/menu";
	}
	
	@RequestMapping("/about")
	public String about(ModelMap model) {
		logger.debug("about");
		logger.debug("establecimiento--{}", (Establecimiento) model.get("establecimiento"));
		return "menu/about";
	}
	
}
