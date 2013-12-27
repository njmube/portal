package com.magnabyte.cfdi.portal.model.documento;

public enum TipoDocumento {
	
	FACTURA(1), NOTA_CREDITO(2);
	
	private int id;

	private TipoDocumento(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
}
