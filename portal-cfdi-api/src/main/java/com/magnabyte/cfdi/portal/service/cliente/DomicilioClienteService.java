package com.magnabyte.cfdi.portal.service.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.commons.Pais;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el servicio de domicilio de cliente
 */
public interface DomicilioClienteService {

	List<DomicilioCliente>  getByCliente(Cliente cliente);
	
	void save(Cliente cliente);
	
	void update(Cliente cliente);
	
	void saveEstado(Estado estado);
	
	void savePaisSinEstado(DomicilioCliente domicilio, Pais pais);
	
	Estado readEstado(Estado estado);

	Pais readPais(Pais pais);
	
}
