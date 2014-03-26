package com.magnabyte.cfdi.portal.service.establecimiento.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.model.commons.Usuario;
import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusGenerico;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.commons.UsuarioService;
import com.magnabyte.cfdi.portal.service.establecimiento.AutorizacionCierreService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el servicio para autorización de cierre del día
 */
@Service("autCierreService")
public class AutorizacionCierreServiceImpl implements AutorizacionCierreService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Transactional(readOnly = true)
	@Override
	public boolean autorizar(Usuario usuario) {
		
		Usuario usrBd = usuarioService.getUsuarioByEstablecimiento(usuario);
		
		if (usrBd != null) {
			if(usrBd.getEstatus().getId() != EstatusGenerico.INACTIVO.getId()) {
				if(usuario.getUsuario().equals(usrBd.getUsuario()) && 
						!usuario.getPassword().equals(usrBd.getPassword())) {
					throw new PortalException(messageSource.getMessage("cierre.error.password", null, null));
				} else if (!usuario.getUsuario().equals(usrBd.getUsuario()) && 
						usuario.getPassword().equals(usrBd.getPassword())) {
					throw new PortalException(messageSource.getMessage("cierre.error.usuario", null, null));			
				} else if (!usuario.getUsuario().equals(usrBd.getUsuario()) &&
						!usuario.getPassword().equals(usrBd.getPassword())) {
					throw new PortalException(messageSource.getMessage("cierre.error.usuario.password", null, null));
				} else {
					return true;
					}
				} else {
				throw new PortalException(messageSource.getMessage("cierre.error.usuario.inactivo", null, null));
			}
		} else {
			throw new PortalException(messageSource.getMessage("cierre.error.usuario.inexistente", null, null));
		}
	}

}
