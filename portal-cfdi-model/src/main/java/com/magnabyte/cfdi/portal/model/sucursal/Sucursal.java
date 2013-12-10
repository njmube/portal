package com.magnabyte.cfdi.portal.model.sucursal;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class Sucursal extends User implements Serializable {
	
	private Integer id;
	private String rutaRepositorio;

	public Sucursal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRutaRepositorio() {
		return rutaRepositorio;
	}

	public void setRutaRepositorio(String rutaRepositorio) {
		this.rutaRepositorio = rutaRepositorio;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
