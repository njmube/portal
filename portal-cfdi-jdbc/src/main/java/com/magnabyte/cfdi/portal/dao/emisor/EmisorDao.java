package com.magnabyte.cfdi.portal.dao.emisor;

import mx.gob.sat.cfd._3.TUbicacion;

import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

public interface EmisorDao {

	EmpresaEmisor read(EmpresaEmisor empresa);

	TUbicacion readLugarExpedicion(Establecimiento establecimiento);
}
