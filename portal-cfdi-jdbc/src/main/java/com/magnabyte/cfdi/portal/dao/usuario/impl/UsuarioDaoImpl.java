package com.magnabyte.cfdi.portal.dao.usuario.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.usuario.UsuarioDao;

@Repository("usuarioDao")
public class UsuarioDaoImpl extends GenericJdbcDao implements UsuarioDao {

	private static final Logger logger = LoggerFactory
			.getLogger(UsuarioDaoImpl.class);

}
