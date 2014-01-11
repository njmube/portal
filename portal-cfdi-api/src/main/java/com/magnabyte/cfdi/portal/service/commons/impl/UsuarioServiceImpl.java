package com.magnabyte.cfdi.portal.service.commons.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.commons.UsuarioDao;
import com.magnabyte.cfdi.portal.model.commons.Usuario;
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

}
