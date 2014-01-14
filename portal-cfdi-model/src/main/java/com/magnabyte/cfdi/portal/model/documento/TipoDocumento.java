package com.magnabyte.cfdi.portal.model.documento;

import java.util.HashMap;
import java.util.Map;

public enum TipoDocumento {
	
	FACTURA(1, "FACTURA", "ingreso"), NOTA_CREDITO(2, "NOTA DE CRÉDITO", "egreso");
	
	private int id;
	private String nombre;
	private String nombreComprobante;
	private static Map<Integer, TipoDocumento> idToTipoDocumentoMap;

	static {
		idToTipoDocumentoMap = new HashMap<Integer, TipoDocumento>();
		for (TipoDocumento tipoDocumento : values()) {
			idToTipoDocumentoMap.put(tipoDocumento.getId(), tipoDocumento);
		}
	}
	
	private TipoDocumento(int id, String nombre, String nombreComprobante) {
		this.id = id;
		this.nombre = nombre;
		this.nombreComprobante = nombreComprobante;
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
	
	public String getNombreComprobante() {
		return nombreComprobante;
	}

	public void setNombreComprobante(String nombreComprobante) {
		this.nombreComprobante = nombreComprobante;
	}

	public static TipoDocumento getById(int id) {
		return idToTipoDocumentoMap.get(id);
	}
}
