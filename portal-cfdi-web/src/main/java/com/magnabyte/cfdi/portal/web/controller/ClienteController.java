package com.magnabyte.cfdi.portal.web.controller;

import java.util.List;

import mx.gob.sat.cfd._3.Comprobante.Receptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.magnabyte.cfdi.portal.service.cliente.ClienteService;

@Controller
public class ClienteController {

	private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping("/listaClientes")
	public String listaClientes(ModelMap model, @ModelAttribute Receptor receptor) {
		logger.debug("listaaaa");
		List<Receptor> clientes = clienteService.findClientesByNameRfc(receptor);
		if(!clientes.isEmpty())
			model.put("emptyList", false);
		model.put("clientes", clientes);
		return "sucursal/listaClientes";
	}
	
	@RequestMapping("/clienteForm")
	public String clienteForm(@ModelAttribute Receptor receptor) {
		logger.debug("regresando forma cliente");
		return "sucursal/clienteForm";
	}
	
	@RequestMapping(value = "/confirmarDatos", method = RequestMethod.POST)
	public String confirmarDatos(@ModelAttribute Receptor receptor) {
		logger.debug("Confimar datos");
		return "sucursal/confirmarDatos";
	}
}
