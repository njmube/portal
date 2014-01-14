package com.magnabyte.cfdi.portal.service.commons;

import java.util.List;

import com.magnabyte.cfdi.portal.model.commons.Usuario;

public interface UsuarioService {

	Usuario getUsuarioByEstablecimiento(Usuario usuario);

	List<Usuario> getAllUsuarios();

	void update(Usuario usuario);

	void save(Usuario usuario);
}
