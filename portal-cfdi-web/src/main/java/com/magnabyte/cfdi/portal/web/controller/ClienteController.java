package com.magnabyte.cfdi.portal.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.factory.ClienteFactory;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.commons.OpcionDeCatalogoService;

@Controller
@SessionAttributes({"cliente", "ticket"})
public class ClienteController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private OpcionDeCatalogoService opcionDeCatalogoService;
	
	@RequestMapping("/listaClientes")
	public String listaClientes(ModelMap model, @ModelAttribute Cliente cliente) {
		List<Cliente> clientes = clienteService.findClientesByNameRfc(cliente);
		if(!clientes.isEmpty())
			model.put("emptyList", false);
		model.put("clientes", clientes);
		return "sucursal/listaClientes";
	}
	
	@RequestMapping("/clienteForm")
	public String clienteForm(@ModelAttribute Cliente cliente, ModelMap model) {
		logger.debug("regresando forma cliente");
		model.put("listaPaises", opcionDeCatalogoService.getCatalogo("c_pais", "id_pais"));
		return "sucursal/clienteForm";
	}
	
	@RequestMapping(value = "/confirmarDatos", method = RequestMethod.POST)
	public String confirmarDatos(@Valid @ModelAttribute Cliente cliente, ModelMap model, BindingResult result) {
		logger.debug("Confimar datos");	
		if (result.hasErrors()) {
			return "redirect:/confirmarDatos/" + cliente.getId();
		}
		
		if(cliente.getId() != null) {		
			clienteService.update(cliente);
		} else {
			clienteService.save(cliente);
		}
		model.put("cliente", cliente);
		logger.debug("Cliente: {}", cliente.getId());		
		return "redirect:/confirmarDatos/" + cliente.getId();
	}
	
	@RequestMapping("/clienteCorregir/{id}")
	public String corregirDatos(@PathVariable Integer id, ModelMap model) {
		logger.debug("confirmarDatos page");
		model.put("cliente", clienteService.read(ClienteFactory.newInstance(id)));
		model.put("listaPaises", opcionDeCatalogoService.getCatalogo("c_pais", "id_pais"));
		model.put("listaEstados", opcionDeCatalogoService.getCatalogo("c_estado", "id_estado"));
		return "sucursal/clienteCorregir";
	}
	
	@RequestMapping("/datosFacturacion/{idDomicilio}")
	public String datosFacturacion(@ModelAttribute Cliente cliente, 
			@ModelAttribute Ticket ticket, @PathVariable Integer idDomicilio) {
		logger.debug(cliente.toString());
		logger.debug(ticket.toString());
		return "sucursal/datosFacturacion";
	}
}
