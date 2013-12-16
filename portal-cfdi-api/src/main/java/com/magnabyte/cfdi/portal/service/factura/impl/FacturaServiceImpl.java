package com.magnabyte.cfdi.portal.service.factura.impl;

import java.util.Collection;

import mx.gob.sat.cfd._3.Comprobante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.dao.factura.FacturaDao;
import com.magnabyte.cfdi.portal.service.factura.FacturaService;

@Service("facturaService")
public class FacturaServiceImpl implements FacturaService {
	
	@Autowired
	private FacturaDao facturaDao;
	
	@Override
	public Collection<Comprobante> obtenerDatos() {
		return facturaDao.obteberDatosAImprimir();
	}

}
