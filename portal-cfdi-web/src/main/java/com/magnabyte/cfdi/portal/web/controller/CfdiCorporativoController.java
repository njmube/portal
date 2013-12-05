package com.magnabyte.cfdi.portal.web.controller;

import java.util.List;

import mx.gob.sat.cfd._3.Comprobante;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.model.documento.DocumentoFile;
import com.magnabyte.cfdi.portal.model.sucursal.Sucursal;
import com.magnabyte.cfdi.portal.service.samba.SambaService;

@Controller
@SessionAttributes("sucursal")
public class CfdiCorporativoController {

	private static final Logger logger = LoggerFactory.getLogger(CfdiCorporativoController.class);

	@Autowired
	private SambaService sambaService;
	
	@RequestMapping("/facturaCorp")
	public String menuCorp(ModelMap model) {
		logger.debug("facturaCorp...");
		Sucursal sucursal = (Sucursal) model.get("sucursal");
		logger.debug("Se obtendran los datos de la sucursal: {}", sucursal);
		List<DocumentoFile> documentos = sambaService.getFilesFromDirectory(sucursal.getRutaRepositorio());
		model.put("documentos", documentos);
		return "corporativo/facturaCorp";
	}
	
	@RequestMapping("/facturaCorp/validate/{fileName:.+\\.[a-z]+}")
	public String validarFactura(@PathVariable String fileName, ModelMap model) {
		logger.debug("valida factura");
		logger.debug(fileName);
		logger.debug(((Sucursal)model.get("sucursal")).getRutaRepositorio() + fileName);
		Comprobante comprobante = sambaService.getFile(((Sucursal)model.get("sucursal")).getRutaRepositorio(), fileName);
		logger.debug("el comprobante {}", comprobante);
		model.put("comprobante", comprobante);
		return "corporativo/facturaValidate";
	}
}
