package com.magnabyte.cfdi.portal.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magnabyte.cfdi.portal.model.establecimiento.factory.EstablecimientoFactory;
import com.magnabyte.cfdi.portal.model.ticket.ListaTickets;
import com.magnabyte.cfdi.portal.model.utils.StringUtils;
import com.magnabyte.cfdi.portal.service.documento.TicketService;
import com.magnabyte.cfdi.portal.web.cfdi.CfdiService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que represente el controlador de ticket
 */
@Controller
public class TicketController {

	private static final Logger logger = 
			LoggerFactory.getLogger(TicketController.class);
	    
	@Autowired
    private TicketService ticketService;
	
	@Autowired
	private CfdiService cfdiService;
	 
	@RequestMapping(value = "/portal/cfdi/tickets", method = RequestMethod.POST)
	@ResponseBody
	public String listaTickets(@RequestBody ListaTickets tickets) {
		logger.debug("Entrando al metodo que recibe los tickets.");
		
		cfdiService.closeOfDay(EstablecimientoFactory
				.newInstanceClave(StringUtils.formatTicketClaveSucursal(
						tickets.getClaveEstablecimiento())), tickets);
		
		return ticketService.recibeTicketsWsdl(tickets); 
	}
	
	@RequestMapping(value = "/portal/cfdi/fechaCierre/{noEstablecimiento}")
	@ResponseBody
	public String fechaCierre(@PathVariable String noEstablecimiento) {
		
		logger.debug("Entrando al metodo que recibe el no. de establecimiento.");
		logger.debug("Recibiendo establecimiento: {}", noEstablecimiento);
		
		return ticketService.fechaCierre(noEstablecimiento);
	}
}
