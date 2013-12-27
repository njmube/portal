package com.magnabyte.cfdi.portal.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.sat.cfd._3.Comprobante;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.dao.certificado.CertificadoDao;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.service.codigoqr.CodigoQRService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.util.NumerosALetras;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;
import com.magnabyte.cfdi.portal.web.webservice.DocumentoWebService;

@Controller
@SessionAttributes("documento")
public class DocumentoController {

	private static final Logger logger = LoggerFactory.getLogger(DocumentoController.class);

	@Autowired
	private CodigoQRService codigoQRService;
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private DocumentoXmlService documentoXmlService;
	
	@Autowired
	private DocumentoWebService documentoWebService;
	
	@Autowired
	private CertificadoDao certificadoDao;
	
	@RequestMapping(value = "/generaFactura", method = RequestMethod.POST)
	public String generaFactura(@ModelAttribute Documento documento, ModelMap model) {
		logger.debug("generando factura");
		if (documentoService.sellarComprobante(documento.getComprobante())) {
			if (documentoWebService.timbrarDocumento(documento)) {
				documentoService.save(documento);
			}
			model.put("documento", documento);
		}
		
		return "redirect:/imprimirFactura";
	}
	
	@RequestMapping("/imprimirFactura")
	public String imprimirFactura() {
		logger.debug("Factura generada...");
		return "documento/documentoSuccess";
	}

//	@RequestMapping("/reporte")
//	public String reporte(@ModelAttribute Documento documento, ModelMap model, HttpServletRequest request) {
//		logger.debug("Creando reporte");
//		Locale locale = new Locale("es", "MX");
//		List<Comprobante> comprobantes = new ArrayList<Comprobante>();
//		comprobantes.add(documento.getComprobante());
//		String pathImages = request.getSession().getServletContext().getRealPath("resources/img");
//		if (documento instanceof DocumentoCorporativo ) {
//			model.put("FOLIO_SAP", ((DocumentoCorporativo) documento).getFolioSap());
//		} else if (documento instanceof DocumentoSucursal) {
//			model.put("SUCURSAL", documento.getEstablecimiento().getNombre());
//		}
//		if (documento.getComprobante().getTipoDeComprobante().equals("ingreso")) {
//			model.put("TIPO_DOC", "FACTURA");
//		} else {
//			model.put("TIPO_DOC", "NOTA DE CREDITO");
//		}
//
//		model.put("NUM_SERIE_CERT", certificadoDao.obtenerCertificado());
//		model.put("SELLO_CFD", documento.getTimbreFiscalDigital().getSelloCFD());
//		model.put("SELLO_SAT", documento.getTimbreFiscalDigital().getSelloSAT());
//		model.put("FECHA_TIMBRADO", documento.getTimbreFiscalDigital().getFechaTimbrado());
//		model.put("FOLIO_FISCAL", documento.getTimbreFiscalDigital().getUUID());
//		model.put("CADENA_ORIGINAL", documento.getCadenaOriginal());
//		model.put("PATH_IMAGES", pathImages);
//		model.put(JRParameter.REPORT_LOCALE, locale);
//		model.put("QRCODE", codigoQRService.generaCodigoQR(documento));
//		model.put("LETRAS", NumerosALetras.convertNumberToLetter(documento.getComprobante().getTotal().toString()));
//		model.put("REGIMEN", documento.getComprobante().getEmisor().getRegimenFiscal().get(0).getRegimen());
//		model.put("objetoKey", comprobantes);
//		return "reporte";
//	}
	
	@RequestMapping("/documentoXml")
	public @ResponseBody Comprobante documentoXml(@ModelAttribute Documento documento) {
		return documento.getComprobante();
	}
	
//	@RequestMapping("/documentoXml")
//	public void documentoXml(@ModelAttribute Documento documento, HttpServletResponse response) {
//		try {
//			response.setHeader("Content-Disposition", "attachment; filename=somefile.xml"); 
//			OutputStream out = response.getOutputStream();
//			out.write(documentoXmlService.convierteComprobanteAByteArray(documento.getComprobante()));
//			out.flush();
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	@RequestMapping("/reporte")
	public void reporte(@ModelAttribute Documento documento, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("Creando reporte");
		JasperDesign disenoReporte = null;
		JasperPrint reporteCompleto = null;
		JasperReport reporteCompilado = null;
		String context = request.getContextPath();
//		/src/main/webapp/WEB-INF/reports
		
		String pathPlantilla = context + "src" + 
				File.separator + "main" + File.separator + "webapp" + File.separator +
				"WEB-INF" + File.separator + "reports" + File.separator + "ReporteFactura.jrxml";
		logger.debug(pathPlantilla);
		
		Locale locale = new Locale("es", "MX");
		List<Comprobante> comprobantes = new ArrayList<Comprobante>();
		comprobantes.add(documento.getComprobante());
		String pathImages = request.getSession().getServletContext().getRealPath("resources/img");
		if (documento instanceof DocumentoCorporativo ) {
			model.put("FOLIO_SAP", ((DocumentoCorporativo) documento).getFolioSap());
		} else if (documento instanceof DocumentoSucursal) {
			model.put("SUCURSAL", documento.getEstablecimiento().getNombre());
		}
		if (documento.getComprobante().getTipoDeComprobante().equals("ingreso")) {
			model.put("TIPO_DOC", "FACTURA");
		} else {
			model.put("TIPO_DOC", "NOTA DE CREDITO");
		}

		model.put(JRParameter.REPORT_LOCALE, locale);
		model.put("NUM_SERIE_CERT", certificadoDao.obtenerCertificado());
		model.put("SELLO_CFD", documento.getTimbreFiscalDigital().getSelloCFD());
		model.put("SELLO_SAT", documento.getTimbreFiscalDigital().getSelloSAT());
		model.put("FECHA_TIMBRADO", documento.getTimbreFiscalDigital().getFechaTimbrado());
		model.put("FOLIO_FISCAL", documento.getTimbreFiscalDigital().getUUID());
		model.put("CADENA_ORIGINAL", documento.getCadenaOriginal());
		model.put("PATH_IMAGES", pathImages);
		model.put("QRCODE", codigoQRService.generaCodigoQR(documento));
		model.put("LETRAS", NumerosALetras.convertNumberToLetter(documento.getComprobante().getTotal().toString()));
		model.put("REGIMEN", documento.getComprobante().getEmisor().getRegimenFiscal().get(0).getRegimen());
		model.put("objetoKey", comprobantes);
		
		JRBeanCollectionDataSource dataSource = 
				new JRBeanCollectionDataSource(documento.getComprobante().getConceptos().getConcepto());
		
		try {
			response.setHeader("Content-Disposition", "attachment; filename=reporteFactura.xml"); 
			disenoReporte = JRXmlLoader.load(pathPlantilla);
			reporteCompilado = JasperCompileManager.compileReport(disenoReporte);
			reporteCompleto = JasperFillManager
					.fillReport(reporteCompilado, model, dataSource);
			
			byte[] bytesReport = JasperExportManager.exportReportToPdf(reporteCompleto);
			
			OutputStream out = response.getOutputStream();
			out.write(bytesReport);
			out.flush();
			out.close();
			
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Comprobante> getModel(Documento documento) {
		List<Comprobante> comprobantes = new ArrayList<Comprobante>();
		
		
		return comprobantes;
	}
	
}
