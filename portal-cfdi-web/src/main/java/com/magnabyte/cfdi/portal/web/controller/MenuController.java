package com.magnabyte.cfdi.portal.web.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.model.documento.DocumentoFile;
import com.magnabyte.cfdi.portal.model.sucursal.Sucursal;
import com.magnabyte.cfdi.portal.service.samba.SambaService;

@Controller
@SessionAttributes("sucursal")
public class MenuController {
	
	@Autowired
	private SambaService sambaService;
	
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	@RequestMapping("/menu")
	public String menu(ModelMap model, Principal principal) {
		logger.debug("menu sucursal");
		Sucursal sucursal = new Sucursal();
		sucursal.setNombre(principal.getName().toUpperCase());
		model.put("sucursal", sucursal);
		
		if (sucursal.getNombre().equalsIgnoreCase("magna")) {
			return "menu/menu";
		} else {
			return "redirect:/menuCorp";
		}
	}
	
	@RequestMapping("/menuCorp")
	public String menuCorp(ModelMap model) {
		logger.debug("menuCorp...");
		System.out.println((Sucursal) model.get("sucursal"));
		List<DocumentoFile> documentos = sambaService.getFilesFromDirectory();
		model.put("documentos", documentos);
		return "menu/menuCorp";
	}
	
	@RequestMapping("/cfdi/menu")
	public String menuCliente(ModelMap model) {
		logger.debug("menu cliente");
		return "menu/menu";
	}
	
	@RequestMapping("/about")
	public String about(ModelMap model, HttpSession session) {
		logger.debug("about");
		logger.debug("suc--{}", (Sucursal)session.getAttribute("sucursal"));
		logger.debug("sessionId={}", session.getId());
		return "menu/about";
	}
}
