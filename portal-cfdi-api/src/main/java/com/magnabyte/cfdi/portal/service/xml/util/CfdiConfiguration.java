package com.magnabyte.cfdi.portal.service.xml.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 *
 * Clase que representa la configuracion de cfdi
 */
@Component
public class CfdiConfiguration {
	
	/**
	 * Versión cfdi
	 */
	@Value("${cfdi.version}")
	private String versionCfdi;
	
	/**
	 * Número certificado
	 */
	@Value("${cfdi.numero.certificado.previo}")
	private String numeroCertificadoPrevio;
	
	/**
	 * Sello certificado
	 */
	@Value("${cfdi.sello.previo}")
	private String selloPrevio;
	
	/**
	 * Certificado previo
	 */
	@Value("${cfdi.certificado.previo}")
	private String certificadoPrevio;
	
	/**
	 * Schema Location
	 */
	@Value("${cfdi.schema.location}")
	private String schemaLocation;

	/**
	 * Devuelve la versión de cfdi
	 * @return versionCfdi
	 */
	public String getVersionCfdi() {
		return versionCfdi;
	}

	/**
	 * Asigna la versión se cfdi
	 * @param versionCfdi
	 */
	public void setVersionCfdi(String versionCfdi) {
		this.versionCfdi = versionCfdi;
	}

	/**
	 * Devuelve el número de certificado previo
	 * @return numeroCertificadoPrevio
	 */ 
	public String getNumeroCertificadoPrevio() {
		return numeroCertificadoPrevio;
	}

	/**
	 * Asigna el número de certificado previo
	 * @param numeroCertificadoPrevio
	 */
	public void setNumeroCertificadoPrevio(String numeroCertificadoPrevio) {
		this.numeroCertificadoPrevio = numeroCertificadoPrevio;
	}

	/**
	 * Devuelve el sello previo
	 * @return selloPrevio
	 */
	public String getSelloPrevio() {
		return selloPrevio;
	}

	/**
	 * Asigna el sello previo
	 * @param selloPrevio
	 */
	public void setSelloPrevio(String selloPrevio) {
		this.selloPrevio = selloPrevio;
	}

	/**
	 * Devuelve el vertificado previo
	 * @return certificadoPrevio
	 */
	public String getCertificadoPrevio() {
		return certificadoPrevio;
	}

	/**
	 * Asigna el vertificado previo
	 * @param certificadoPrevio
	 */
	public void setCertificadoPrevio(String certificadoPrevio) {
		this.certificadoPrevio = certificadoPrevio;
	}

	/**
	 * Devuelve el schema location
	 * @return schemaLocation
	 */
	public String getSchemaLocation() {
		return schemaLocation;
	}

	/**
	 * Asigna el schema location
	 * @param schemaLocation
	 */
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}
	
}
