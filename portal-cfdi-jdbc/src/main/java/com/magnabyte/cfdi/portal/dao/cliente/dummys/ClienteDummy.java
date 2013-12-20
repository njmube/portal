package com.magnabyte.cfdi.portal.dao.cliente.dummys;

import java.util.ArrayList;
import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.commons.Estado;

public class ClienteDummy {

	public static Cliente generateCliente() {
		Cliente cliente = new Cliente();
		
		cliente.setId(5);
		cliente.setNombre("Omar Velasco Peña");
		cliente.setRfc("VEPO8408291T5");
		cliente.setDomicilios(getDomicilios());
		
		return cliente;
	}
	
	private static List<DomicilioCliente> getDomicilios(){
		List<DomicilioCliente> domicilios = new ArrayList<DomicilioCliente>();
		
		DomicilioCliente domicilio = new DomicilioCliente();
		Cliente cliente = new Cliente();
		
		cliente.setId(5);
		domicilio.setCliente(cliente);
		domicilio.setCalle("Norte 81");
		domicilio.setNoExterior("408");
		domicilio.setNoInterior("21");
		domicilio.setColonia("Electricistas");
		domicilio.setMunicipio("Azcapotzalco");
		
		Estado estado = new Estado();
		estado.setId(1);
		
		domicilio.setEstado(estado);
		domicilio.setCodigoPostal("02060");
		domicilio.setLocalidad("México");
		domicilio.setReferencia("Entre Aqiuiles Elorduy y Rabaúl");
		
		domicilios.add(domicilio);
		return domicilios;
	}
}
