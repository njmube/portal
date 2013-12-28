package com.magnabyte.cfdi.portal.model.documento;

public enum TipoDocumento {
	
	FACTURA(1, "FACTURA"), NOTA_CREDITO(2, "NOTA DE CRÉDITO");
	
	private int id;
	private String nombre;

	private TipoDocumento(int id, String nombre) {
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
}
