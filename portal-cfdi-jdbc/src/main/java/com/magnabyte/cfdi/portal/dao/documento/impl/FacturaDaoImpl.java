package com.magnabyte.cfdi.portal.dao.documento.impl;

import java.util.List;

import mx.gob.sat.cfd._3.Comprobante;

import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.documento.FacturaDao;
import com.magnabyte.cfdi.portal.model.documento.Documento;

@Repository("facturaDao")
public class FacturaDaoImpl extends GenericJdbcDao implements FacturaDao {

	@Override
	public List<Comprobante> obteberDatosAImprimir() {
		return null;
	}

	@Override
	public void create(Documento documento) {
		// TODO Auto-generated method stub
		
	}

}
