package com.magnabyte.cfdi.portal.web.controller;

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

import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.service.samba.SambaService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;

@Controller
@SessionAttributes({"establecimiento", "documento"})
public class CorporativoController {

	private static final Logger logger = LoggerFactory.getLogger(CorporativoController.class);

	@Autowired
	private SambaService sambaService;
	
	@Autowired
	private DocumentoXmlService documentoXmlService;
	
	@RequestMapping("/facturaCorp")
	public String facturaCorp(@ModelAttribute Establecimiento sucursal, ModelMap model) {
		logger.debug("facturaCorp...");
		String urlSapFiles = sucursal.getRutaRepositorio().getRutaRepositorio() + sucursal.getRutaRepositorio().getRutaRepoIn();
		List<DocumentoCorporativo> documentos = sambaService.getFilesFromDirectory(urlSapFiles);
		model.put("documentos", documentos);
		return "corporativo/facturaCorp";
	}
	
	@RequestMapping("/facturaCorp/validate/{fileName:.+\\.[a-z]+}")
	public String validarFactura(@ModelAttribute Establecimiento establecimiento, @PathVariable String fileName, ModelMap model) {
		logger.debug("valida factura");
		logger.debug(establecimiento.getRutaRepositorio() + fileName);
		String urlSapFiles = establecimiento.getRutaRepositorio().getRutaRepositorio() + establecimiento.getRutaRepositorio().getRutaRepoIn();
		Comprobante comprobante = documentoXmlService.convertXmlSapToCfdi(sambaService.getFileStream(urlSapFiles, fileName));
		DocumentoCorporativo documento = new DocumentoCorporativo();
		documento.setComprobante(comprobante);
		documento.setFolioSap(fileName.substring(1, 11));
		documento.setNombreXmlPrevio(fileName);
		documento.setEstablecimiento(establecimiento);
		model.put("folioSap", documento.getFolioSap());
		model.put("comprobante", comprobante);
		model.put("documento", documento);
		return "corporativo/facturaValidate";
	}
	
}
