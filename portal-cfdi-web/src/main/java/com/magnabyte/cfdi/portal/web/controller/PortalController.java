package com.magnabyte.cfdi.portal.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.factory.ClienteFactory;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoPortal;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.utils.StringUtils;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.commons.OpcionDeCatalogoService;
import com.magnabyte.cfdi.portal.service.documento.ComprobanteService;
import com.magnabyte.cfdi.portal.service.documento.TicketService;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que represente el controlador de portal
 */
@Controller
@SessionAttributes({"ticket", "cliente", "establecimiento", "documento"})
@RequestMapping("/portal/cfdi")
public class PortalController {

	private static final Logger logger = LoggerFactory.getLogger(PortalController.class);
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private EstablecimientoService establecimientoService;
	
	@Autowired
	private OpcionDeCatalogoService opcionDeCatalogoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ComprobanteService comprobanteService;
	
	@Autowired
	private DocumentoXmlService documentoXmlService;
	
	@RequestMapping("/menu")
	public String menu() {
		logger.debug("menu portal para clientes");
		return "portal/menu";
	}
	
	@RequestMapping("/buscaTicket")
	public String buscaTicket(ModelMap model) {
		model.put("ticket", new Ticket());
		model.put("establecimiento", new Establecimiento());
		return "portal/buscaTicket";
	}
	
	@RequestMapping(value = "/validaTicket", method = RequestMethod.POST)
	public String validaTicket(@Valid @ModelAttribute Ticket ticket, BindingResult resultTicket, 
			@ModelAttribute Establecimiento establecimiento, ModelMap model) {
		if (resultTicket.hasErrors()) {
			return "portal/buscaTicket";
		}
		logger.debug("controller portal cliente--{}", ticket);
		establecimiento.setClave(StringUtils.formatTicketClaveSucursal(
				ticket.getTransaccion().getTransaccionHeader().getIdSucursal()));
		establecimiento = establecimientoService.readByClave(establecimiento);
		logger.debug("establecimiento {}", establecimiento);
		if (ticketService.ticketExists(ticket, establecimiento)) {
			if (!ticketService.isTicketFacturado(ticket, establecimiento)) {
				model.put("ticket", ticket);
				model.put("establecimiento", establecimiento);
				return "redirect:/portal/cfdi/buscaRfc";
			} else {
				model.put("ticketProcessed", true);
			}
		} else {
			model.put("invalidTicket", true);
		}
		return "portal/buscaTicket";
	}
	
	@RequestMapping("/buscaRfc")
	public String buscaRfc(ModelMap model) {
		logger.debug("buscaRfc page");
		logger.debug("Ticket: ---{}", (Ticket)model.get("ticket"));
		model.put("cliente", new Cliente());
		model.put("emptyList", true);
		return "portal/buscaRfc";
	}
	
	@RequestMapping("/confirmarDatos/{id}")
	public String confirmarDatos(@PathVariable Integer id, ModelMap model) {
		logger.debug("confirmarDatos page");
		model.put("cliente", clienteService.read(ClienteFactory.newInstance(id)));
		return "portal/confirmarDatos";
	}
	
	@RequestMapping("/datosFacturacion/{idDomicilio}")
	public String datosFacturacion(@ModelAttribute Establecimiento establecimiento, @ModelAttribute Cliente cliente, 
			@ModelAttribute Ticket ticket, @PathVariable Integer idDomicilio, ModelMap model) {
		Comprobante comprobante = comprobanteService.obtenerComprobantePor(cliente, ticket, idDomicilio, establecimiento, TipoDocumento.FACTURA);
		DocumentoPortal documento = new DocumentoPortal();
		documento.setCliente(cliente);
		documento.setTicket(ticket);
		documento.setComprobante(comprobante);
		documento.setEstablecimiento(establecimiento);
		documento.setTipoDocumento(TipoDocumento.FACTURA);
		model.put("documento", documento);
		model.put("ticket", ticket);
		return "redirect:/portal/cfdi/confirmarDatosFacturacion";
	}
	
	@RequestMapping("/confirmarDatosFacturacion")
	public String confirmarDatosFacturacion(@ModelAttribute Documento documento, ModelMap model) {
		if(documentoXmlService.isValidComprobanteXml(documento.getComprobante())) {
			model.put("comprobante", documento.getComprobante());
			return "portal/facturaValidate";
		} else {
			logger.error("Error al validar el Comprobante.");
			throw new PortalException("Error al validar el Comprobante.");
		}
	}
}
