package com.magnabyte.cfdi.portal.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.service.commons.OpcionDeCatalogoService;
import com.magnabyte.cfdi.portal.service.documento.TicketService;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;

@Controller
@SessionAttributes({"ticket"})
@RequestMapping("/portal/cfdi")
public class PortalController {

	private static final Logger logger = LoggerFactory.getLogger(PortalController.class);
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private EstablecimientoService establecimientoService;
	
	@Autowired
	private OpcionDeCatalogoService opcionDeCatalogoService;
	
	@RequestMapping("/menu")
	public String menu() {
		logger.debug("menu portal para clientes");
		return "portal/menu";
	}
	
	@RequestMapping("/buscaTicket")
	public String buscaTicket(ModelMap model) {
		model.put("ticket", new Ticket());
		return "portal/buscaTicket";
	}
	
	@RequestMapping(value = "/validaTicket", method = RequestMethod.POST)
	public String validaTicket(@Valid @ModelAttribute Ticket ticket, BindingResult resultTicket, 
			ModelMap model, RedirectAttributes redirectAttributes) {
		logger.debug("controller portal cliente--{}", ticket);
		Establecimiento establecimiento = new Establecimiento();
		establecimiento.setClave(ticketService.formatTicketClave(ticket));
		establecimiento = establecimientoService.readByClave(establecimiento);
		logger.debug("establecimiento {}", establecimiento);
		if (resultTicket.hasErrors()) {
			return "portal/buscaTicket";
		}
		if (ticketService.ticketExists(ticket, establecimiento)) {
			if (!ticketService.ticketProcesado(ticket, establecimiento)) {
				model.put("ticket", ticket);
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
		return "portal/buscaRfc";
	}
	
}
