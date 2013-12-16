package com.magnabyte.cfdi.portal.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.factory.ClienteFactory;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;

@Controller
@SessionAttributes("establecimiento")
public class SucursalCfdiController {

	@Autowired
	private ClienteService clienteService;
	
	private static final Logger logger = LoggerFactory.getLogger(SucursalCfdiController.class);
	
	@RequestMapping("/buscaRfc")
	public String buscaRfc(@ModelAttribute Cliente cliente, ModelMap model) {
		logger.debug("buscaRfc page");
		model.put("emptyList", true);
		return "sucursal/buscaRfc";
	}
	
	@RequestMapping("/confirmarDatos/{id}")
	public String confirmarDatos(@PathVariable Integer id, ModelMap model) {
		logger.debug("confirmarDatos page");
		model.put("cliente", clienteService.read(ClienteFactory.newInstance(id)));
		return "sucursal/confirmarDatos";
	}
}
