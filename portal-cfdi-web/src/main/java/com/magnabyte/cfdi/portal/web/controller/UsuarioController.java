package com.magnabyte.cfdi.portal.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.magnabyte.cfdi.portal.model.commons.Usuario;
import com.magnabyte.cfdi.portal.service.commons.UsuarioService;

@Controller
public class UsuarioController {

	private static final Logger logger = LoggerFactory
			.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping("/catalogoUsuarios")
	public String catalogoEstablecimiento(ModelMap model) {
		logger.debug("-- catalogoUsuarios");
		List<Usuario> usuarios = usuarioService.getAllUsuarios();
		model.put("usuarios", usuarios);
		return "admin/listaUsuarios";
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
	
	@RequestMapping(value = "/guardarUsuario", method = RequestMethod.POST)
	public String guardarUsuarios (@ModelAttribute Usuario usuario ) {
		if (usuario.getId() != 0){
//			usuarioService.update(usuario);
		} else {
//			usuarioService.save(usuario);
		}
		
		return "redirect:/catalogoUsuarios";
	}
	
}
