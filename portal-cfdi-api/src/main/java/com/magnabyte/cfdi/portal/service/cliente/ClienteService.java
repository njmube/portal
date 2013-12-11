package com.magnabyte.cfdi.portal.service.cliente;

import java.util.List;

import mx.gob.sat.cfd._3.Comprobante.Receptor;

public interface ClienteService {

	List<Receptor> findClientesByNameRfc(Receptor receptor);

}
