package com.magnabyte.cfdi.portal.dao.cliente;

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
 * Interfáz que representa el acceso a datos del domicilio de cliente
 */
public interface DomicilioClienteDao {

	List<DomicilioCliente> readByCliente(Cliente cliente);
	
	void save(DomicilioCliente domicilio);

	void update(DomicilioCliente domicilio);
	
	void delete(DomicilioCliente domicilio);

	void saveEstado(Estado estado);
	
	void savePaisSinEstado(DomicilioCliente domicilio, Pais pais);
	
	Estado readEstado(Estado estado);

	Pais readPais(Pais pais);

	DomicilioCliente readById(DomicilioCliente domicilioCliente);


}
