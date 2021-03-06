package com.magnabyte.cfdi.portal.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.service.cliente.ClienteService;
import com.magnabyte.cfdi.portal.service.commons.OpcionDeCatalogoService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el controlador de cliente
 */ 
@Controller
@SessionAttributes({"cliente"})
public class ClienteController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private OpcionDeCatalogoService opcionDeCatalogoService;
	
	@Value("${generic.rfc.extranjeros}")
	private String genericRfcExtranjeros;
	
	@RequestMapping("/portal/cfdi/buscaPorRfc")
	public String buscaPorRfc(@ModelAttribute Cliente cliente, ModelMap model) {
		Cliente clienteBD = clienteService.findClienteByRfc(cliente);
		if (clienteBD != null) {
			model.put("cliente", clienteBD);
			return "portal/confirmarDatos";
		} else {
			model.put("cliente", cliente);
			model.put("errorMsg", true);
			return "portal/buscaRfc";
		}
	}
	
	@RequestMapping(value = "/listaClientes", method = RequestMethod.POST)
	public String listaClientes(ModelMap model, @ModelAttribute Cliente cliente) {		
		List<Cliente> clientes = clienteService.findClientesByNameRfc(cliente);
		if(!clientes.isEmpty()) {
			model.put("emptyList", false);
		}
		model.put("clientes", clientes);
		return "sucursal/listaClientes";
	}
	
	@RequestMapping(value = {"/clienteForm", "/portal/cfdi/clienteForm"})
	public String clienteForm(@ModelAttribute Cliente cliente, ModelMap model) {
		logger.debug("regresando forma cliente");
		model.put("cliente", cliente);
		model.put("rfcExtranjeros", genericRfcExtranjeros);
		model.put("listaPaises", opcionDeCatalogoService.getCatalogo("c_pais", "id_pais"));
		model.put("emptyList", true);
		return "sucursal/clienteForm";
	}
	
	@RequestMapping(value = "/confirmarDatos/{viewError}", method = RequestMethod.POST)
	public String confirmarDatos(@Valid @ModelAttribute("clienteCorregir") Cliente cliente, BindingResult result, ModelMap model, 
			@PathVariable String viewError) {
		logger.debug("Confimar datos");	
		if (result.hasErrors()) {
			model.put("errorSave", true);
			model.put("errorMessage", result.getAllErrors());
			model.put("listaPaises", opcionDeCatalogoService
					.getCatalogo("c_pais", "id_pais"));
			model.put("rfcExtranjeros", genericRfcExtranjeros);
			logger.debug(result.getAllErrors().toString());
			
			return "sucursal/" + viewError;
		}
		
		if(cliente.getId() != null) {		
			clienteService.update(cliente);
		} else {
			try {
				clienteService.save(cliente);
			} catch (PortalException ex) {
				model.put("errorSave", true);
				model.put("errorMessage", ex.getMessage());
				model.put("listaPaises", opcionDeCatalogoService
						.getCatalogo("c_pais", "id_pais"));
				model.put("rfcExtranjeros", genericRfcExtranjeros);
				return "sucursal/" + viewError;
			}
		}
		model.put("cliente", cliente);
		logger.debug("Cliente: {}", cliente.getId());		
		return "redirect:/confirmarDatos/" + cliente.getId();
	}
	
	@RequestMapping(value = "/portal/cfdi/confirmarDatos/{viewError}", method = RequestMethod.POST)
	public String confirmarDatosPortal(@Valid @ModelAttribute("clienteCorregir") Cliente cliente, BindingResult result, ModelMap model, 
			@PathVariable String viewError) {
		logger.debug("Confimar datos");	
		if (result.hasErrors()) {
			model.put("error", result.getAllErrors());
			logger.debug(result.getAllErrors().toString());
			if (viewError.equals("clienteForm")) {
				return "sucursal/" + viewError;
			}
			return "portal/" + viewError;
		}
		
		if(cliente.getId() != null) {		
			clienteService.update(cliente);
		} else {
			clienteService.save(cliente);
		}
		model.put("cliente", cliente);
		logger.debug("Cliente: {}", cliente.getId());		
		return "redirect:/portal/cfdi/confirmarDatos/" + cliente.getId();
	}
	
	@RequestMapping("/clienteCorregir/{id}")
	public String corregirDatos(@PathVariable Integer id, ModelMap model) {
		logger.debug("confirmarDatos page");
		Cliente cliente = clienteService.read(ClienteFactory.newInstance(id));
		model.put("clienteCorregir", cliente);
		model.put("rfcExtranjeros", genericRfcExtranjeros);
		model.put("listaEstados", opcionDeCatalogoService.getCatalogoParam("c_estado", "id_pais", 
				cliente.getDomicilios().get(0).getEstado().getPais().getId().toString(), "id_estado"));
		return "sucursal/clienteCorregir";
	}
	
	@RequestMapping("/portal/cfdi/clienteCorregir/{id}")
	public String corregirDatosPortal(@PathVariable Integer id, ModelMap model) {
		logger.debug("confirmarDatos page");
		Cliente cliente = clienteService.read(ClienteFactory.newInstance(id));
		model.put("clienteCorregir", cliente);
		model.put("rfcExtranjeros", genericRfcExtranjeros);
		model.put("listaEstados", opcionDeCatalogoService.getCatalogoParam("c_estado", "id_pais", 
				cliente.getDomicilios().get(0).getEstado().getPais().getId().toString(), "id_estado"));
		return "portal/clienteCorregir";
	}
	
	@RequestMapping(value = "/refacturacion/listaClientes", method = RequestMethod.POST)
	public String listaClientesRefact(ModelMap model, @ModelAttribute Cliente cliente) {		
		List<Cliente> clientes = clienteService.findClientesByNameRfc(cliente);
		if(!clientes.isEmpty()) {
			model.put("emptyList", false);
		}
		model.put("clientes", clientes);
		return "documento/listaClientes";
	}
	
	@RequestMapping(value = {"/refacturacion/clienteForm"})
	public String clienteFormRefact(@ModelAttribute Cliente cliente, ModelMap model) {
		logger.debug("regresando forma cliente");
		model.put("cliente", cliente);
		model.put("rfcExtranjeros", genericRfcExtranjeros);
		model.put("listaPaises", opcionDeCatalogoService.getCatalogo("c_pais", "id_pais"));
		model.put("emptyList", true);
		return "documento/clienteForm";
	}
	
	@RequestMapping(value = "/refacturacion/confirmarDatos/{viewError}", method = RequestMethod.POST)
	public String confirmarDatosRefact(@Valid @ModelAttribute("clienteCorregir") Cliente cliente, BindingResult result, ModelMap model, 
			@PathVariable String viewError) {
		logger.debug("Confimar datos");	
		if (result.hasErrors()) {
			model.put("errorSave", true);
			model.put("errorMessage", result.getAllErrors());
			model.put("listaPaises", opcionDeCatalogoService
					.getCatalogo("c_pais", "id_pais"));
			model.put("rfcExtranjeros", genericRfcExtranjeros);
			logger.debug(result.getAllErrors().toString());
			
			return "documento/" + viewError;
		}
		
		if(cliente.getId() != null) {		
			clienteService.update(cliente);
		} else {
			try {
				clienteService.save(cliente);
			} catch (PortalException ex) {
				model.put("errorSave", true);
				model.put("errorMessage", ex.getMessage());
				model.put("listaPaises", opcionDeCatalogoService
						.getCatalogo("c_pais", "id_pais"));
				model.put("rfcExtranjeros", genericRfcExtranjeros);
				return "documento/" + viewError;
			}
		}
		model.put("cliente", cliente);
		logger.debug("Cliente: {}", cliente.getId());		
		return "redirect:/refacturacion/confirmarDatos/" + cliente.getId();
	}
	
	@RequestMapping("/refacturacion/clienteCorregir/{id}")
	public String corregirDatosRefact(@PathVariable Integer id, ModelMap model) {
		logger.debug("confirmarDatos page");
		Cliente cliente = clienteService.read(ClienteFactory.newInstance(id));
		model.put("clienteCorregir", cliente);
		model.put("rfcExtranjeros", genericRfcExtranjeros);
		model.put("listaEstados", opcionDeCatalogoService.getCatalogoParam("c_estado", "id_pais", 
				cliente.getDomicilios().get(0).getEstado().getPais().getId().toString(), "id_estado"));
		return "documento/clienteCorregir";
	}
	
	@RequestMapping("/refacturacion/buscaRfc")
	public String buscaRfcRefact(ModelMap model) {
		logger.debug("buscaRfc page");
		logger.debug("Ticket: ---{}", (Ticket)model.get("ticket"));
		model.put("cliente", new Cliente());
		model.put("emptyList", true);
		return "documento/buscaRfc";
	}
	
	@RequestMapping("/refacturacion/confirmarDatos/{id}")
	public String confirmarDatosRefact(@PathVariable Integer id, ModelMap model) {
		logger.debug("confirmarDatos page");
		model.put("cliente", clienteService.read(ClienteFactory.newInstance(id)));
		return "documento/confirmarDatos";
	}

	
}
