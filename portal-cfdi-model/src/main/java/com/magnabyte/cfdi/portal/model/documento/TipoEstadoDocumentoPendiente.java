package com.magnabyte.cfdi.portal.model.documento;

public enum TipoEstadoDocumentoPendiente {
	SELLO_PENDIENTE(1), TIMBRE_PENDIENTE(2), ACUSE_PENDIENTE(3);

	private int id;

	private TipoEstadoDocumentoPendiente(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
