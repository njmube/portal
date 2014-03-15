package com.magnabyte.cfdi.portal.model.establecimiento;

import java.io.Serializable;

import com.magnabyte.cfdi.portal.model.documento.TipoDocumento;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:14/03/2014
 * Clase que representa la serie y folio de un {@link Establecimiento}
 */
public class SerieFolioEstablecimiento implements Serializable {

	/**
	 * Serial generado por la clase Serializable
	 */
	private static final long serialVersionUID = -5664032600923265794L;
	
	/**
	 * Identificador único de l serie y folio de un {@link Establecimiento}
	 */
	private int id;
	
	/**
	 * {@link Establecimiento}
	 */
	private Establecimiento establecimiento;
	
	/**
	 * Serie del {@link Establecimiento}
	 */
	private String serie;
	
	/**
	 * Folio del {@link Establecimiento}
	 */
	private String folio;
	
	/**
	 * Status del la serie y folio del {@link Establecimiento}
	 */
	private String status;
	
	/**
	 * Tipo de documento
	 */
	private TipoDocumento tipoDocumento;
	
	/**
	 * Devuelve el identificador único de la serie y folio del establecimiento 
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Asigna el identificador unico de la serie y folio del establecimiento
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Devuelve el {@link Establecimiento}
	 * @return {@link Establecimiento}
	 */
	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}
	
	/**
	 * Asigna el {@link Establecimiento}
	 * @param establecimiento
	 */
	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}
	
	/**
	 * Devuelve la serie del establecimiento
	 * @return serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * Asigna la serie del establecimiento
	 * @param serie
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * Devuelve el folio del establecimiento
	 * @return folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * Asigna el folio del estableimiento
	 * @param folio
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * Devuelve el status de la serie y folio del establecimiento 
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Asigna el status de la serie y folio del establecimiento
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Devuelve el {@link TipoDocumento}
	 * @return
	 */
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * Asigna el {@link TipoDocumento}
	 * @param tipoDocumento
	 */
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}
