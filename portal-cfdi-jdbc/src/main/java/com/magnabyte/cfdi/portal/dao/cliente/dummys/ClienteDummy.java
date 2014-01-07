package com.magnabyte.cfdi.portal.dao.cliente.dummys;

import java.util.ArrayList;
import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.commons.Pais;

public class ClienteDummy {

	public static Cliente generateCliente() {
		Cliente cliente = new Cliente();
		
		cliente.setId(4);
		cliente.setNombre("Edgar Pérez Castellón");
		cliente.setRfc("PECE890903HD3");
		cliente.setDomicilios(getDomicilios());
		
		return cliente;
	}
	
	public static Cliente generateClienteDif() {
		Cliente cliente = new Cliente();
		
		cliente.setId(4);
		cliente.setNombre("Edgar Ramirez Ñongora");
		cliente.setRfc("PECE890903HD3R");
		cliente.setDomicilios(getDomiciliosDif());
		
		return cliente;
	}
	
	public static Cliente generateClienteNoExiste() {
		Cliente cliente = new Cliente();
		
		cliente.setNombre("COCA TRAFICO S.A. de C.V.");
		cliente.setRfc("CCTR890903HD3");
		cliente.setDomicilios(getDomiciliosNoExiste());
		
		return cliente;
	}
	
	private static List<DomicilioCliente> getDomicilios() {
		List<DomicilioCliente> domicilios = new ArrayList<DomicilioCliente>();
		
		DomicilioCliente domicilio = new DomicilioCliente();
		Cliente cliente = new Cliente();
		
		cliente.setId(1);
		domicilio.setCliente(cliente);
		domicilio.setCalle("Norte 79-A");
		domicilio.setNoExterior("377");
		domicilio.setNoInterior("2");
		domicilio.setColonia("Electricistas");
		domicilio.setMunicipio("Azcapotzalco");
		
		Pais pais = new Pais();
		pais.setId(1);
		pais.setNombre("México");
		
		Estado estado = new Estado();
		estado.setId(9);
		estado.setNombre("Distrito Federal");
		estado.setPais(pais);
		
		domicilio.setEstado(estado);
		domicilio.setCodigoPostal("02060");
		domicilio.setLocalidad("México");
		domicilio.setReferencia("Entre Rabaúl y Aquiles Elorduy");
		
		domicilios.add(domicilio);
		return domicilios;
	}
	
	private static List<DomicilioCliente> getDomiciliosDif() {
		List<DomicilioCliente> domicilios = new ArrayList<DomicilioCliente>();
		
		DomicilioCliente domicilio = new DomicilioCliente();
		Cliente cliente = new Cliente();
		
		cliente.setId(1);
		domicilio.setCliente(cliente);
		domicilio.setCalle("Norte 79-B");
		domicilio.setNoExterior("3772");
		domicilio.setNoInterior("21");
		domicilio.setColonia("Electricistasf");
		domicilio.setMunicipio("Azcapotzalcod");
		
		Pais pais = new Pais();
		pais.setId(2);
		pais.setNombre("México");
		
		Estado estado = new Estado();
		estado.setId(19);
		estado.setNombre("Nuevo León");
		estado.setPais(pais);
		
		domicilio.setEstado(estado);
		domicilio.setCodigoPostal("020609");
		domicilio.setLocalidad("México DF");
		domicilio.setReferencia("Entre Aqiuiles Elorduy y Rabau");
		
		domicilios.add(domicilio);
		return domicilios;
	}
	
	private static List<DomicilioCliente> getDomiciliosNoExiste() {
		List<DomicilioCliente> domicilios = new ArrayList<DomicilioCliente>();
		
		DomicilioCliente domicilio = new DomicilioCliente();
		Cliente cliente = new Cliente();
		
		domicilio.setCliente(cliente);
		domicilio.setCalle("Norte 65");
		domicilio.setNoExterior("45");
		domicilio.setNoInterior("78");
		domicilio.setColonia("Sara Goza");
		domicilio.setMunicipio("Azcapolanco");
		
		Pais pais = new Pais();
		pais.setId(2);
		pais.setNombre("México");
		
		Estado estado = new Estado();
		estado.setId(19);
		estado.setNombre("Nuevo León");
		estado.setPais(pais);
		
		domicilio.setEstado(estado);
		domicilio.setCodigoPostal("020609");
		domicilio.setLocalidad("México DF");
		domicilio.setReferencia("Entre Aqiuiles Elorduy y Rabau");
		
		domicilios.add(domicilio);
		return domicilios;
	}
}
