package com.magnabyte.cfdi.portal.dao.cliente.dummys;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;

public class ClienteDummy {

	public static Cliente generateCliente() {
		Cliente cliente = new Cliente();
		
		cliente.setId(1);
		cliente.setNombre("Omar Velasco Peña");
		cliente.setRfc("VEPO8408291T5");
		
		return cliente;
	}
}
