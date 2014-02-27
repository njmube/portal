package com.magnabyte.cfdi.portal.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRParameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.DefaultSessionAttributeStore;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.factory.ClienteFactory;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.documento.DocumentoPortal;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.codigoqr.CodigoQRService;
import com.magnabyte.cfdi.portal.service.commons.OpcionDeCatalogoService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.util.NumerosALetras;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;
import com.magnabyte.cfdi.portal.web.cfdi.CfdiService;
import com.magnabyte.cfdi.portal.web.webservice.DocumentoWebService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que represente el controlador de documento
 */
@Controller
@SessionAttributes({"documento", "cliente"})
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
	private ServletContext servletContext;
	
	@Autowired
	private CfdiService cfdiService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private OpcionDeCatalogoService opcionDeCatalogoService;
	
	@Value("${generic.rfc.extranjeros}")
	private String genericRfcExtranjeros;
	
	@RequestMapping(value = {"/generarDocumento", "/portal/cfdi/generarDocumento"}, method = RequestMethod.POST)
	public String generarDocumento(@ModelAttribute Documento documento, ModelMap model, final RedirectAttributes redirectAttributes) {
		logger.debug("generando documento");

		documentoService.guardarDocumento(documento);
		if (documento instanceof DocumentoCorporativo) {
			//FIXME Quitar para produccion
			documento.getComprobante().getEmisor().setRfc("AAA010101AAA");
			cfdiService.generarDocumentoCorp(documento);
		} else {
			cfdiService.generarDocumento(documento);
		}
		redirectAttributes.addFlashAttribute("documento", documento);
		
		if (documento instanceof DocumentoPortal) {
			return "redirect:/portal/cfdi/imprimirFactura";
		} else {
			return "redirect:/imprimirFactura";
		}
	}

	@RequestMapping(value = {"/imprimirFactura", "/portal/cfdi/imprimirFactura"})
	public String imprimirFactura(@ModelAttribute Documento documento, HttpServletRequest request, 
			DefaultSessionAttributeStore store, WebRequest webRequest, ModelMap model) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap == null) {
			model.remove("documento");
			store.cleanupAttribute(webRequest, "documento");
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication instanceof AnonymousAuthenticationToken) {
				return "redirect:/portal/cfdi/menu";
			} else {
				return "redirect:/menuPage";
			}
		}
		logger.debug("Factura generada...");
		if (documento instanceof DocumentoCorporativo) {
			return "documento/documentoSuccessCorp";
		} else {
			return "documento/documentoSuccess";
		}
	}
	
	@RequestMapping("/restartFlow")
	public String restarFlow(SessionStatus status, DefaultSessionAttributeStore store, WebRequest webRequest, ModelMap model) {
		logger.debug("reiniciando flujo");
		model.remove("documento");
		store.cleanupAttribute(webRequest, "documento");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof AnonymousAuthenticationToken) {
			return "redirect:/portal/cfdi/menu";
		} else {
			return "redirect:/menuPage";
		}
	}

	@RequestMapping(value = {"/reporte/{filename}", "/portal/cfdi/reporte/{filename}"})
	public String reporte(@ModelAttribute Documento documento, @PathVariable String filename, 
			ModelMap model) {
		logger.debug("Creando reporte");
		Locale locale = new Locale("es", "MX");
		List<Comprobante> comprobantes = new ArrayList<Comprobante>();
		comprobantes.add(documento.getComprobante());
		String pathImages = servletContext.getRealPath("resources/img");
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
		model.put("IVA", documento.getComprobante().getImpuestos().getTraslados().getTraslado().get(0).getTasa());
		model.put("objetoKey", comprobantes);
		return ("reporte");
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
	
	@RequestMapping(value = {"/documentoDownloadXml/{idDocumento}/{fileName}"
			, "/portal/cfdi/documentoDownloadXml/{idDocumento}/{fileName}"})
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
	
	@RequestMapping(value = {"/documentoDownloadPdf/{idDocumento}/{fileName}/{origin}"
			, "/portal/cfdi/documentoDownloadPdf/{idDocumento}/{fileName}/{origin}"})
	public String documentoDownloadPdf (@PathVariable Integer idDocumento, 
			@PathVariable String fileName, @PathVariable String origin, ModelMap model) {
		
		Documento documento = new Documento();
		documento.setId(idDocumento);
		model.put("documento", documentoService.findById(documento));
		
		if (origin.equals("in")) {
			return "redirect:/reporte/" + fileName;
		} else {
			return "redirect:/portal/cfdi/reporte/" + fileName;
		}
	}

	
	@RequestMapping("/portal/cfdi/documentoEnvio") 
	public @ResponseBody Boolean documentoEnvio(@RequestParam Integer idDocumento, 
			@RequestParam String fileName, @RequestParam String email) {
		try {
			cfdiService.envioDocumentosFacturacion(email, fileName, idDocumento);
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
	public String listaDocumentos(ModelMap model, @ModelAttribute Cliente cliente, 
			@RequestParam String fechaInicial, @RequestParam String fechaFinal) {
		logger.debug("Obteniendo la lista de documentos");
		List<Documento> documentos = documentoService.getDocumentos(cliente, fechaInicial, fechaFinal);
		if(documentos != null && !documentos.isEmpty()) {
			model.put("emptyList", false);
		}
		model.put("documentos", documentos);
		
		return "documento/listaDocumentos";
	}

	@RequestMapping("/refacturarForm")
	public String refacturar(ModelMap model) {
		model.put("documento", new DocumentoSucursal());
		return "documento/refacturarForm";
	}
	
	@RequestMapping(value = "/validaSerieFolio", method = RequestMethod.POST)
	public String validaSerieFolio(@ModelAttribute Documento documento, ModelMap model) {
		logger.debug("validaSerieFolio");
		try {
			documentoService.findBySerieFolioImporte(documento);
			return "redirect:/modificarFactura";
		} catch (PortalException ex) {
			model.put("error", true);
			model.put("messageError", ex.getMessage());
			return "documento/refacturarForm";
		}
	}
	
	@RequestMapping("/modificarFactura")
	public String modificarFactura(@ModelAttribute Documento documento) {
		logger.debug("modificarFactura");
		return "documento/modificarFactura";
	}
	
	@RequestMapping(value = "/refacturacion/listaClientes", method = RequestMethod.POST)
	public String listaClientes(ModelMap model, @ModelAttribute Cliente cliente) {		
		List<Cliente> clientes = clienteService.findClientesByNameRfc(cliente);
		if(!clientes.isEmpty()) {
			model.put("emptyList", false);
		}
		model.put("clientes", clientes);
		return "documento/listaClientes";
	}
	
	@RequestMapping(value = {"/refacturacion/clienteForm"})
	public String clienteForm(@ModelAttribute Cliente cliente, ModelMap model) {
		logger.debug("regresando forma cliente");
		model.put("cliente", cliente);
		model.put("rfcExtranjeros", genericRfcExtranjeros);
		model.put("listaPaises", opcionDeCatalogoService.getCatalogo("c_pais", "id_pais"));
		model.put("emptyList", true);
		return "documento/clienteForm";
	}
	
	@RequestMapping(value = "/refacturacion/confirmarDatos/{viewError}", method = RequestMethod.POST)
	public String confirmarDatos(@Valid @ModelAttribute("clienteCorregir") Cliente cliente, BindingResult result, ModelMap model, 
			@PathVariable String viewError) {
		logger.debug("Confimar datos");	
		if (result.hasErrors()) {
			model.put("errorSave", true);
			model.put("errorMessage", result.getAllErrors());
			model.put("listaPaises", opcionDeCatalogoService
					.getCatalogo("c_pais", "id_pais"));
			model.put("rfcExtranjeros", genericRfcExtranjeros);
			logger.debug(result.getAllErrors().toString());
			
			return "documento/" + viewError;
		}
		
		if(cliente.getId() != null) {		
			clienteService.update(cliente);
		} else {
			try {
				clienteService.save(cliente);
			} catch (PortalException ex) {
				model.put("errorSave", true);
				model.put("errorMessage", ex.getMessage());
				model.put("listaPaises", opcionDeCatalogoService
						.getCatalogo("c_pais", "id_pais"));
				model.put("rfcExtranjeros", genericRfcExtranjeros);
				return "documento/" + viewError;
			}
		}
		model.put("cliente", cliente);
		logger.debug("Cliente: {}", cliente.getId());		
		return "redirect:/refacturacion/confirmarDatos/" + cliente.getId();
	}
	
	@RequestMapping("/refacturacion/clienteCorregir/{id}")
	public String corregirDatos(@PathVariable Integer id, ModelMap model) {
		logger.debug("confirmarDatos page");
		Cliente cliente = clienteService.read(ClienteFactory.newInstance(id));
		model.put("clienteCorregir", cliente);
		model.put("rfcExtranjeros", genericRfcExtranjeros);
		model.put("listaEstados", opcionDeCatalogoService.getCatalogoParam("c_estado", "id_pais", 
				cliente.getDomicilios().get(0).getEstado().getPais().getId().toString(), "id_estado"));
		return "documento/clienteCorregir";
	}
	
	@RequestMapping("/refacturacion/buscaRfc")
	public String buscaRfc(ModelMap model) {
		logger.debug("buscaRfc page");
		logger.debug("Ticket: ---{}", (Ticket)model.get("ticket"));
		model.put("cliente", new Cliente());
		model.put("emptyList", true);
		return "documento/buscaRfc";
	}
	
	@RequestMapping("/refacturacion/confirmarDatos/{id}")
	public String confirmarDatos(@PathVariable Integer id, ModelMap model) {
		logger.debug("confirmarDatos page");
		model.put("cliente", clienteService.read(ClienteFactory.newInstance(id)));
		return "documento/confirmarDatos";
	}
	
	@RequestMapping("/refacturacion/cambiarCliente/{id}")
	public String prueba(@ModelAttribute Documento documento, @ModelAttribute Cliente cliente) {
		logger.debug("prueba");
		documento.getComprobante().getReceptor().setRfc(cliente.getRfc());
		return "documento/modificarFactura";
	}
}
