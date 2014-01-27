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
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.commons.UsuarioService;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que represente el controlador de usuario
 */
@Controller
public class UsuarioController {

	private static final Logger logger = LoggerFactory
			.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EstablecimientoService establecimientoService;
	
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
		Usuario usuario = usuarioService.read(usu);
		model.put("listaEstablecimientos",establecimientoService.readAll()); 
		model.put("usuario", usuario);
		
		return "admin/usuarioForm";	
	}
	
	@RequestMapping(value = "/guardarUsuario", method = RequestMethod.POST)
	public String guardarUsuarios (ModelMap model, @ModelAttribute Usuario usuario) {
		try {
			if (usuario.getId() != null) {
				usuarioService.update(usuario);
			} else {
				usuarioService.save(usuario);
			}
		} catch (PortalException ex) {
				model.put("error", true);
				model.put("messageError", ex.getMessage());
				model.put("listaEstablecimientos" ,establecimientoService.readAll());
			
				if (usuario.getId() != null){
					model.put("usuario", usuarioService.read(usuario));
				} else {
					model.put("usuario", usuarioService.getUsuarioByEstablecimiento(usuario));
				}
				
			return  usuario.getId() != null ? "admin/usuarioForm"  : "admin/altaUsuario";
				
		}
		return "redirect:/catalogoUsuarios";
	}
	
	@RequestMapping("/altaUsuario")
	public String altaUsuarios(ModelMap model) {
		model.put("listaEstablecimientos",establecimientoService.readAll());
		model.put("usuario", new Usuario());
		return "admin/altaUsuario";
	}
	
}
