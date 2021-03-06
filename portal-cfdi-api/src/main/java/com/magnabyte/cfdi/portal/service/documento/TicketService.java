package com.magnabyte.cfdi.portal.service.documento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.ListaTickets;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 *
 * Interfáz que representa el servicio ticket
 */
public interface TicketService {

	void save(DocumentoSucursal documento);
	
	boolean ticketExists(Ticket ticket, Establecimiento establecimiento);

//	String formatTicketClaveSucursal(Ticket ticket);

	void updateEstadoFacturado(DocumentoSucursal documento);

	Ticket read(Ticket ticket, Establecimiento establecimiento);

	boolean isTicketFacturado(Ticket ticket, Establecimiento establecimiento);
	
	List<String> readArticulosSinPrecio();

	void closeOfDay(Establecimiento establecimiento, String fechaCierre, List<Ticket> ventas, List<Ticket> devoluciones);

	Ticket crearTicketVentasMostrador(List<Ticket> ventas,
			Establecimiento establecimiento);

	Integer readIdDocFromTicketFacturado(DocumentoSucursal documento);

	void guardarTicketsCierreDia(Documento documento);

	void updateEstadoNcr(DocumentoSucursal documento);
	
	String recibeTicketsWsdl(ListaTickets tickets);
	
	String fechaCierre(String noEstablecimiento);

	Ticket readByDocumento(Documento documento);

	DocumentoSucursal readDocFromTicket(String archivoOrigen);

	Ticket crearTicketDevolucion(DocumentoSucursal documentoOrigen,
			List<Ticket> devoluciones, Establecimiento establecimiento);

	Ticket findByDocumento(Documento documento);

	void updateEstadoRefacturado(DocumentoSucursal documento);

	void validarFechaFacturacion(Ticket ticket);	

}
