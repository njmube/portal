package com.magnabyte.cfdi.portal.model.documento;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el detalle de documento
 */
public class DocumentoDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2448120078306911621L;

	/**
	 * Itenditicador único de detalle de documento
	 */
	private Integer id;
	
	/**
	 * Documento al que pertenece el detalle
	 */
	private Documento documento;
	
	/**
	 * Cantidad de productos
	 */
	private Double cantidad;
	
	/**
	 * Unidad de medida del producto
	 */
	private String unidad;
	
	/**
	 * Descripcion del producto
	 */
	private String descripcion;
	
	/**
	 * Precio unitario del producto
	 */
	private BigDecimal precioUnitario;
	
	/**
	 * Precio total de detalle
	 */
	private BigDecimal precioTotal;

	/**
	 * Constructor por default
	 */
	public DocumentoDetalle() {
	}

	/**
	 * Devuelve el identificador único del detalle
	 * de documento
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Asigna el identificador único al detalle de
	 * documento
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Devuelve el documento de detalle de
	 * documento
	 * @return documento {@link Documento}
	 */
	public Documento getDocumento() {
		return documento;
	}

	/**
	 * Asigna el documento al detall de
	 * documento
	 * @param documento {@link Documento}
	 */
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	/**
	 * Devuelve la cantidad de productos de
	 * detalle de documento
	 * @return cantidad
	 */
	public Double getCantidad() {
		return cantidad;
	}

	/**
	 * Asigna la cantidad de productos de
	 * detalle de documento
	 * @param cantidad
	 */
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Devuelve la unidad de medida del producto de
	 * detalle de documento
	 * @return unidad
	 */
	public String getUnidad() {
		return unidad;
	}

	/**
	 * Asigna la unidad de medida de detalle
	 * de documento
	 * @param unidad
	 */
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	/**
	 * Devuelve la descripción de producto de
	 * detalle de documento
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asigna la descripción del producto de
	 * detalle de documento 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve el precio unitario de producto
	 * de detalle de documento
	 * @return precioUnitario
	 */
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	/**
	 * Asigna el precio unitario al producto de
	 * detalle de documento
	 * @param precioUnitario
	 */
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	/**
	 * Devuelve el procio total de detalle de documento
	 * @return precioTotal
	 */
	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	/**
	 * Asigna el precio total de detalle de
	 * documento
	 * @param precioTotal
	 */
	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

}
