package com.magnabyte.cfdi.portal.dao.cliente.dummys;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.commons.Estado;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el domicilio de cliente dommy para pruebas
 */
public class DomicilioClienteDummy {

	public static DomicilioCliente generateDomicilioCliente() {
		DomicilioCliente domicilio = new DomicilioCliente();
		Cliente cliente = new Cliente();
		
		cliente.setId(1);
		domicilio.setCliente(cliente);
		domicilio.setCalle("Calle");
		domicilio.setNoExterior("No ext");
		domicilio.setNoInterior("No int");
		domicilio.setColonia("Colonia");
		domicilio.setMunicipio("Municipio");
		
		Estado estado = new Estado();
		estado.setId(1);
		
		domicilio.setEstado(estado);
		domicilio.setCodigoPostal("Codigo Postal");
		domicilio.setLocalidad("Localidad");
		domicilio.setReferencia("Referencia");
		
		return domicilio; 
	}
}
