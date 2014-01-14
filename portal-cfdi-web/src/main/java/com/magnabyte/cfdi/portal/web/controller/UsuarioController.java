package com.magnabyte.cfdi.portal.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.magnabyte.cfdi.portal.model.commons.Usuario;

@Controller
public class UsuarioController {

	private static final Logger logger = LoggerFactory
			.getLogger(UsuarioController.class);
	
	@RequestMapping("/catalogoUsuarios")
	public String catalogoEstablecimiento(ModelMap model) {
		logger.debug("-- catalogoUsuarios");
		List<Usuario> usuarios = null;//establecimientoService.readAll();
		model.put("usuarios", usuarios);
		return "admin/listaEstablecimientos";
	}
	
	@RequestMapping("/mostrarUsuario/{id}")
	public String mostrarUsuario(@PathVariable int id, ModelMap model) {
		logger.debug("-- id "+id);
		Usuario usu = new Usuario();
		usu.setId(id);
//		Usuario usuario = usuarioService.readById(usu);
		
//		model.put("usuario", usuario);
		
		return "admin/usuarioForm";	
	}
	
}
