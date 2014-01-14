package com.magnabyte.cfdi.portal.model.commons.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum EstatusUsuario {

	ACTIVO(1, "ACTIVO"), INACTIVO(2, "INACTIVO");
	
	private int id;
	private String nombre;
	private static Map<Integer, EstatusUsuario> idToEstatusMap;

	static {
		idToEstatusMap = new HashMap<Integer, EstatusUsuario>();
		for (EstatusUsuario estatus : values()) {
			idToEstatusMap.put(estatus.getId(), estatus);
		}
	}
	
	private EstatusUsuario(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static EstatusUsuario getById(int id) {
		return idToEstatusMap.get(id);
	}
}
