package com.magnabyte.cfdi.portal.service.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.commons.Pais;

public interface DomicilioClienteService {

	List<DomicilioCliente>  getByCliente(Cliente cliente);
	
	public void save(Cliente cliente);
	
	public void update(Cliente cliente);
	
	void saveEstado(Estado estado);
	
	void savePaisSinEstado(DomicilioCliente domicilio, Pais pais);
	
	public Estado readEstado(Estado estado);

	public Pais readPais(Pais pais);


	
}
