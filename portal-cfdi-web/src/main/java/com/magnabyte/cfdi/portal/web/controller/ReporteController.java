package com.magnabyte.cfdi.portal.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

@Controller
public class ReporteController {
	@RequestMapping("/cfdi/reportep")
	public String reporte(ModelMap model) {
		List<Establecimiento> establecimientos = new ArrayList<Establecimiento>();
		for (int i = 0; i < 15; i++) {
			Establecimiento e = new Establecimiento();
			e.setClave("clave" + i);
			e.setNombre("Nombre" + i);
			e.setPassword("password" + i);
			e.setRutaRepositorio("url" + i);
			establecimientos.add(e);
		}
		model.put("objetoKey", establecimientos);
		return "prueba";
	}

}
