package com.magnabyte.cfdi.portal.web.utils;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magnabyte.cfdi.portal.model.commons.OpcionDeCatalogo;
import com.magnabyte.cfdi.portal.service.commons.OpcionDeCatalogoService;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que represente el controlador de autocompletado
 */
@Controller
public class AutocompleteController {

	private static final Logger logger = 
			LoggerFactory.getLogger(AutocompleteController.class);
	
	@Autowired
	private OpcionDeCatalogoService opcionDeCatalogoService;
		
	@RequestMapping(value = "/portal/cfdi/listaEstados", method = RequestMethod.POST)
	public @ResponseBody Collection<OpcionDeCatalogo> getEstados(@RequestParam String pais) {
		logger.debug("Identificador del pais: {}", pais);
		Collection<OpcionDeCatalogo> response = opcionDeCatalogoService.
				getCatalogoParam("c_estado", "id_pais", pais, "id_estado");
		return response;
	}
}
