package com.magnabyte.cfdi.portal.dao.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.commons.Pais;

public interface DomicilioClienteDao {

	List<DomicilioCliente> readByCliente(Cliente cliente);
	
	public void save(DomicilioCliente domicilio);

	public void update(DomicilioCliente domicilio);
	
	public void delete(DomicilioCliente domicilio);

	void saveEstado(Estado estado);
	
	void savePaisSinEstado(DomicilioCliente domicilio, Pais pais);
	
	public Estado readEstado(Estado estado);

	public Pais readPais(Pais pais);


}
