package com.magnabyte.cfdi.portal.web.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusGenerico;
import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;
import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;
import com.magnabyte.cfdi.portal.model.establecimiento.DomicilioEstablecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.RutaRepositorio;
import com.magnabyte.cfdi.portal.model.establecimiento.SerieFolioEstablecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.TipoEstablecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.factory.EstablecimientoFactory;
import com.magnabyte.cfdi.portal.service.commons.OpcionDeCatalogoService;
import com.magnabyte.cfdi.portal.service.establecimiento.DomicilioEstablecimientoService;
import com.magnabyte.cfdi.portal.service.establecimiento.EstablecimientoService;
import com.magnabyte.cfdi.portal.service.establecimiento.RutaEstablecimientoService;
import com.magnabyte.cfdi.portal.service.establecimiento.SerieFolioEstablecimientoService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el controlador de establecimiento
 */
@Controller
@SessionAttributes("nuevoEstablecimiento")
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
	
	@Autowired
	private  SerieFolioEstablecimientoService serieFolioEstablecimientoService;
	
	@RequestMapping("/catalogoEstablecimiento")
	public String catalogoEstablecimiento(ModelMap model) {
		logger.debug("-- catalogoEstablecimiento");
		List<Establecimiento> establecimientos= establecimientoService.readAll();
		model.put("establecimientos", establecimientos);
		return "admin/listaEstablecimientos";
	}
	
	@RequestMapping("/establecimientoSerieFolio")
	public String establecimientoSerieFolio(ModelMap model) {
		logger.debug("-- catalogoEstablecimientoStatusA");
		List<Establecimiento> establecimientos= establecimientoService.readAll();
		List<Establecimiento> establecimientoswithSerie = new ArrayList<Establecimiento>();
		for (Establecimiento establecimiento : establecimientos) {
			establecimiento.setSerieFolioEstablecimientoLista(
					establecimientoService.readSerieFolioEstablecimiento(establecimiento));
			if (establecimiento.getTipoEstablecimiento().getId() != 3){
				establecimientoswithSerie.add(establecimiento);
			}
		}
		model.put("establecimientos", establecimientoswithSerie);
		return "admin/listaEstablecimientosStatusA";
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
		
		model.put("nuevoEstablecimiento", estable);
		model.put("listaPaises", opcionDeCatalogoService.getCatalogo("c_pais", "id_pais"));
		model.put("listaEstados", opcionDeCatalogoService.getCatalogoParam("c_estado", "id_pais", 
				estable.getDomicilio().getEstado().getPais().getId().toString(), "id_estado"));
		return "admin/establecimientoForm";
	}
	
	@RequestMapping(value = "/altaEstablecimiento")
	public String altaEstablecimiento (ModelMap model){
		model.put("nuevoEstablecimiento", new  Establecimiento());
		model.put("listaPaises", opcionDeCatalogoService.getCatalogo("c_pais", "id_pais"));
		model.put("listaEstados", opcionDeCatalogoService.getCatalogo("c_estado", "id_estado"));
		return "admin/altaEstablecimiento";
	}
	
	@RequestMapping(value = "/guardarEstablecimiento", method = RequestMethod.POST)
	public String guardarEstablecimiento(@ModelAttribute("nuevoEstablecimiento") Establecimiento establecimiento, ModelMap model){
		if (!establecimientoService.exist(establecimiento)) {
			if (establecimiento.getId() != null){
				establecimientoService.update(establecimiento); 
			} else {
				boolean serieFactura, serieNotaCredito;
				serieFactura = establecimientoService.existSerie(establecimiento.getSerieFolioEstablecimientoLista().get(0).getSerie());
				serieNotaCredito = establecimientoService.existSerie(establecimiento.getSerieFolioEstablecimientoLista().get(1).getSerie());
				if (!serieFactura && !serieNotaCredito){
					establecimientoService.save(establecimiento);
				} else {
					model.put("existSerie", true);
					model.put("nuevoEstablecimiento", establecimiento);
					if(serieFactura)
						model.put("existSerieFactura", true);
					else if(serieNotaCredito)
						model.put("existSerieNotaCredito", true);
					return "admin/altaSerieFolio";
				}
			}
		} else {
			muestraError(model, establecimiento);
			if (establecimiento.getId() != null){
				return "admin/establecimientoForm";
			} else {
				return "admin/altaEstablecimiento";
			}
		}
		return "redirect:/catalogoEstablecimiento";
	}
	
	@RequestMapping(value = "/asignarSerieFolio", method = RequestMethod.POST)
	public String asignarSerieFolio(@ModelAttribute("nuevoEstablecimiento") Establecimiento establecimiento, ModelMap model) {
		model.put("nuevoEstablecimiento", establecimiento);
		model.put("guardar", true);
		return "admin/altaSerieFolio";
	}
	
	@RequestMapping("/reAsignarSerieFolio/{id}")
	public String reasignarSerieFolio(@PathVariable int id, ModelMap model) {
		Establecimiento establecimiento = EstablecimientoFactory.newInstance(id);
		model.put("nuevoEstablecimiento", establecimiento);
		return "admin/altaSerieFolio";
	}
	
	
	@RequestMapping("/actualizarSerieFolio")
	public String actualizarSerieFolio(@ModelAttribute("nuevoEstablecimiento") Establecimiento establecimiento, ModelMap model) {
		logger.debug("Reasignando Serie y Folio");
		boolean serieFactura, serieNotaCredito;
		serieFactura = establecimientoService.existSerie(establecimiento.getSerieFolioEstablecimientoLista().get(0).getSerie());
		serieNotaCredito = establecimientoService.existSerie(establecimiento.getSerieFolioEstablecimientoLista().get(1).getSerie());
		if (!serieFactura && !serieNotaCredito){
			establecimientoService.updateSerieFolio(establecimiento);
		} else {
			model.put("existSerie", true);
			model.put("nuevoEstablecimiento", establecimiento);
			if(serieFactura)
				model.put("existSerieFactura", true);
			else if(serieNotaCredito)
				model.put("existSerieNotaCredito", true);
			return "admin/altaSerieFolio";
		}
		return "redirect:/establecimientoSerieFolio";
	}
	
	public ModelMap muestraError(ModelMap model, Establecimiento establecimiento) {
		model.put("error", true);
		model.put("messageError", "El nombre o clave de sucusal ya existe");
		Estado estado = domicilioEstablecimientoService.findEstado(establecimiento.getDomicilio().getEstado());
		establecimiento.getDomicilio().setEstado(estado);
		model.put("nuevoEstablecimiento",  establecimiento);
		model.put("listaPaises", opcionDeCatalogoService.getCatalogo("c_pais", "id_pais"));
		model.put("listaEstados", opcionDeCatalogoService.getCatalogoParam("c_estado", "id_pais", 
				establecimiento.getDomicilio().getEstado().getPais().getId().toString(), "id_estado"));
		return model;
	}
	
}
