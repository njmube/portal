package com.magnabyte.cfdi.portal.web.controller;

import java.util.List;

import jcifs.smb.NtlmPasswordAuthentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.service.cfdi.v32.CfdiV32Service;
import com.magnabyte.cfdi.portal.service.documento.ComprobanteService;
import com.magnabyte.cfdi.portal.service.samba.SambaService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el controlador de establecimiento corporativo
 */
@Controller
@SessionAttributes({"establecimiento", "documento"})
public class CorporativoController {

	private static final Logger logger = LoggerFactory.getLogger(CorporativoController.class);

	@Autowired
	private SambaService sambaService;
	
	@Autowired
	private CfdiV32Service cfdiV32Service;
	
	@Autowired
	private DocumentoXmlService documentoXmlService;
	
	@Autowired
	private ComprobanteService comprobanteService;
	
	@RequestMapping("/facturaCorp")
	public String facturaCorp(@ModelAttribute Establecimiento establecimiento, ModelMap model) {
		logger.debug("facturaCorp...");
		String urlSapFiles = establecimiento.getRutaRepositorio().getRutaRepositorio() 
				+ establecimiento.getRutaRepositorio().getRutaRepoIn();
		NtlmPasswordAuthentication authentication = sambaService.getAuthentication(establecimiento);
		List<DocumentoCorporativo> documentos = sambaService.getFilesFromDirectory(urlSapFiles, authentication);
		model.put("documentos", documentos);
		return "corporativo/facturaCorp";
	}
	
	@RequestMapping("/facturaCorp/validate/{fileName:.+\\.xml$}")
	public String validarFactura(@ModelAttribute Establecimiento establecimiento, @PathVariable String fileName, ModelMap model) {
		logger.debug("valida factura");
		String urlSapFiles = establecimiento.getRutaRepositorio().getRutaRepositorio() + establecimiento.getRutaRepositorio().getRutaRepoIn();
		NtlmPasswordAuthentication authentication = sambaService.getAuthentication(establecimiento);
		DocumentoCorporativo documento = documentoXmlService.convertXmlSapToCfdi(sambaService.getFileStream(urlSapFiles, fileName, authentication));
		documento.setCliente(comprobanteService.obtenerClienteDeComprobante(documento.getComprobante()));
		documento.setFolioSap(fileName.substring(1, 11));
		documento.setNombreXmlPrevio(fileName);
		documento.setEstablecimiento(establecimiento);
		model.put("comprobante", documento.getComprobante());
		model.put("documento", documento);
		return "redirect:/facturaCorp/confirmarDatosFacturacion";
	}
	
	@RequestMapping("/facturaCorp/confirmarDatosFacturacion")
	public String confirmarDatosFacturacion(@ModelAttribute Documento documento, ModelMap model) {
		cfdiV32Service.isValidComprobanteXml(documento.getComprobante());
		return "corporativo/facturaValidate";
	}
	
}
