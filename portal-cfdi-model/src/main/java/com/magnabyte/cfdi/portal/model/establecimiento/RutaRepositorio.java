package com.magnabyte.cfdi.portal.model.establecimiento;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa la ruta repositorio
 */
public class RutaRepositorio {

	/**
	 * Identificador único de ruta repositorio
	 */
	private Integer id;
	
	/**
	 * Ruta repositorio
	 */
	private String rutaRepositorio;
	
	/**
	 * Ruta repositorio de entrada
	 */
	private String rutaRepoIn;
	
	/**
	 * Ruta repositorio de salida
	 */
	private String rutaRepoOut;
	
	/**
	 * 
	 */
	private String rutaRepoInProc;
	
	/**
	 * Establecimiento al que pertenece la ruta
	 * de repositorio
	 */
	private Establecimiento establecimiento;

	/**
	 * Constructor por default
	 */
	public RutaRepositorio() {
	}

	/**
	 * Devuelve el identificador único de
	 * ruta repositorio
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Asigna el identificador único de 
	 * ruta repositorio
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Devuelve el establecimiento de 
	 * ruta repositorio
	 * @return establecimiento {@link Establecimiento}
	 */
	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}

	/**
	 * Asigna el establecimiento de ruta repositorio
	 * @param establecimiento {@link Establecimiento}
	 */
	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	/**
	 * Devuelve la ruta repositorio
	 * @return rutaRepositorio
	 */
	public String getRutaRepositorio() {
		return rutaRepositorio;
	}

	/**
	 * Asigna la ruta repositorio
	 * @param rutaRepositorio
	 */
	public void setRutaRepositorio(String rutaRepositorio) {
		this.rutaRepositorio = rutaRepositorio;
	}

	/**
	 * Devuelve la ruta repositorio de entrada
	 * @return rutaRepoIn
	 */
	public String getRutaRepoIn() {
		return rutaRepoIn;
	}

	/**
	 * Asigna la ruta repositorio de entrada
	 * @param rutaRepoIn
	 */
	public void setRutaRepoIn(String rutaRepoIn) {
		this.rutaRepoIn = rutaRepoIn;
	}

	/**
	 * Devuelve la ruta repositorio de salida
	 * @return rutaRepoOut
	 */
	public String getRutaRepoOut() {
		return rutaRepoOut;
	}

	/**
	 * Asigna la ruta repositorio de salida
	 * @param rutaRepoOut
	 */
	public void setRutaRepoOut(String rutaRepoOut) {
		this.rutaRepoOut = rutaRepoOut;
	}

	/**
	 * 
	 * @return
	 */
	public String getRutaRepoInProc() {
		return rutaRepoInProc;
	}

	/**
	 * 
	 * @param rutaRepoInProc
	 */
	public void setRutaRepoInProc(String rutaRepoInProc) {
		this.rutaRepoInProc = rutaRepoInProc;
	}

}
