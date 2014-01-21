package com.magnabyte.cfdi.portal.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magnabyte.cfdi.portal.model.ticket.ListaTickets;
import com.magnabyte.cfdi.portal.service.documento.TicketService;

@Controller
public class TicketController {

	private static final Logger logger = 
			LoggerFactory.getLogger(TicketController.class);
	    
	@Autowired
    private TicketService ticketService;
	 
	@RequestMapping(value = "/portal/cfdi/tickets", method = RequestMethod.POST)
	@ResponseBody
	//FIXME Cambiar a regresar un List<Ticket>
	public String listaTickets(@RequestBody ListaTickets tickets) {
		logger.debug("Entrando al metodo que recibe los tickets");
		
		return ticketService.recibeTicketsWsdl(tickets); 
	}
	
	@RequestMapping(value = "/portal/cfdi/hola", method = RequestMethod.POST)
	@ResponseBody
	public String saludo() {
		return ticketService.hola();
	}
}
