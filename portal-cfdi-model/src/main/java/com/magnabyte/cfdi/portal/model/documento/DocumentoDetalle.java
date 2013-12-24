package com.magnabyte.cfdi.portal.model.documento;

import java.io.Serializable;
import java.math.BigDecimal;

public class DocumentoDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2448120078306911621L;

	private Integer id;
	private Documento documento;
	private Double cantidad;
	private String unidad;
	private String descripcion;
	private BigDecimal precioUnitario;
	private BigDecimal precioTotal;

	/**
	 * Constructor por default
	 */
	public DocumentoDetalle() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

}
