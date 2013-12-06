package com.magnabyte.cfdi.portal.web.controller;

import mx.gob.sat.cfd._3.Comprobante.Receptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("sucursal")
public class SucursalCfdiController {

	private static final Logger logger = LoggerFactory.getLogger(SucursalCfdiController.class);
	
	@RequestMapping("/buscaRfc")
	public String buscaRfc(@ModelAttribute Receptor receptor) {
		logger.debug("buscaRfc page");
		logger.debug("new-{}", receptor);
		return "sucursal/buscaRfc";
	}
	
	@RequestMapping("/muestraReceptor")
	public String muestraReceptor(@ModelAttribute Receptor receptor) {
		return "menu/about";
	}
}
