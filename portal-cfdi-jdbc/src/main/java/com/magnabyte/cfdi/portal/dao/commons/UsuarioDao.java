package com.magnabyte.cfdi.portal.dao.commons;

import java.util.List;

import com.magnabyte.cfdi.portal.model.commons.Usuario;

public interface UsuarioDao {

	Usuario getUsuarioByEstablecimiento(Usuario usuario);

	List<Usuario> getAllUsuarios();

	void update(Usuario usuario);

	void save(Usuario usuario);
}
