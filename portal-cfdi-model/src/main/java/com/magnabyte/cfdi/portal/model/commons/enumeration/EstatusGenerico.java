package com.magnabyte.cfdi.portal.model.commons.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum EstatusGenerico {

	ACTIVO(1, "ACTIVO"), INACTIVO(2, "INACTIVO");
	
	private int id;
	private String nombre;
	private static Map<Integer, EstatusGenerico> idToEstatusMap;

	static {
		idToEstatusMap = new HashMap<Integer, EstatusGenerico>();
		for (EstatusGenerico estatus : values()) {
			idToEstatusMap.put(estatus.getId(), estatus);
		}
	}
	
	private EstatusGenerico(int id, String nombre) {
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

	public static EstatusGenerico getById(int id) {
		return idToEstatusMap.get(id);
	}
}
