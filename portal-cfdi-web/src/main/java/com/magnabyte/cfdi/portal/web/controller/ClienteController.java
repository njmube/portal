package com.magnabyte.cfdi.portal.web.controller;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.cfd._3.Comprobante.Receptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClienteController {

	private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
	
	@RequestMapping("/listaClientes")
	public String listaClientes(ModelMap model, @RequestParam(value = "rfc", required = false) boolean rfc) {
		logger.debug("listaaaa");
		List<Receptor> clientes = new ArrayList<Receptor>();
		if (rfc)
		for (int i = 0; i < 15; i ++) {
			Receptor rec = new Receptor();
			rec.setRfc("rfc" + i);
			rec.setNombre("nombre" + i);
			clientes.add(rec);
		}
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
