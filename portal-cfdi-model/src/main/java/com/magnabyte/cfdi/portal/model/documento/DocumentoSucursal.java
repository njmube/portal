package com.magnabyte.cfdi.portal.model.documento;

import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public class DocumentoSucursal extends Documento {
	private Ticket ticket;

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
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
