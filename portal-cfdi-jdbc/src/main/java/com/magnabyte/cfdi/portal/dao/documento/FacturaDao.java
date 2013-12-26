package com.magnabyte.cfdi.portal.dao.documento;

import java.util.List;

import mx.gob.sat.cfd._3.Comprobante;

import com.magnabyte.cfdi.portal.model.documento.Documento;

public interface FacturaDao {
	List<Comprobante> obteberDatosAImprimir();
	
	public void create(Documento documento);
}
