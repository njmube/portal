package com.magnabyte.cfdi.portal.dao.factura;

import java.util.Collection;

import mx.gob.sat.cfd._3.Comprobante;

public interface FacturaDao {
	Collection<Comprobante> obteberDatosAImprimir();
}
