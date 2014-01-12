package com.magnabyte.cfdi.portal.model.documento;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import mx.gob.sat.cfd._3.Comprobante;
import mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public class Documento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 383916791240840326L;

	private Integer id;
	private Comprobante comprobante;
	private Cliente cliente;
	private String cadenaOriginal;
	private Date fechaFacturacion;
	private Establecimiento establecimiento;
	private TimbreFiscalDigital timbreFiscalDigital;
	private TipoDocumento tipoDocumento;
	private String nombre;
	private List<Ticket> ventas;
	private byte[] xmlCfdi;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public TimbreFiscalDigital getTimbreFiscalDigital() {
		return timbreFiscalDigital;
	}

	public void setTimbreFiscalDigital(TimbreFiscalDigital timbreFiscalDigital) {
		this.timbreFiscalDigital = timbreFiscalDigital;
	}

	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFechaFacturacion() {
		return fechaFacturacion;
	}

	public void setFechaFacturacion(Date fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}

	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}
	
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	public List<Ticket> getVentas() {
		return ventas;
	}
	
	public void setVentas(List<Ticket> ventas) {
		this.ventas = ventas;
	}
	
	public byte[] getXmlCfdi() {
		return xmlCfdi;
	}
	
	public void setXmlCfdi(byte[] xmlCfdi) {
		this.xmlCfdi = xmlCfdi;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Documento [id=");
		builder.append(id);
		builder.append(", comprobante=");
		builder.append(comprobante);
		builder.append(", cliente=");
		builder.append(cliente);
		builder.append(", cadenaOriginal=");
		builder.append(cadenaOriginal);
		builder.append(", fechaFacturacion=");
		builder.append(fechaFacturacion);
		builder.append(", establecimiento=");
		builder.append(establecimiento);
		builder.append(", timbreFiscalDigital=");
		builder.append(timbreFiscalDigital);
		builder.append(", tipoDocumento=");
		builder.append(tipoDocumento);
		builder.append("]");
		return builder.toString();
	}

}
