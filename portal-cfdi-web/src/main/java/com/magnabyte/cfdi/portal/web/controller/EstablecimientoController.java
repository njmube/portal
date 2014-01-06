package com.magnabyte.cfdi.portal.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.factory.EstablecimientoFactory;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;


@Controller
public class EstablecimientoController {
	
	private static final Logger logger = LoggerFactory.getLogger(EstablecimientoController.class);
	
	@Autowired
	private EstablecimientoService establecimientoService;
	
	@RequestMapping("/catalogoEstablecimiento")
	public String catalogoEstablecimiento(ModelMap model) {
		logger.debug("-- catalogoEstablecimiento");
		List<Establecimiento> establecimientos= establecimientoService.readAll();
		model.put("establecimientos", establecimientos);
		return "admin/establecimiento";
	}
	
	@RequestMapping("/actualizarEstablecimiento/{id}")
	public String guardarEstablecimiento(@PathVariable Integer id, ModelMap model) {
		logger.debug("-- id "+id);
		Establecimiento establecimiento = EstablecimientoFactory.newInstance(id);
		model.put("establecimiento", establecimientoService.readById(establecimiento));
		return "admin/establecimientoForm";
	}
}
