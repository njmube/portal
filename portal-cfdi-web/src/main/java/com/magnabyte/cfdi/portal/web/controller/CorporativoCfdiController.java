package com.magnabyte.cfdi.portal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.cfd._3.Comprobante;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import com.magnabyte.cfdi.portal.model.documento.DocumentoFile;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.samba.SambaService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;
import com.magnabyte.cfdi.portal.web.webservice.DocumentoWebService;

@Controller
@SessionAttributes({"establecimiento", "comprobante"})
public class CorporativoCfdiController {

	private static final Logger logger = LoggerFactory.getLogger(CorporativoCfdiController.class);

	@Autowired
	private SambaService sambaService;
	
	@Autowired
	private DocumentoXmlService documentoXmlService;
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private DocumentoWebService documentoWebService;
	
	@RequestMapping("/facturaCorp")
	public String facturaCorp(@ModelAttribute Establecimiento sucursal, ModelMap model) {
		logger.debug("facturaCorp...");
		List<DocumentoFile> documentos = sambaService.getFilesFromDirectory(sucursal.getRutaRepositorio());
		model.put("documentos", documentos);
		return "corporativo/facturaCorp";
	}
	
	@RequestMapping("/facturaCorp/validate/{fileName:.+\\.[a-z]+}")
	public String validarFactura(@ModelAttribute Establecimiento sucursal, @PathVariable String fileName, ModelMap model) {
		logger.debug("valida factura");
		logger.debug(sucursal.getRutaRepositorio() + fileName);
		Comprobante comprobante = documentoXmlService.convertXmlSapToCfdi(sambaService.getFileStream(sucursal.getRutaRepositorio(), fileName));
		logger.debug("el comprobante {}", comprobante);
		model.put("folioSap", fileName.substring(1, 11));
		model.put("comprobante", comprobante);
		return "corporativo/facturaValidate";
	}
	
	@RequestMapping(value = "/generaFactura", method = RequestMethod.POST)
	public String generaFactura(@ModelAttribute Comprobante comprobante) {
		logger.debug("generando factura");
		if (documentoService.sellarDocumento(comprobante)) {
			documentoWebService.timbrarDocumento(comprobante);
		}
		
		return "corporativo/pdf";
	}
	
	@RequestMapping("/limpia")
	public String limpia(ModelMap model, HttpServletRequest request) {
		logger.debug("cleaning");
		model.remove("comprobante");
		WebUtils.setSessionAttribute(request, "comprobante", null);
		return "corporativo/pdf";
	}
}
