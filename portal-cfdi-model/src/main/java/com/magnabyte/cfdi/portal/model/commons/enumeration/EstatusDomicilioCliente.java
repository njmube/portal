package com.magnabyte.cfdi.portal.model.commons.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum EstatusDomicilioCliente {

	ACTIVO(1, "ACTIVO"), INACTIVO(2, "INACTIVO");
	
	private Integer id;
	private String nombre;
	private static Map<Integer, EstatusDomicilioCliente> idToEstatusMap;

	static {
		idToEstatusMap = new HashMap<Integer, EstatusDomicilioCliente>();
		for (EstatusDomicilioCliente estatus : values()) {
			idToEstatusMap.put(estatus.getId(), estatus);
		}
	}
	
	private EstatusDomicilioCliente(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static EstatusDomicilioCliente getById(Integer id) {
		return idToEstatusMap.get(id);
	}
}
