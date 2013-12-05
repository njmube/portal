package com.magnabyte.cfdi.portal.model.transaccion.sucursal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArticuloXml {
	private Integer id;
	private String descripcion;

	public Integer getId() {
		return id;
	}

	@XmlElement(name = "szPOSItemID")
	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	@XmlElement(name = "szDesc")
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArticuloXml [id=");
		builder.append(id);
		builder.append(", descripcion=");
		builder.append(descripcion);
		builder.append("]");
		return builder.toString();
	}

}
