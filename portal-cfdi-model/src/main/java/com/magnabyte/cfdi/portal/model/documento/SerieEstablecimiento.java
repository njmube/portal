package com.magnabyte.cfdi.portal.model.documento;

import com.magnabyte.cfdi.portal.model.commons.OpcionDeCatalogo;

public class SerieEstablecimiento {

	private Integer id;
	private String serie;
	private String folioInicial;
	private String folioConsecutivo;
	private OpcionDeCatalogo tipoDocumento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getFolioInicial() {
		return folioInicial;
	}

	public void setFolioInicial(String folioInicial) {
		this.folioInicial = folioInicial;
	}

	public String getFolioConsecutivo() {
		return folioConsecutivo;
	}

	public void setFolioConsecutivo(String folioConsecutivo) {
		this.folioConsecutivo = folioConsecutivo;
	}

	public OpcionDeCatalogo getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(OpcionDeCatalogo tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

}
