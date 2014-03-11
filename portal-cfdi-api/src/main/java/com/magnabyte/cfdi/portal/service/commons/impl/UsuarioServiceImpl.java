package com.magnabyte.cfdi.portal.service.commons.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.commons.UsuarioDao;
import com.magnabyte.cfdi.portal.model.commons.Usuario;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.commons.UsuarioService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 *
 * Clase que representa el servicio de usuario
 */
@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private MessageSource messageSource;
	
	@Transactional(readOnly = true)
	@Override
	public Usuario getUsuarioByEstablecimiento(Usuario usuario) {
		return usuarioDao.getUsuarioByEstablecimiento(usuario);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Usuario read(Usuario usuario) {
		return usuarioDao.read(usuario);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> getAllUsuarios() {
		return usuarioDao.getAllUsuarios();
	}
	
	@Transactional
	@Override
	public void save(Usuario usuario) {
		if(usuario != null) {
			if (!exist(usuario)) {
				usuarioDao.save(usuario);		
			} else {
				throw new PortalException(messageSource.getMessage("usuario.nombre.existente", null, null));
			}
		 } else {
			throw new PortalException(messageSource.getMessage("usuario.nulo", null, null));
		 }
	}
	
	@Transactional
	@Override
	public void update(Usuario usuario) {
		if(usuario != null) {
			if(usuario.getId() != null)  {
				if (!exist(usuario)) {
					usuarioDao.update(usuario);		
				} else {
					throw new PortalException(messageSource.getMessage("usuario.sucursal.existente", null, null));
				}
			} else {
				throw new PortalException(messageSource.getMessage("usuario.id.nulo", null, null));
			}
		} else {
			throw new PortalException(messageSource.getMessage("usuario.nulo", null, null));
		}
	}
	
	private boolean exist(Usuario usuario) {
		Usuario usuarioDB = usuarioDao.getUsuarioByEstablecimiento(usuario);
		
		if (usuario.getId() != null) {
			if (usuarioDB != null) {
				if (usuario.getId() == usuarioDB.getId()){
					if (!usuario.getPassword().equals(usuarioDB.getPassword()) || 
							usuario.getEstatus().getId() != usuarioDB.getEstatus().getId()) {
						return false;
					}
				}
				return true;
			}
		} else {
			if (usuarioDB != null) {
				return true;
			}
		}
	
		return false;
	
	}

}
