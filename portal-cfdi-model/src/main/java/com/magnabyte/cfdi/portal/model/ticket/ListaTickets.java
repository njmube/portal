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

	private List<Ticket> tickets;
	
	public ListaTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public ListaTickets() {
		
	}

	@XmlElement(name = "ticket")
	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	
}
