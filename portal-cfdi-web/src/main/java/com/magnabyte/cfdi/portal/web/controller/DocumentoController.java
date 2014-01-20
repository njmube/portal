package com.magnabyte.cfdi.portal.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.sat.cfd._3.Comprobante;
import net.sf.jasperreports.engine.JRParameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.documento.DocumentoPortal;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;
import com.magnabyte.cfdi.portal.service.codigoqr.CodigoQRService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.util.NumerosALetras;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;
import com.magnabyte.cfdi.portal.web.cfdi.CfdiService;
import com.magnabyte.cfdi.portal.web.webservice.DocumentoWebService;

@Controller
@SessionAttributes("documento")
public class DocumentoController {

	private static final Logger logger = LoggerFactory
			.getLogger(DocumentoController.class);

	@Autowired
	private CodigoQRService codigoQRService;

	@Autowired
	private DocumentoService documentoService;

	@Autowired
	private DocumentoXmlService documentoXmlService;

	@Autowired
	private DocumentoWebService documentoWebService;
	
	@Autowired
	private CfdiService cfdiService;
	
	@RequestMapping(value = {"/generarDocumento", "/portal/cfdi/generarDocumento"}, method = RequestMethod.POST)
	public String generarDocumento(@ModelAttribute Documento documento, ModelMap model) {
		logger.debug("generando documento");

		cfdiService.generarDocumento(documento);
		model.put("documento", documento);
		
		if (documento instanceof DocumentoPortal) {
			return "redirect:/portal/cfdi/imprimirFactura";
		} else {
			return "redirect:/imprimirFactura";
		}
	}

	@RequestMapping(value = {"/imprimirFactura", "/portal/cfdi/imprimirFactura"})
	public String imprimirFactura() {
		logger.debug("Factura generada...");
		return "documento/documentoSuccess";
	}
	
	//FIXME verificar limpieza de session
	@RequestMapping("/restartFlow")
	public String restarFlow(SessionStatus status) {
		logger.debug("reiniciando flujo");
		return "redirect:/menu";
	}

	@RequestMapping(value = {"/reporte", "/portal/cfdi/reporte"})
	public String reporte(@ModelAttribute Documento documento, ModelMap model,
			HttpServletRequest request) {
		logger.debug("Creando reporte");
		Locale locale = new Locale("es", "MX");
		List<Comprobante> comprobantes = new ArrayList<Comprobante>();
		comprobantes.add(documento.getComprobante());
		String pathImages = request.getSession().getServletContext().getRealPath("resources/img");
		if (documento instanceof DocumentoCorporativo) {
			model.put("FOLIO_SAP", ((DocumentoCorporativo) documento).getFolioSap());
		} else if (documento instanceof DocumentoSucursal) {
			model.put("SUCURSAL", documento.getEstablecimiento().getNombre());
		}
		model.put("TIPO_DOC", documento.getTipoDocumento().getNombre());
		model.put("NUM_SERIE_CERT", documentoXmlService.obtenerNumCertificado(documento.getXmlCfdi()));
		model.put("SELLO_CFD", documento.getTimbreFiscalDigital().getSelloCFD());
		model.put("SELLO_SAT", documento.getTimbreFiscalDigital().getSelloSAT());
		model.put("FECHA_TIMBRADO", documento.getTimbreFiscalDigital().getFechaTimbrado());
		model.put("FOLIO_FISCAL", documento.getTimbreFiscalDigital().getUUID());
		model.put("CADENA_ORIGINAL", documento.getCadenaOriginal());
		model.put("PATH_IMAGES", pathImages);
		model.put(JRParameter.REPORT_LOCALE, locale);
		model.put("QRCODE", codigoQRService.generaCodigoQR(documento));
		model.put("LETRAS", NumerosALetras.convertNumberToLetter(documento.getComprobante().getTotal().toString()));
		model.put("REGIMEN", documento.getComprobante().getEmisor().getRegimenFiscal().get(0).getRegimen());
		model.put("objetoKey", comprobantes);
		return "reporte";
	}

	@RequestMapping(value = {"/documentoXml", "/portal/cfdi/documentoXml"})
	public void documentoXml(@ModelAttribute Documento documento,
			HttpServletResponse response) {
		try {			
			String filename = documento.getTipoDocumento() + "_" + documento.getComprobante().getSerie() + "_" + documento.getComprobante().getFolio() + ".xml";
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			OutputStream out = response.getOutputStream();
			out.write(documentoXmlService.convierteComprobanteAByteArray(documento.getComprobante(), PortalUtils.encodingUTF8));
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.debug("Ocurrió un error al descargar el XML.", e);
			throw new PortalException("Ocurrió un error al descargar el XML.", e);
		}
	}
	
	@RequestMapping(value = {"/documentoDownloadXml/{idDoc}/{fileName}"
			, "/portal/cfdi/documentoDownloadXml/{idDoc}/{fileName}"})
	public void documentoDownloadXml(@PathVariable Integer idDocumento, 
			@PathVariable String fileName, HttpServletResponse response) {
		try {						
			Documento documento = new Documento();
			documento.setId(idDocumento);
			documento = documentoService.read(documento);
			byte [] doc = documentoService.recuperarDocumentoXml(documento);
			
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xml");
			OutputStream out = response.getOutputStream();
			out.write(doc);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.debug("Ocurrió un error al descargar el XML.", e);
			throw new PortalException("Ocurrió un error al descargar el XML.", e);
		}
	}
	
	@RequestMapping(value = {"/documentoDownloadPdf/{idDoc}/{fileName}/{origin}"
			, "/portal/cfdi/documentoDownloadPdf/{idDoc}/{fileName}/{origin}"})
	public String documentoDownloadPdf (@PathVariable Integer idDoc, 
			@PathVariable String fileName, @PathVariable String origin, ModelMap model) {
		
		Documento documento = new Documento();
		documento.setId(idDoc);
		model.put("documento", documentoService.findById(documento));
		
		if (origin.equals("in")) {
			return "redirect:/reporte";
		} else {
			return "redirect:/portal/cfdi/reporte";
		}
	}

	
	@RequestMapping("/portal/cfdi/documentoEnvio") 
	public @ResponseBody Boolean documentoEnvio(@RequestParam Integer idDocumento, 
			@RequestParam String fileName, @RequestParam String email, HttpServletRequest request) {
		try {
			documentoService.envioDocumentosFacturacionPorXml(email, fileName, idDocumento, request);
		} catch (PortalException ex) {
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = {"/buscarDocs", "/portal/cfdi/buscarDocs"})	
	public String buscaDocumentos(ModelMap model) {
		model.put("cliente", new Cliente());
		model.put("emptyList", true);
		return "documento/buscaDocumentos";
	}
	
	@RequestMapping("/portal/cfdi/listaDocumentos")
	public String listaDocumentos(ModelMap model, @ModelAttribute Cliente cliente) {
		logger.debug("Opteniendo la lista de documentos");
		List<Documento> documentos = documentoService.getDocumentos(cliente);
		if(documentos != null && !documentos.isEmpty()) {
			model.put("emptyList", false);
		}
		model.put("documentos", documentos);
		
		return "documento/listaDocumentos";
	}
	
}
