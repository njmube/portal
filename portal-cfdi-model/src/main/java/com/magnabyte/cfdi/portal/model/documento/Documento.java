package com.magnabyte.cfdi.portal.model.documento;

import java.io.Serializable;

import mx.gob.sat.cfd._3.Comprobante;

public class Documento implements Serializable {
	private Comprobante comprobante;
	private String cadenaOriginal;

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

}
