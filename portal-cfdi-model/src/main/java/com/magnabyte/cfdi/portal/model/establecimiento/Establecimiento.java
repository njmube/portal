package com.magnabyte.cfdi.portal.model.establecimiento;

import java.io.Serializable;
import java.util.Date;

import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa un establecimiento
 */
public class Establecimiento implements Serializable {

	private static final long serialVersionUID = 2674768576109909674L;
	
	/**
	 * Identificador único de establecimiento
	 */
	private Integer id;
	
	/**
	 * Clave de establecimiento
	 */
	private String clave;
	
	/**
	 * Nombre de establecimiento
	 */
	private String nombre;
	
	/**
	 * Contraseña de establecimiento
	 */
	private String password;
	
	/**
	 * Dominio de samba
	 */
	private String smbDomain;
	
	/**
	 * Usuario de samba
	 */
	private String smbUsername;
	
	/**
	 * Contraseña de samba
	 */
	private String smbPassword;
	
	/**
	 * Fecha de cierre siguiente
	 */
	private Date siguienteCierre;
	
	/**
	 * Facha de último cierre
	 */
	private Date ultimoCierre;
	
	/**
	 * Empresa de establecimiento
	 */
	private EmpresaEmisor empresa;
	
	/**
	 * Ruta de repositorio de establecimiento
	 */
	private RutaRepositorio rutaRepositorio;
	
	/**
	 * Tipo de establecimiento
	 */
	private TipoEstablecimiento tipoEstablecimiento;
	
	/**
	 * Domicilio de establecimiento
	 */
	private DomicilioEstablecimiento domicilio;

	/**
	 * Devuelve el identificador único de
	 * establecimiento
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Asigna el identificadir único de 
	 * establecimiento
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Devuelve la clave de establecimiento
	 * @return clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * Asigna la clave de establecimiento
	 * @param clave
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * Devuleve el nombre de establecimiento
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el nombre de establecimeinto
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la contraseña de establecimiento
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Asigna la contraseña de establecimiento
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Devuleve el dominio de samba de
	 * establecimiento
	 * @return smbDomain
	 */
	public String getSmbDomain() {
		return smbDomain;
	}

	/**
	 * Asigna el domino de samba de establecimiento
	 * @param smbDomain
	 */
	public void setSmbDomain(String smbDomain) {
		this.smbDomain = smbDomain;
	}

	/**
	 * Devuelve el usuario de samba de
	 * establecimiento
	 * @return smbUsername
	 */
	public String getSmbUsername() {
		return smbUsername;
	}

	/**
	 * Asigna el usuario de samba de establecimiento
	 * @param smbUsername
	 */
	public void setSmbUsername(String smbUsername) {
		this.smbUsername = smbUsername;
	}

	/**
	 * Devuelve el password de samba de
	 * establecimiento
	 * @return smbPassword
	 */
	public String getSmbPassword() {
		return smbPassword;
	}

	/**
	 * Asigna el password de samba de establecimiento
	 * @param smbPassword
	 */
	public void setSmbPassword(String smbPassword) {
		this.smbPassword = smbPassword;
	}

	/**
	 * Devuelve el tipo de establecimiento
	 * @return tipoEstablecimiento {@link TipoEstablecimiento}
	 */
	public TipoEstablecimiento getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}

	/**
	 * Asigna el tipo de establecimiento
	 * @param tipoEstablecimiento {@link TipoEstablecimiento}
	 */
	public void setTipoEstablecimiento(TipoEstablecimiento tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}

	/**
	 * Devuleve el domicilio de establecimiento
	 * @return domicilio {@link DomicilioEstablecimiento}
	 */
	public DomicilioEstablecimiento getDomicilio() {
		return domicilio;
	}

	/**
	 * Asigna el domicilio de establecimiento
	 * @param domicilio {@link DomicilioEstablecimiento}
	 */
	public void setDomicilio(DomicilioEstablecimiento domicilio) {
		this.domicilio = domicilio;
	}

	/**
	 * Devuelve la empresa emisor de establecimiento
	 * @return empreda {@link EmpresaEmisor}
	 */
	public EmpresaEmisor getEmpresaEmisor() {
		return empresa;
	}

	/**
	 * Asigna la empresa emisor de establecimiento
	 * @param empresa {@link EmpresaEmisor}
	 */
	public void setEmpresaEmisor(EmpresaEmisor empresa) {
		this.empresa = empresa;
	}

	/**
	 * Devuelve la ruta repositorio de establecimiento
	 * @return rutaRepositorio {@link RutaRepositorio}
	 */
	public RutaRepositorio getRutaRepositorio() {
		return rutaRepositorio;
	}

	/**
	 * Asigna la ruta repositorio al establecimiento
	 * @param rutaRepositorio {@link RutaRepositorio}
	 */
	public void setRutaRepositorio(RutaRepositorio rutaRepositorio) {
		this.rutaRepositorio = rutaRepositorio;
	}

	/**
	 * Devuleve la fecha siguiente de cierre de
	 * establecimiento
	 * @return siguienteCierre
	 */
	public Date getSiguienteCierre() {
		return siguienteCierre;
	}

	/**
	 * Asigna la fecha siguiente de cierre de
	 * establecimeinto
	 * @param siguienteCierre
	 */
	public void setSiguienteCierre(Date siguienteCierre) {
		this.siguienteCierre = siguienteCierre;
	}

	/**
	 * Devuelve la fecha de último cierre de
	 * establecimiento
	 * @return ultimoCierre
	 */
	public Date getUltimoCierre() {
		return ultimoCierre;
	}

	/**
	 * Asigna la fecha de último cierre de
	 * establecimiento
	 * @param ultimoCierre
	 */
	public void setUltimoCierre(Date ultimoCierre) {
		this.ultimoCierre = ultimoCierre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Establecimiento other = (Establecimiento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
