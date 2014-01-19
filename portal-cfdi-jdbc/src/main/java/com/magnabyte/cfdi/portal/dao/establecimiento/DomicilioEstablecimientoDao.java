package com.magnabyte.cfdi.portal.dao.establecimiento;

import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.establecimiento.DomicilioEstablecimiento;

public interface DomicilioEstablecimientoDao {

	void save(DomicilioEstablecimiento domicilioEstablecimiento);

	Estado readEstado(Estado estado);

	void update(DomicilioEstablecimiento domicilio);

	DomicilioEstablecimiento readById(DomicilioEstablecimiento domicilioEstablecimiento);

	Estado findEstado(Estado estado);


}
