package com.magnabyte.cfdi.portal.dao.factura;

import java.util.List;

import mx.gob.sat.cfd._3.Comprobante;

public interface FacturaDao {
	List<Comprobante> obteberDatosAImprimir();
}
