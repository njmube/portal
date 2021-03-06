package com.magnabyte.cfdi.portal.model.ticket;

import java.util.HashMap;
import java.util.Map;

public enum TipoEstadoTicket {
	FACTURADO(1), FACTURADO_MOSTRADOR(2), DEVUELTO(3), NCR_GENERADA(4), REFACTURADO(5), POR_REFACTURAR(6);
	
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
