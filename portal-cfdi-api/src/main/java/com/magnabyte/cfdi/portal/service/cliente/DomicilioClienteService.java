package com.magnabyte.cfdi.portal.service.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;

public interface DomicilioClienteService {

	List<DomicilioCliente>  getByCliente(Cliente cliente);
}
