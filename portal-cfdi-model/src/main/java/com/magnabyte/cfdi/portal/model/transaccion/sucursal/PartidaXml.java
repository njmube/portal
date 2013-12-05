package com.magnabyte.cfdi.portal.model.transaccion.sucursal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PartidaXml {
	private ArticuloXml articulo;
	private Double precio;
	private Integer cantidad;
	private Double total;

	public ArticuloXml getArticulo() {
		return articulo;
	}

	@XmlElement(name = "ARTICLE", type = ArticuloXml.class)
	public void setArticulo(ArticuloXml articulo) {
		this.articulo = articulo;
	}

	public Double getPrecio() {
		return precio;
	}

	@XmlElement(name = "dTaPrice")
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	@XmlElement(name = "dTaQty")
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getTotal() {
		return total;
	}

	@XmlElement(name = "dTaTotal")
	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PartidaXml [articulo=");
		builder.append(articulo);
		builder.append(", precio=");
		builder.append(precio);
		builder.append(", cantidad=");
		builder.append(cantidad);
		builder.append(", total=");
		builder.append(total);
		builder.append("]");
		return builder.toString();
	}

}
