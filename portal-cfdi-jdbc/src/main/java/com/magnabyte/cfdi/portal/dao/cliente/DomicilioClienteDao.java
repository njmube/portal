package com.magnabyte.cfdi.portal.dao.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;

public interface DomicilioClienteDao {

	List<DomicilioCliente> readByCliente(Cliente cliente);
}
