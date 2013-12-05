package com.magnabyte.cfdi.portal.web.controller;

import mx.gob.sat.cfd._3.Comprobante.Receptor;
import mx.gob.sat.cfd._3.ObjectFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("sucursal")
public class SucursalCfdiController {

	private static final Logger logger = LoggerFactory.getLogger(SucursalCfdiController.class);
	
	@Autowired
	private ObjectFactory comprobanteFactory;
	
	@RequestMapping("/buscaRfc")
	public String buscaRfc(ModelMap model) {
		logger.debug("buscaRfc page");
		Receptor receptor = comprobanteFactory.createComprobanteReceptor();
		model.put("receptor", receptor);
		return "sucursal/buscaRfc";
	}
}
