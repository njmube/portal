package com.magnabyte.cfdi.portal.model.establecimiento;

import java.io.Serializable;
import java.util.Date;

import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;

public class Establecimiento implements Serializable {

	private static final long serialVersionUID = 2674768576109909674L;
	
	private Integer id;
	private String clave;
	private String nombre;
	private String password;
	private String smbDomain;
	private String smbUsername;
	private String smbPassword;
	private Date siguienteCierre;
	private Date ultimoCierre;
	private EmpresaEmisor empresa;
	private RutaRepositorio rutaRepositorio;
	private TipoEstablecimiento tipoEstablecimiento;
	private DomicilioEstablecimiento domicilio;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSmbDomain() {
		return smbDomain;
	}

	public void setSmbDomain(String smbDomain) {
		this.smbDomain = smbDomain;
	}

	public String getSmbUsername() {
		return smbUsername;
	}

	public void setSmbUsername(String smbUsername) {
		this.smbUsername = smbUsername;
	}

	public String getSmbPassword() {
		return smbPassword;
	}

	public void setSmbPassword(String smbPassword) {
		this.smbPassword = smbPassword;
	}

	public TipoEstablecimiento getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}

	public void setTipoEstablecimiento(TipoEstablecimiento tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}

	public DomicilioEstablecimiento getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(DomicilioEstablecimiento domicilio) {
		this.domicilio = domicilio;
	}

	public EmpresaEmisor getEmpresaEmisor() {
		return empresa;
	}

	public void setEmpresaEmisor(EmpresaEmisor empresa) {
		this.empresa = empresa;
	}

	public RutaRepositorio getRutaRepositorio() {
		return rutaRepositorio;
	}

	public void setRutaRepositorio(RutaRepositorio rutaRepositorio) {
		this.rutaRepositorio = rutaRepositorio;
	}

	public Date getSiguienteCierre() {
		return siguienteCierre;
	}

	public void setSiguienteCierre(Date siguienteCierre) {
		this.siguienteCierre = siguienteCierre;
	}

	public Date getUltimoCierre() {
		return ultimoCierre;
	}

	public void setUltimoCierre(Date ultimoCierre) {
		this.ultimoCierre = ultimoCierre;
	}

	@Override
	public String toString() {
		return "Establecimiento [id=" + id + ", clave=" + clave + ", nombre="
				+ nombre + ", password=" + password + ", empresa=" + empresa
				+ ", rutaRepositorio=" + rutaRepositorio
				+ ", tipoEstablecimiento=" + tipoEstablecimiento
				+ ", domicilio=" + domicilio + "]";
	}

}
