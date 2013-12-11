package com.magnabyte.cfdi.portal.web.controller;

import mx.gob.sat.cfd._3.Comprobante.Receptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("establecimiento")
public class SucursalCfdiController {

	private static final Logger logger = LoggerFactory.getLogger(SucursalCfdiController.class);
	
	@RequestMapping("/buscaRfc")
	public String buscaRfc(@ModelAttribute Receptor receptor, ModelMap model) {
		logger.debug("buscaRfc page");
		model.put("emptyList", true);
		return "sucursal/buscaRfc";
	}
}
