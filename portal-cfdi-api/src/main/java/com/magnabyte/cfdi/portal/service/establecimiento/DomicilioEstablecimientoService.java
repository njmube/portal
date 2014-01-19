package com.magnabyte.cfdi.portal.service.establecimiento;

import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.establecimiento.DomicilioEstablecimiento;

public interface DomicilioEstablecimientoService {

	void save(DomicilioEstablecimiento domicilioEstablecimiento);

	void update(DomicilioEstablecimiento domicilioEstablecimiento);

	Estado readEstado(Estado estado);

	DomicilioEstablecimiento readById(
			DomicilioEstablecimiento domicilioEstablecimiento);

	Estado findEstado(Estado estado);

}
