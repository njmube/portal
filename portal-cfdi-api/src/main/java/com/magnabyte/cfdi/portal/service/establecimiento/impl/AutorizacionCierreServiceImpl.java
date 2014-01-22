package com.magnabyte.cfdi.portal.service.establecimiento.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.model.commons.Usuario;
import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusUsuario;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.commons.UsuarioService;
import com.magnabyte.cfdi.portal.service.establecimiento.AutorizacionCierreService;

/**
 * Clase que representa el servicio para autorización de cierre del día
 * 
 * @author Edgar Pérez
 *
 */
@Service("autCierreService")
public class AutorizacionCierreServiceImpl implements AutorizacionCierreService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Transactional(readOnly = true)
	@Override
	public boolean autorizar(Usuario usuario) {
		
		Usuario usrBd = usuarioService.getUsuarioByEstablecimiento(usuario);
		
		if (usrBd != null) {
			if(usrBd.getEstatus().getId() != EstatusUsuario.INACTIVO.getId()) {
				if(usuario.getUsuario().equals(usrBd.getUsuario()) && 
						!usuario.getPassword().equals(usrBd.getPassword())) {
					throw new PortalException("El password proporcionado es incorrecto.");
				} else if (!usuario.getUsuario().equals(usrBd.getUsuario()) && 
						usuario.getPassword().equals(usrBd.getPassword())) {
					throw new PortalException("El usuario proporcionado es incorrecto.");			
				} else if (!usuario.getUsuario().equals(usrBd.getUsuario()) &&
						!usuario.getPassword().equals(usrBd.getPassword())) {
					throw new PortalException("El usuario y password proporcionado son incorrectos.");
				} else {
					return true;
					}
				} else {
				throw new PortalException("No se pudo autorizar el cierre,"
						+ " el usuario esta inactivo.");
			}
		} else {
			throw new PortalException("No existe el usuario en la sucursal");
		}
	}

}
