package com.magnabyte.cfdi.portal.model.transaccion.sucursal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransaccionHeaderXml {
	private Integer idSucursal;
	private Integer idTicket;
	private Integer idCaja;

	public Integer getIdSucursal() {
		return idSucursal;
	}

	@XmlElement(name = "lRetailStoreID")
	public void setIdSucursal(Integer idSucursal) {
		this.idSucursal = idSucursal;
	}

	public Integer getIdTicket() {
		return idTicket;
	}

	@XmlElement(name = "lTaNmbr")
	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}

	public Integer getIdCaja() {
		return idCaja;
	}

	@XmlElement(name = "lWorkstationNmbr")
	public void setIdCaja(Integer idCaja) {
		this.idCaja = idCaja;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransaccionHeaderXml [idSucursal=");
		builder.append(idSucursal);
		builder.append(", idTicket=");
		builder.append(idTicket);
		builder.append(", idCaja=");
		builder.append(idCaja);
		builder.append("]");
		return builder.toString();
	}

}
