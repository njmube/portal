package com.magnabyte.cfdi.portal.service.documento.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.dao.documento.TicketDao;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.documento.TicketService;

@Service("ticketService")
public class TicketServiceImpl implements TicketService {

	private static final Logger logger = 
			LoggerFactory.getLogger(TicketServiceImpl.class);
	
	@Autowired
	TicketDao ticketDao;
	
	@Override
	public void save(DocumentoSucursal documento) {
		if(((DocumentoSucursal) documento).getTicket() != null) {
			ticketDao.save((DocumentoSucursal) documento);
		} else {
			logger.debug("El Ticket no puede ser nulo.");
			throw new PortalException("El Ticket no puede ser nulo.");
		}
	}

}
