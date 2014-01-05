package com.magnabyte.cfdi.portal.service.documento;

import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public interface TicketService {

	void save(DocumentoSucursal documento);
	
	boolean ticketExists(Ticket ticket, Establecimiento establecimiento);

	String formatTicketClave(Ticket ticket);

	void updateEstadoFacturado(DocumentoSucursal documento);

	Ticket read(Ticket ticket, Establecimiento establecimiento);

	boolean ticketProcesado(Ticket ticket, Establecimiento establecimiento);
}
