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
		List<DocumentoCorporativo> documentos = null;
//	Corregir	sambaService.getFilesFromDirectory(sucursal.getRutaRepositorio());
		model.put("documentos", documentos);
		return "corporativo/facturaCorp";
	}
	
	@RequestMapping("/facturaCorp/validate/{fileName:.+\\.[a-z]+}")
	public String validarFactura(@ModelAttribute Establecimiento sucursal, @PathVariable String fileName, ModelMap model) {
		logger.debug("valida factura");
		logger.debug(sucursal.getRutaRepositorio() + fileName);
		Comprobante comprobante = null; 
//		Corregir		documentoXmlService.convertXmlSapToCfdi(sambaService.getFileStream(sucursal.getRutaRepositorio(), fileName));
		DocumentoCorporativo documento = new DocumentoCorporativo();
		documento.setComprobante(comprobante);
		documento.setFolioSap(fileName.substring(1, 11));
		documento.setNombreXmlPrevio(fileName);
//		documento.setRutaXmlPrevio(sucursal.getRutaRepositorio());
		model.put("folioSap", documento.getFolioSap());
		model.put("comprobante", comprobante);
		model.put("documento", documento);
		return "corporativo/facturaValidate";
	}
	
//	@RequestMapping("/limpia")
//	public String limpia(ModelMap model, HttpServletRequest request) {
//		logger.debug("cleaning");
//		model.remove("comprobante");
//		WebUtils.setSessionAttribute(request, "comprobante", null);
//		return "corporativo/pdf";
//	}
}
