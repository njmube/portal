package com.magnabyte.cfdi.portal.web.controller;

import javax.validation.Valid;

import mx.gob.sat.cfd._3.Comprobante;

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

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.factory.ClienteFactory;
import com.magnabyte.cfdi.portal.model.commons.factory.OpcionDeCatalogoFactory;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;
import com.magnabyte.cfdi.portal.service.samba.SambaService;

@Controller
@SessionAttributes({"establecimiento", "ticket", "cliente", "documento"})
public class SucursalController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private SambaService sambaService;
	
	@Autowired
	private DocumentoService documentoService;
	
	private static final Logger logger = LoggerFactory.getLogger(SucursalController.class);
	
	@RequestMapping("/buscaTicket")
	public String buscaTicket(ModelMap model) {
		model.put("ticket", new Ticket());
		return "sucursal/buscaTicket";
	}
	
	@RequestMapping(value = "/validaTicket", method = RequestMethod.POST)
	public String validaTicket(@Valid @ModelAttribute Ticket ticket, BindingResult resultTicket, 
			@ModelAttribute Establecimiento establecimiento, ModelMap model) {
		logger.debug("controller--{}", ticket);
		if (resultTicket.hasErrors()) {
			return "sucursal/buscaTicket";
		}
		if (sambaService.ticketExists(ticket, establecimiento)) {
			model.put("ticket", ticket);
			return "redirect:/buscaRfc";
		}
		model.put("invalidTicket", true);
		return "sucursal/buscaTicket";
	}
	
	@RequestMapping("/buscaRfc")
	public String buscaRfc(ModelMap model) {
		logger.debug("buscaRfc page");
		logger.debug("Ticket: ---{}", (Ticket)model.get("ticket"));
		model.put("cliente", new Cliente());
		model.put("emptyList", true);
		return "sucursal/buscaRfc";
	}
	
	@RequestMapping("/confirmarDatos/{id}")
	public String confirmarDatos(@PathVariable Integer id, ModelMap model) {
		logger.debug("confirmarDatos page");
		model.put("cliente", clienteService.read(ClienteFactory.newInstance(id)));
		return "sucursal/confirmarDatos";
	}
	
	@RequestMapping("/datosFacturacion/{idDomicilio}")
	public String datosFacturacion(@ModelAttribute Establecimiento establecimiento, @ModelAttribute Cliente cliente, 
			@ModelAttribute Ticket ticket, @PathVariable Integer idDomicilio, ModelMap model) {
		Comprobante comprobante = documentoService.obtenerComprobantePor(cliente, ticket, idDomicilio, establecimiento);
		DocumentoSucursal documento = new DocumentoSucursal();
		documento.setCliente(cliente);
		ticket.setStatus(OpcionDeCatalogoFactory.newInstance(1));
		documento.setTicket(ticket);
		documento.setComprobante(comprobante);
		documento.setEstablecimiento(establecimiento);
		model.put("documento", documento);
		model.put("comprobante", comprobante);
		model.put("ticket", ticket);
		return "sucursal/facturaValidate";
	}
}
