package com.magnabyte.cfdi.portal.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.service.CodigoQR.CodigoQRService;
import com.magnabyte.cfdi.portal.service.factura.FacturaService;
import com.magnabyte.cfdi.portal.service.util.NumerosALetras;

@Controller
@SessionAttributes("comprobante")
public class DocumentoController {

	private static final Logger logger = LoggerFactory
			.getLogger(DocumentoController.class);

	@Autowired
	private FacturaService facturaService;
	
	@Autowired
	private CodigoQRService codigoQRService;

	@RequestMapping("/reporte")
	public String reporte(@ModelAttribute Comprobante comprobante, ModelMap model, HttpServletRequest request) {
		logger.debug("Creando reporte");
		Locale locale = new Locale("es", "MX");
		List<Comprobante> comprobantes = new ArrayList<Comprobante>();
		comprobantes.add(comprobante);
		String pathImages = request.getSession().getServletContext().getRealPath("resources/img");
		logger.debug("-----{}", pathImages);
		model.put("PATH_IMAGES", pathImages);
		model.put(JRParameter.REPORT_LOCALE, locale);
		model.put("QRCODE", codigoQRService.generaCodigoQR(facturaService.obtenerDatos().get(0)));
		model.put("LETRAS", NumerosALetras.convertNumberToLetter(comprobantes.get(0).getTotal().toString()));
		model.put("REGIMEN", comprobantes.get(0).getEmisor().getRegimenFiscal()
				.get(0).getRegimen());
		model.put("objetoKey", comprobantes);
		return "Reporte";
	}
}
