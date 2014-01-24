package com.magnabyte.cfdi.portal.model.ticket;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ticketListType")
@XmlRootElement(name = "ticketList")
public class ListaTickets {

	private String claveEstablecimiento;
	
	private List<Ticket> ventas;
	
	private List<Ticket> devoluciones;
	
	public ListaTickets() {
		
	}

	@XmlElement(name = "ventas")
	public List<Ticket> getVentas() {
		return ventas;
	}

	public void setVentas(List<Ticket> ventas) {
		this.ventas = ventas;
	}

	@XmlElement(name = "devoluciones")
	public List<Ticket> getDevoluciones() {
		return devoluciones;
	}

	public void setDevoluciones(List<Ticket> devoluciones) {
		this.devoluciones = devoluciones;
	}

	@XmlElement(name = "establecimiento")
	public String getClaveEstablecimiento() {
		return claveEstablecimiento;
	}

	public void setClaveEstablecimiento(String claveEstablecimiento) {
		this.claveEstablecimiento = claveEstablecimiento;
	}
	
	
}
