package com.magnabyte.cfdi.portal.web.controller;

import java.util.List;
import java.util.Locale;

import mx.gob.sat.cfd._3.Comprobante;
import net.sf.jasperreports.engine.JRParameter;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.magnabyte.cfdi.portal.service.CodigoQR.CodigoQRService;
import com.magnabyte.cfdi.portal.service.factura.FacturaService;
import com.magnabyte.cfdi.portal.service.util.NumerosALetras;

@Controller
public class DocumentoController {

	private static final Logger logger = LoggerFactory
			.getLogger(DocumentoController.class);

	@Autowired
	private FacturaService facturaService;

	@Autowired
	private CodigoQRService codigoQRService;

	@RequestMapping("/cfdi/reportep")
	public String reporte(ModelMap model) {
		logger.debug("Creando reporte");
		Locale locale = new Locale("es", "MX");
		List<Comprobante> comprobantes = facturaService.obtenerDatos();
		model.put(JRParameter.REPORT_LOCALE, locale);
		model.put("QRCODE", codigoQRService.generaCodigoQR(facturaService
				.obtenerDatos().get(0)));
		model.put(
				"LETRAS",
				NumerosALetras.convertNumberToLetter(comprobantes.get(0)
						.getTotal().toString()));
		model.put("REGIMEN", comprobantes.get(0).getEmisor().getRegimenFiscal()
				.get(0).getRegimen());
		model.put("objetoKey", comprobantes);
		return "Reporte";
	}
}
