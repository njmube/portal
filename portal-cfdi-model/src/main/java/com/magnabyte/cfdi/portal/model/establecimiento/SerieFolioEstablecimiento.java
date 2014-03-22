package com.magnabyte.cfdi.portal.model.establecimiento;

import java.io.Serializable;

import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusGenerico;
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
	private Integer id;
	
	/**
	 * {@link Establecimiento}
	 */
	private Establecimiento establecimiento;
	
	/**
	 * Serie del {@link Establecimiento}
	 */
	private String serie;
	
	/**
	 * Folio inicial del {@link Establecimiento}
	 */
	private Integer folioInicial;
	
	/**
	 * Folio consecutivo del {@link Establecimiento}
	 */
	private Integer folioConsecutivo;
	
	/**
	 * Status del la serie y folio del {@link Establecimiento}
	 */
	private EstatusGenerico status;
	
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
	 * Devuelve el folio inicial del establecimiento
	 * @return folio
	 */
	public Integer getFolioInicial() {
		return folioInicial;
	}

	/**
	 * Asigna el folio inicial del establecimiento
	 * @param folio_inicial
	 */
	public void setFolioInicial(Integer folioInicial) {
		this.folioInicial = folioInicial;
	}

	/**
	 * Devuelve el status de la serie y folio del establecimiento 
	 * @return
	 */
	public EstatusGenerico getStatus() {
		return status;
	}

	/**
	 * Asigna el status de la serie y folio del establecimiento
	 * @param status
	 */
	public void setStatus(EstatusGenerico status) {
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

	/**
	 * Devuelve el folio consecutivo del establecimiento
	 * @return folio_consecutivo
	 */
	public Integer getFolioConsecutivo() {
		return folioConsecutivo;
	}

	/**
	 * Asigna el folio consecutivo de Establecimiento
	 * @param folio_consecutivo
	 */
	public void setFolioConsecutivo(Integer folioConsecutivo) {
		this.folioConsecutivo = folioConsecutivo;
	}
	
}
