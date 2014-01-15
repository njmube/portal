package com.magnabyte.cfdi.portal.model.documento;

import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public class DocumentoSucursal extends Documento {
	
	private static final long serialVersionUID = 3038505761611083436L;
	
	private Ticket ticket;
	private boolean requiereNotaCredito;

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	public boolean isRequiereNotaCredito() {
		return requiereNotaCredito;
	}

	public void setRequiereNotaCredito(boolean requiereNotaCredito) {
		this.requiereNotaCredito = requiereNotaCredito;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DocumentoSucursal [ticket=");
		builder.append(ticket);
		builder.append("]");
		return builder.toString();
	}

}
