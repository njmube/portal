package com.magnabyte.cfdi.portal.model.transaccion.sucursal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TAS")
public class TicketXml {
	private TransaccionXml transaccion;
	
	public TicketXml() {
	}
	
	public TicketXml(TransaccionXml transaccion) {
		super();
		this.transaccion = transaccion;
	}

	public TransaccionXml getTransaccion() {
		return transaccion;
	}
	
	@XmlElement(name = "NEW_TA", type = TransaccionXml.class)
	public void setTransaccion(TransaccionXml transaccion) {
		this.transaccion = transaccion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ticket [transaccion=");
		builder.append(transaccion);
		builder.append("]");
		return builder.toString();
	}

	
}
