package com.magnabyte.cfdi.portal.service.documento;

import java.util.List;

import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.ListaTickets;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public interface TicketService {

	void save(DocumentoSucursal documento);
	
	boolean ticketExists(Ticket ticket, Establecimiento establecimiento);

//	String formatTicketClaveSucursal(Ticket ticket);

	void updateEstadoFacturado(DocumentoSucursal documento);

	Ticket read(Ticket ticket, Establecimiento establecimiento);

	boolean isTicketFacturado(Ticket ticket, Establecimiento establecimiento);
	
	boolean isTicketFacturado(String archivoOrigen);

	List<String> readArticulosSinPrecio();

	void closeOfDay(Establecimiento establecimiento, String fechaCierre, List<Ticket> ventas, List<Ticket> devoluciones);

	Ticket crearTicketVentasMostrador(List<Ticket> ventas,
			Establecimiento establecimiento);

	Integer readIdDocFromTicketFacturado(DocumentoSucursal documento);

	void guardarTicketsCierreDia(Documento documento);

	boolean isTicketFacturadoMostrador(String archivoOrigen);

	void updateEstadoNcr(DocumentoSucursal documento);
	
	String recibeTicketsWsdl(ListaTickets tickets);
	
	String fechaCierre(String noEstablecimiento);

	Ticket readByDocumento(Documento documento);

}
