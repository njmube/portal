package com.magnabyte.cfdi.portal.model.documento;

public class DocumentoCorporativo extends Documento {
	private String folioSap;
	private String rutaXmlPrevio;
	private String nombreXmlPrevio;

	public String getFolioSap() {
		return folioSap;
	}

	public void setFolioSap(String folioSap) {
		this.folioSap = folioSap;
	}

	public String getRutaXmlPrevio() {
		return rutaXmlPrevio;
	}
	
	public void setRutaXmlPrevio(String rutaXmlPrevio) {
		this.rutaXmlPrevio = rutaXmlPrevio;
	}
	
	public String getNombreXmlPrevio() {
		return nombreXmlPrevio;
	}

	public void setNombreXmlPrevio(String nombreXmlPrevio) {
		this.nombreXmlPrevio = nombreXmlPrevio;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DocumentoCorporativo [folioSap=");
		builder.append(folioSap);
		builder.append(", rutaXmlPrevio=");
		builder.append(rutaXmlPrevio);
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
