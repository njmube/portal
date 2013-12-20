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

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.factory.ClienteFactory;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.samba.SambaService;

@Controller
@SessionAttributes({"establecimiento", "ticket", "cliente"})
public class SucursalCfdiController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private SambaService sambaService;
	
	private static final Logger logger = LoggerFactory.getLogger(SucursalCfdiController.class);
	
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
}
