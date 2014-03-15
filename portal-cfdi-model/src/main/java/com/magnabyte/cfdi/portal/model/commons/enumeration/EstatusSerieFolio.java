package com.magnabyte.cfdi.portal.model.commons.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum EstatusSerieFolio {
	
ACTIVO(1, "A"), INACTIVO(2, "I");
	
	private int id;
	private String nombre;
	private static Map<Integer, EstatusSerieFolio> idToEstatusMap;

	static {
		idToEstatusMap = new HashMap<Integer, EstatusSerieFolio>();
		for (EstatusSerieFolio estatus : values()) {
			idToEstatusMap.put(estatus.getId(), estatus);
		}
	}
	
	private EstatusSerieFolio(int id, String nombre) {
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

	public static EstatusSerieFolio getById(int id) {
		return idToEstatusMap.get(id);
	}

}
