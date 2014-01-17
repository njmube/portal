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
				throw new PortalException("Ya existe un usuario con este "
						+ "nombre en la sucursal.");
			}
		 } else {
			throw new PortalException("El usuario no puede ser nulo");
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
					throw new PortalException("Ya existe el usuario en la sucursal.");
				}
			} else {
				throw new PortalException("El id de usuario no puede ser nulo");
			}
		} else {
			throw new PortalException("El usuario no puede ser nulo");
		}
	}
	
	public boolean exist(Usuario usuario) {
		Usuario usu = usuarioDao.getUsuarioByEstablecimiento(usuario);
		
		if(usuario.getId() != null) {
			if(usu != null) {
//				if(!usuario.getPassword().equals(usu.getPassword()) || 
//						usuario.getEstatus().getId() != usu.getEstatus().getId()) {
//					return false;
//					
//				}
				return true;
			}
		} else {
			if(usu != null) {
				return true;
			}
		}
		
		return false;
	}

}
