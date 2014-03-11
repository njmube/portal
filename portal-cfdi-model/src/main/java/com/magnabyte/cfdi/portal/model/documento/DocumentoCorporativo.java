package com.magnabyte.cfdi.portal.model.documento;

public class DocumentoCorporativo extends Documento {

	private static final long serialVersionUID = -6239507110898792522L;

	private String folioSap;
	private String nombreXmlPrevio;
	private String nit;

	public String getFolioSap() {
		return folioSap;
	}

	public void setFolioSap(String folioSap) {
		this.folioSap = folioSap;
	}

	public String getNombreXmlPrevio() {
		return nombreXmlPrevio;
	}

	public void setNombreXmlPrevio(String nombreXmlPrevio) {
		this.nombreXmlPrevio = nombreXmlPrevio;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DocumentoCorporativo [folioSap=");
		builder.append(folioSap);
		builder.append(", nombreXmlPrevio=");
		builder.append(nombreXmlPrevio);
		builder.append(", getComprobante()=");
		builder.append(getComprobante());
		builder.append(", getTimbreFiscalDigital()=");
		builder.append(getTimbreFiscalDigital());
		builder.append(", getCadenaOriginal()=");
		builder.append(getCadenaOriginal());
		builder.append("]");
		return builder.toString();
	}

}
