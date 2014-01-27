package com.magnabyte.cfdi.portal.dao.commons;

import java.util.List;

import com.magnabyte.cfdi.portal.model.commons.Usuario;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el acceso a datos de usuario
 */
public interface UsuarioDao {

	Usuario getUsuarioByEstablecimiento(Usuario usuario);

	Usuario read(Usuario usuario);
	
	List<Usuario> getAllUsuarios();

	void update(Usuario usuario);

	void save(Usuario usuario);

}
