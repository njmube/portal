package com.magnabyte.cfdi.portal.dao.cliente;

import java.util.List;

import mx.gob.sat.cfd._3.Comprobante.Receptor;

public interface ClienteDao {

	List<Receptor> findClientesByNameRfc(Receptor receptor);

}
