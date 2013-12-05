package com.magnabyte.cfdi.portal.model.transaccion.sucursal;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransaccionXml {
	private TransaccionHeaderXml header;
	private List<PartidaXml> partidas;

	public TransaccionHeaderXml getHeader() {
		return header;
	}

	@XmlElement(name = "HEADER", type = TransaccionHeaderXml.class)
	public void setHeader(TransaccionHeaderXml header) {
		this.header = header;
	}

	public List<PartidaXml> getPartidas() {
		return partidas;
	}

	@XmlElement(name = "ART_SALE", type = PartidaXml.class)
	public void setPartidas(List<PartidaXml> partidas) {
		this.partidas = partidas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransaccionXml [header=");
		builder.append(header);
		builder.append(", partidas=");
		builder.append(partidas);
		builder.append("]");
		return builder.toString();
	}

}
