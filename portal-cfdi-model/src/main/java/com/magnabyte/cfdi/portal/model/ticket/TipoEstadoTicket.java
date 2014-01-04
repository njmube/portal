package com.magnabyte.cfdi.portal.model.ticket;

public enum TipoEstadoTicket {
	GUARDADO(1), FACTURADO_MOSTRADOR(2), FACTURADO(3);
	
	private int id;

	private TipoEstadoTicket(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
