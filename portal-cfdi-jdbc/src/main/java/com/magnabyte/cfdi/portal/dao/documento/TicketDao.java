package com.magnabyte.cfdi.portal.dao.documento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.TipoEstadoTicket;

public interface TicketDao {

	void save(DocumentoSucursal documento);

	void updateEstadoFacturado(DocumentoSucursal documento);

	Ticket readByStatus(Ticket ticket, Establecimiento establecimiento, TipoEstadoTicket estadoTicket);
	
	Ticket read(Ticket ticket, Establecimiento establecimiento);

	List<String> readArticulosSinPrecio();

	Integer readIdDocFromTicketGuardado(DocumentoSucursal documento);

	List<String> readAllByDate(String fecha);

}
