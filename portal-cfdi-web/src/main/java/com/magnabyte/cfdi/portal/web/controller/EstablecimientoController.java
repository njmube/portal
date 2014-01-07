package com.magnabyte.cfdi.portal.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.magnabyte.cfdi.portal.model.establecimiento.DomicilioEstablecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.RutaRepositorio;
import com.magnabyte.cfdi.portal.model.establecimiento.factory.EstablecimientoFactory;
import com.magnabyte.cfdi.portal.service.commons.OpcionDeCatalogoService;
import com.magnabyte.cfdi.portal.service.establecimiento.DomicilioEstablecimientoService;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;
import com.magnabyte.cfdi.portal.service.establecimiento.RutaEstablecimientoService;


@Controller
public class EstablecimientoController {
	
	private static final Logger logger = LoggerFactory.getLogger(EstablecimientoController.class);
	
	@Autowired
	private EstablecimientoService establecimientoService;
	
	@Autowired
	private RutaEstablecimientoService rutaEstablecimientoService;
	
	@Autowired
	private DomicilioEstablecimientoService domicilioEstablecimientoService;
	
	@Autowired
	private OpcionDeCatalogoService opcionDeCatalogoService;
	
	
	@RequestMapping("/catalogoEstablecimiento")
	public String catalogoEstablecimiento(ModelMap model) {
		logger.debug("-- catalogoEstablecimiento");
		List<Establecimiento> establecimientos= establecimientoService.readAll();
		model.put("establecimientos", establecimientos);
		return "admin/listaEstablecimientos";
	}
	
	@RequestMapping("/mostrarEstablecimiento/{id}")
	public String guardarEstablecimiento(@PathVariable int id, ModelMap model) {
		logger.debug("-- id "+id);
		Establecimiento establecimiento = EstablecimientoFactory.newInstance();
		DomicilioEstablecimiento domEsta = new DomicilioEstablecimiento();
		RutaRepositorio rutaRepo = new RutaRepositorio();
		establecimiento.setId(id);
		Establecimiento estable = establecimientoService.readAllById(establecimiento);

		domEsta.setId(estable.getDomicilio().getId());
		rutaRepo.setId(estable.getRutaRepositorio().getId());
		
		estable.setDomicilio(domicilioEstablecimientoService.readById(domEsta));
		estable.setRutaRepositorio(rutaEstablecimientoService.readById(rutaRepo));
		
		
		model.put("establecimiento", estable);
		model.put("listaPaises", opcionDeCatalogoService.getCatalogo("c_pais", "id_pais"));
		model.put("listaEstados", opcionDeCatalogoService.getCatalogo("c_estado", "id_estado"));
		return "admin/establecimientoForm";
	}
	@RequestMapping(value = "/guardarEstablecimiento", method = RequestMethod.POST)
	public String guardarEstablecimiento(@ModelAttribute Establecimiento establecimiento){
		
		if (establecimiento.getId() != null){
			domicilioEstablecimientoService.update(establecimiento.getDomicilio());
			rutaEstablecimientoService.update(establecimiento.getRutaRepositorio());
			establecimientoService.update(establecimiento); 
		}else {
			domicilioEstablecimientoService.save(establecimiento.getDomicilio());
			rutaEstablecimientoService.save(establecimiento.getRutaRepositorio());
			establecimientoService.save(establecimiento);
		}
		
		return "redirect:/catalogoEstablecimiento";
	}
	
	@RequestMapping(value = "/altaEstablecimiento")
	public String altaEstablecimiento (@ModelAttribute Establecimiento establecimiento, ModelMap model){
		model.put("establecimiento", new  Establecimiento());
		model.put("listaPaises", opcionDeCatalogoService.getCatalogo("c_pais", "id_pais"));
		model.put("listaEstados", opcionDeCatalogoService.getCatalogo("c_estado", "id_estado"));
		return "admin/altaEstablecimiento";
	}
}
