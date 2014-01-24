package com.magnabyte.cfdi.portal.model.commons.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum EstatusDomiciolioCliente {

	ACTIVO(1, "ACTIVO"), INACTIVO(2, "INACTIVO");
	
	private Integer id;
	private String nombre;
	private static Map<Integer, EstatusDomiciolioCliente> idToEstatusMap;

	static {
		idToEstatusMap = new HashMap<Integer, EstatusDomiciolioCliente>();
		for (EstatusDomiciolioCliente estatus : values()) {
			idToEstatusMap.put(estatus.getId(), estatus);
		}
	}
	
	private EstatusDomiciolioCliente(Integer id, String nombre) {
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

	public static EstatusDomiciolioCliente getById(Integer id) {
		return idToEstatusMap.get(id);
	}
}
