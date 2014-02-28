package com.magnabyte.cfdi.portal.dao.documento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.TipoEstadoTicket;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el acceso a datos de ticket
 */
public interface TicketDao {

	void save(DocumentoSucursal documento);

	void updateEstado(DocumentoSucursal documento);

	Ticket readByStatus(Ticket ticket, Establecimiento establecimiento, TipoEstadoTicket estadoTicket);
	
	Ticket read(Ticket ticket, Establecimiento establecimiento);

	List<String> readArticulosSinPrecio();

	Integer readIdDocFromTicketFacturado(DocumentoSucursal documento);

	List<String> readAllByDate(String fecha);

	void saveTicketsCierreDia(Documento documento, TipoEstadoTicket estadoTicket);

	int readProcesado(String archivoOrigen, TipoEstadoTicket facturado,
			TipoEstadoTicket facturadoMostrador);

	Ticket readByDocumento(Documento documento);

	DocumentoSucursal readDocByStatus(String archivoOrigen,
			TipoEstadoTicket estadoTicket);

	Ticket findByDocumento(Documento documento);

}
