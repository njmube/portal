package com.magnabyte.cfdi.portal.model.ticket;

import java.util.HashMap;
import java.util.Map;

public enum TipoEstadoTicket {
	GUARDADO(1), FACTURADO_MOSTRADOR(2), FACTURADO(3);
	
	private int id;
	
	private static Map<Integer, TipoEstadoTicket> idToTipoEstadoTicketMap;

	static {
		idToTipoEstadoTicketMap = new HashMap<Integer, TipoEstadoTicket>();
		for (TipoEstadoTicket estadoTicket : values()) {
			idToTipoEstadoTicketMap.put(estadoTicket.getId(), estadoTicket);
		}
	}
	
	private TipoEstadoTicket(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public static TipoEstadoTicket getById(int id) {
		return idToTipoEstadoTicketMap.get(id);
	}
	
}
