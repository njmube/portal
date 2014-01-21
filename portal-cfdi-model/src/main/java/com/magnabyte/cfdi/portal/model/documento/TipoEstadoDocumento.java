package com.magnabyte.cfdi.portal.model.documento;

import java.util.HashMap;
import java.util.Map;

public enum TipoEstadoDocumento {
	PENDIENTE(1), FACTURADO(2);

	private int id;
	
	private static Map<Integer, TipoEstadoDocumento> idToTipoEstadoDocumentoMap;
	
	static {
		idToTipoEstadoDocumentoMap = new HashMap<Integer, TipoEstadoDocumento>();
		for (TipoEstadoDocumento estado : values()) {
			idToTipoEstadoDocumentoMap.put(estado.getId(), estado);
		}
	}
	
	private TipoEstadoDocumento(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static TipoEstadoDocumento getById(int id) {
		return idToTipoEstadoDocumentoMap.get(id);
	}
}
