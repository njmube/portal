package com.magnabyte.cfdi.portal.service.xml.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CfdiConfiguration {
	
	@Value("${cfdi.version}")
	private String versionCfdi;
	
	@Value("${cfdi.numero.certificado.previo}")
	private String numeroCertificadoPrevio;
	
	@Value("${cfdi.sello.previo}")
	private String selloPrevio;
	
	@Value("${cfdi.certificado.previo}")
	private String certificadoPrevio;
	
	@Value("${cfdi.schema.location}")
	private String schemaLocation;

	public String getVersionCfdi() {
		return versionCfdi;
	}

	public void setVersionCfdi(String versionCfdi) {
		this.versionCfdi = versionCfdi;
	}

	public String getNumeroCertificadoPrevio() {
		return numeroCertificadoPrevio;
	}

	public void setNumeroCertificadoPrevio(String numeroCertificadoPrevio) {
		this.numeroCertificadoPrevio = numeroCertificadoPrevio;
	}

	public String getSelloPrevio() {
		return selloPrevio;
	}

	public void setSelloPrevio(String selloPrevio) {
		this.selloPrevio = selloPrevio;
	}

	public String getCertificadoPrevio() {
		return certificadoPrevio;
	}

	public void setCertificadoPrevio(String certificadoPrevio) {
		this.certificadoPrevio = certificadoPrevio;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}
	
}
