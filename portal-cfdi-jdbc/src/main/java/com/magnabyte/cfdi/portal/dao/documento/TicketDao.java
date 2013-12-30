package com.magnabyte.cfdi.portal.dao.documento;

import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public interface TicketDao {

	void save(DocumentoSucursal documento);

	Ticket read(Ticket ticket, Establecimiento establecimiento);
}
