package com.magnabyte.cfdi.portal.model.establecimiento;

import java.io.Serializable;

import com.magnabyte.cfdi.portal.model.emisor.Empresa;

public class Establecimiento implements Serializable {

	private static final long serialVersionUID = 2674768576109909674L;
	
	private Integer id;
	private String clave;
	private String nombre;
	private String password;
	private Empresa empresa;
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

	public TipoEstablecimiento getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}

	public void setTipoEstablecimiento(TipoEstablecimiento tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}

	public Empresa getEmisor() {
		return empresa;
	}

	public void setEmisor(Empresa emisor) {
		this.empresa = emisor;
	}

	public DomicilioEstablecimiento getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(DomicilioEstablecimiento domicilio) {
		this.domicilio = domicilio;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public RutaRepositorio getRutaRepositorio() {
		return rutaRepositorio;
	}

	public void setRutaRepositorio(RutaRepositorio rutaRepositorio) {
		this.rutaRepositorio = rutaRepositorio;
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
