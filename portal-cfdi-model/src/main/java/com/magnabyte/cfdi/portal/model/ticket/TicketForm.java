package com.magnabyte.cfdi.portal.model.ticket;

import java.io.Serializable;
import java.math.BigDecimal;

public class TicketForm implements Serializable {
	private String noTicket;
	private String noCaja;
	private String fecha;
	private BigDecimal importe;
	private Ticket ticket;

	public String getNoTicket() {
		return noTicket;
	}

	public void setNoTicket(String noTicket) {
		this.noTicket = noTicket;
	}

	public String getNoCaja() {
		return noCaja;
	}

	public void setNoCaja(String noCaja) {
		this.noCaja = noCaja;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	
	public Ticket getTicket() {
		return ticket;
	}
	
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TicketForm [noTicket=");
		builder.append(noTicket);
		builder.append(", noCaja=");
		builder.append(noCaja);
		builder.append(", fecha=");
		builder.append(fecha);
		builder.append(", importe=");
		builder.append(importe);
		builder.append("]");
		return builder.toString();
	}

}
