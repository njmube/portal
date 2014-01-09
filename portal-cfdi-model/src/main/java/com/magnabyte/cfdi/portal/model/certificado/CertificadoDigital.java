package com.magnabyte.cfdi.portal.model.certificado;

import java.io.Serializable;
import java.util.Date;

public class CertificadoDigital implements Serializable {
	private Integer id;
	private String numeroCertificado;
	private String rutaKey;
	private String nombreKey;
	private String rutaCertificado;
	private String nombreCertificado;
	private String password;
	private Date inicioVigencia;
	private Date finVigencia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroCertificado() {
		return numeroCertificado;
	}

	public void setNumeroCertificado(String numeroCertificado) {
		this.numeroCertificado = numeroCertificado;
	}

	public String getRutaKey() {
		return rutaKey;
	}

	public void setRutaKey(String rutaKey) {
		this.rutaKey = rutaKey;
	}

	public String getNombreKey() {
		return nombreKey;
	}

	public void setNombreKey(String nombreKey) {
		this.nombreKey = nombreKey;
	}

	public String getRutaCertificado() {
		return rutaCertificado;
	}

	public void setRutaCertificado(String rutaCertificado) {
		this.rutaCertificado = rutaCertificado;
	}

	public String getNombreCertificado() {
		return nombreCertificado;
	}

	public void setNombreCertificado(String nombreCertificado) {
		this.nombreCertificado = nombreCertificado;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Date inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public Date getFinVigencia() {
		return finVigencia;
	}

	public void setFinVigencia(Date finVigencia) {
		this.finVigencia = finVigencia;
	}

}
