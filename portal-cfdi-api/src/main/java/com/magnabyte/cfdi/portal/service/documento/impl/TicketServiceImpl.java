package com.magnabyte.cfdi.portal.service.documento.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.documento.TicketDao;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.service.documento.TicketService;

@Service("ticketService")
public class TicketServiceImpl implements TicketService {

	private static final Logger logger = 
			LoggerFactory.getLogger(TicketServiceImpl.class);
	
	@Autowired
	private TicketDao ticketDao;
	
	@Transactional
	@Override
	public void save(DocumentoSucursal documento) {
		if(documento.getTicket() != null) {
			ticketDao.save(documento);
		} else {
			logger.debug("El Ticket no puede ser nulo.");
			throw new PortalException("El Ticket no puede ser nulo.");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public boolean ticketProcesado(Ticket ticket, Establecimiento establecimiento) {
		Ticket ticketDB = ticketDao.read(ticket, establecimiento);
		return ticketDB != null ? true : false;
	}

}
