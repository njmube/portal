package com.magnabyte.cfdi.portal.web.controller;

import java.util.Locale;

import net.sf.jasperreports.engine.JRParameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.magnabyte.cfdi.portal.service.factura.FacturaService;

@Controller
public class DocumentoController {
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoController.class);
	
	@Autowired
	private FacturaService facturaService;
	
	@RequestMapping("/cfdi/reportep")
	public String reporte(ModelMap model) {
		logger.debug("Creando reporte");
		Locale locale = new Locale("es","MX");
		model.put(JRParameter.REPORT_LOCALE, locale);
		model.put("objetoKey", facturaService.obtenerDatos());
		return "Reporte";
	}

}
