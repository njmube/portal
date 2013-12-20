package com.magnabyte.cfdi.portal.model.establecimiento;

import java.io.Serializable;

public class Establecimiento implements Serializable {

	private static final long serialVersionUID = 2674768576109909674L;
	
	private Integer id;
	private String clave;
	private String nombre;
	private String password;
	private String rutaRepositorio;
	private TipoEstablecimiento tipoEstablecimiento;

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

	public String getRutaRepositorio() {
		return rutaRepositorio;
	}

	public void setRutaRepositorio(String rutaRepositorio) {
		this.rutaRepositorio = rutaRepositorio;
	}

	public TipoEstablecimiento getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}

	public void setTipoEstablecimiento(TipoEstablecimiento tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}

	@Override
	public String toString() {
		return "Establecimiento [id=" + id + ", clave=" + clave + ", nombre="
				+ nombre + ", password=" + password + ", rutaRepositorio="
				+ rutaRepositorio + ", tipoEstablecimiento="
				+ tipoEstablecimiento + "]";
	}
	
	

}
