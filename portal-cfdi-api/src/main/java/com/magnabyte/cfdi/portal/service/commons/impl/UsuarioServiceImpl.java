package com.magnabyte.cfdi.portal.service.commons.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.commons.UsuarioDao;
import com.magnabyte.cfdi.portal.model.commons.Usuario;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.commons.UsuarioService;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioDao usuarioDao;
	
	@Transactional(readOnly = true)
	@Override
	public Usuario getUsuarioByEstablecimiento(Usuario usuario) {
		return usuarioDao.getUsuarioByEstablecimiento(usuario);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> getAllUsuarios() {
		return usuarioDao.getAllUsuarios();
	}
	
	@Transactional
	@Override
	public void update(Usuario usuario) {
		if(usuario != null) {
			if(usuario.getId() != null) {
				usuarioDao.update(usuario);		
			} else {
				throw new PortalException("El id de usuario no puede ser nulo");
			}
		} else {
			throw new PortalException("El usuario no puede ser nulo");
		}
	}

}
