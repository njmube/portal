package com.magnabyte.cfdi.portal.model.establecimiento;

public class RutaRepositorio {

	private Integer id;
	private String rutaRepositorio;
	private String rutaRepoIn;
	private String rutaRepoOut;
	private String rutaRepoInProc;
	private Establecimiento establecimiento;

	/**
	 * Constructor por default
	 */
	public RutaRepositorio() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getRutaRepositorio() {
		return rutaRepositorio;
	}

	public void setRutaRepositorio(String rutaRepositorio) {
		this.rutaRepositorio = rutaRepositorio;
	}

	public String getRutaRepoIn() {
		return rutaRepoIn;
	}

	public void setRutaRepoIn(String rutaRepoIn) {
		this.rutaRepoIn = rutaRepoIn;
	}

	public String getRutaRepoOut() {
		return rutaRepoOut;
	}

	public void setRutaRepoOut(String rutaRepoOut) {
		this.rutaRepoOut = rutaRepoOut;
	}

	public String getRutaRepoInProc() {
		return rutaRepoInProc;
	}

	public void setRutaRepoInProc(String rutaRepoInProc) {
		this.rutaRepoInProc = rutaRepoInProc;
	}

}
