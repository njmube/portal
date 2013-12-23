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
import com.magnabyte.cfdi.portal.service.documento.DocumentoService;

@Controller
@SessionAttributes({"cliente", "ticket"})
public class ClienteController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private DocumentoService documentoService;
	
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
	public String clienteForm(ModelMap model) {
		logger.debug("regresando forma cliente");
		model.put("cliente", new Cliente());
		model.put("listaPaises", opcionDeCatalogoService.getCatalogo("c_pais", "id_pais"));
		model.put("emptyList", true);
		return "sucursal/clienteForm";
	}
	
	@RequestMapping(value = "/confirmarDatos/{viewError}", method = RequestMethod.POST)
	public String confirmarDatos(@Valid @ModelAttribute Cliente cliente, BindingResult result, ModelMap model, 
			@PathVariable String viewError) {
		logger.debug("Confimar datos");	
		if (result.hasErrors()) {
			model.put("error", result.getAllErrors());
			logger.debug(result.getAllErrors().toString());
			return "sucursal/" + viewError;
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
			@ModelAttribute Ticket ticket, @PathVariable Integer idDomicilio, ModelMap model) {
		model.put("comprobante", documentoService.obtenerComprobantePor(
				cliente, ticket, idDomicilio));		
		return "corporativo/facturaValidate";
	}
}
