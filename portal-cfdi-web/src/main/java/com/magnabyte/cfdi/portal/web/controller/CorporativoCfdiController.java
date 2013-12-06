package com.magnabyte.cfdi.portal.web.controller;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.cfd._3.Comprobante;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.model.documento.DocumentoFile;
import com.magnabyte.cfdi.portal.model.sucursal.Sucursal;
import com.magnabyte.cfdi.portal.service.samba.SambaService;

@Controller
@SessionAttributes("sucursal")
public class CorporativoCfdiController {

	private static final Logger logger = LoggerFactory.getLogger(CorporativoCfdiController.class);

	@Autowired
	private SambaService sambaService;
	
	@RequestMapping("/facturaCorp")
	public String facturaCorp(@ModelAttribute Sucursal sucursal, ModelMap model) {
		logger.debug("facturaCorp...");
//		List<DocumentoFile> documentos = sambaService.getFilesFromDirectory(sucursal.getRutaRepositorio());
		List<DocumentoFile> documentos = new ArrayList<DocumentoFile>();
		for (int i = 0; i < 15; i ++) {
			DocumentoFile d = new DocumentoFile();
			d.setNombre("archivo" + i + ".xml");
			documentos.add(d);
		}
		model.put("documentos", documentos);
		return "corporativo/facturaCorp";
	}
	
	@RequestMapping("/facturaCorp/validate/{fileName:.+\\.[a-z]+}")
	public String validarFactura(@ModelAttribute Sucursal sucursal, @PathVariable String fileName, ModelMap model) {
		logger.debug("valida factura");
		logger.debug(fileName);
		logger.debug(sucursal.getRutaRepositorio() + fileName);
		Comprobante comprobante = sambaService.getFile(sucursal.getRutaRepositorio(), fileName);
		logger.debug("el comprobante {}", comprobante);
		model.put("comprobante", comprobante);
		return "corporativo/facturaValidate";
	}
}
