package com.magnabyte.cfdi.portal.service.establecimiento.impl;

import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.establecimiento.AutorizacionCierreService;

/**
 * Clase que representa el servicio para autorización de cierre del día
 * 
 * @author Edgar Pérez
 *
 */
@Service("autCierreService")
public class AutorizacionCierreServiceImpl implements AutorizacionCierreService {

	public static final String USUARIODOMMY = "zama";
	public static final String PASSWORDDOMMY = "veloci";
	
	@Override
	public boolean autorizar(String usuario, String password) {
		if(usuario.equals(USUARIODOMMY) && !password.equals(PASSWORDDOMMY)) {
			throw new PortalException("El password proporcionado es incorrecto.");
		} else if (!usuario.equals(USUARIODOMMY) && password.equals(PASSWORDDOMMY)) {
			throw new PortalException("El usuario proporcionado es incorrecto.");			
		} else if (!usuario.equals(USUARIODOMMY) && !password.equals(PASSWORDDOMMY)) {
			throw new PortalException("El usuario y password proporcionado son incorrectos.");
		} else {
			return true;
		}
	}

}
