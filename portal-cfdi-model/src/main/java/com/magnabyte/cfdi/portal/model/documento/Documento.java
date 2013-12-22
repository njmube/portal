package com.magnabyte.cfdi.portal.model.documento;

import java.io.Serializable;

import mx.gob.sat.cfd._3.Comprobante;
import mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital;

public abstract class Documento implements Serializable {
	private Comprobante comprobante;
	private TimbreFiscalDigital timbreFiscalDigital;
	private String cadenaOriginal;

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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Documento [comprobante=");
		builder.append(comprobante);
		builder.append(", timbreFiscalDigital=");
		builder.append(timbreFiscalDigital);
		builder.append(", cadenaOriginal=");
		builder.append(cadenaOriginal);
		builder.append("]");
		return builder.toString();
	}

}
