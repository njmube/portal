package com.magnabyte.cfdi.portal.model.ticket;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa una lista de tickets
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ticketListType")
@XmlRootElement(name = "ticketList")
public class ListaTickets {

	/**
	 * Clave de establecimiento
	 */
	private String claveEstablecimiento;
	
	/**
	 * Lista de tickets de ventas
	 */
	private List<Ticket> ventas;
	
	/**
	 * Lista de tickets de devoluciones
	 */
	private List<Ticket> devoluciones;
	
	/**
	 * Constructor por default
	 */
	public ListaTickets() {		
	}

	/**
	 * Devuleve una lista de tickets de ventas
	 * @return ventas List<{@link Ticket}>
	 */
	@XmlElement(name = "ventas")
	public List<Ticket> getVentas() {
		return ventas;
	}

	/**
	 * Asigna una lista de tickets de ventas
	 * @param ventas List<{@link Ticket}>
	 */
	public void setVentas(List<Ticket> ventas) {
		this.ventas = ventas;
	}

	/**
	 * Devuelve una lista de tickets de devoluciones
	 * @return devoluciones List<{@link Ticket}>
	 */
	@XmlElement(name = "devoluciones")
	public List<Ticket> getDevoluciones() {
		return devoluciones;
	}

	/**
	 * Asigna una lista de tickets de devoluciones
	 * @param devoluciones List<{@link Ticket}>
	 */
	public void setDevoluciones(List<Ticket> devoluciones) {
		this.devoluciones = devoluciones;
	}

	/**
	 * Devuleve la clave de establecimiento
	 * @return clave
	 */
	@XmlElement(name = "establecimiento")
	public String getClaveEstablecimiento() {
		return claveEstablecimiento;
	}

	/**
	 * Asigna la clave de establecimiento
	 * @param claveEstablecimiento
	 */
	public void setClaveEstablecimiento(String claveEstablecimiento) {
		this.claveEstablecimiento = claveEstablecimiento;
	}
	
	
}
