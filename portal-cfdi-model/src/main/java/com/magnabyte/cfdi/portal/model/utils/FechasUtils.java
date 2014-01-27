package com.magnabyte.cfdi.portal.model.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnabyte.cfdi.portal.model.exception.PortalException;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa las utilerias para formato de fechas
 */
public class FechasUtils {

	public static final Logger logger =
			LoggerFactory.getLogger(FechasUtils.class);
	
	public static final String formatddMMyyyyHyphen = "dd-MM-yyyy";
	public static final String formatddMMyyyySlash = "dd/MM/yyyy";
	public static final String formatyyyyMMdd = "yyyyMMdd";
	public static final String formatyyyyMMddHyphen = "yyyy-MM-dd";
	public static final String formatyyyyMMddSlash = "yyyy/MM/dd";
	public static final String formatddMMyyyyHHmmssSlash = "dd/MM/yyyy HH:mm:ss";
	public static final String formatyyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String formatyyyyMMddHHmmssHyphen = "yyyy-MM-dd HH:mm:ss";
	
	private static final long MILISECONDS_DAY = 24 * 60 * 60 * 1000;

	public static Date parseStringToDate(String strDate, String formatDate) {
		logger.debug("Iniciando el proceso de conversión de una cadena de texto a fecha");
		logger.debug("Convirtiendo la cadena: " + strDate
				+ " a fecha. La cadena tiene el formato: " + formatDate);
		Date fecha = null;
		try {
			if (StringUtils.isReallyEmptyOrNull(formatDate)) {
				logger.warn("El formato que tiene la fecha que se desea convertir no puede ser nulo");
				throw new PortalException(
						"Error en el formato de conversion de la fecha");
			}
			if (!StringUtils.isReallyEmptyOrNull(strDate)) {
				SimpleDateFormat format = new SimpleDateFormat(formatDate);
				fecha = format.parse(strDate);
			}
		} catch (ParseException ex) {
			throw new PortalException("Error al convertir la cadena: " + strDate
					+ " en una fecha.", ex);
		}
		logger.debug("El proceso termino correctamente");
		return fecha;
	}

	/**
	 * Convierte un Date a un String en el formato especificado.
	 * 
	 * @param fecha
	 * @param formato
	 * @return
	 */
	public static String parseDateToString(Date fecha, String formato) {
		logger.debug("Iniciando el proceso de conversión de una fecha a una cadena");
		SimpleDateFormat format = null;
		if (fecha != null) {
			if (!StringUtils.isReallyEmptyOrNull(formato)) {
				format = new SimpleDateFormat(formato);
			} else {
				throw new PortalException("El formato no puede ser nulo o vacío");
			}
		} else {
			throw new PortalException("La fecha no puede ser nula");
		}
		return format.format(fecha);
	}

	/**
	 * Obtiene el número de días entre un rango de fechas
	 * 
	 * @param startDate
	 *            Fecha Inicial
	 * @param endDate
	 *            Fecha Final
	 * @return Numero de días en entero
	 */
	public static Integer getDaysBetweenDates(Date startDate, Date endDate) {
		long diff = endDate.getTime() - startDate.getTime();
		Integer days = (int) Math.floor(diff / MILISECONDS_DAY);
		return days;
	}
	
	public static String specificStringFormatDate(String fecha, String formatoOrigen,
			String formatoDestino) {
		Date date = null;
		SimpleDateFormat format = null;
		if (fecha != null) {
			if (!StringUtils.isReallyEmptyOrNull(formatoDestino)) {
				format = new SimpleDateFormat(formatoDestino);
				 date = FechasUtils.parseStringToDate(fecha, formatoOrigen);
			} else {
				throw new PortalException("El formato no puede ser nulo o vacío");
			}
		} 
		else {
			throw new PortalException("La fecha no puede ser nula");
		}
		
		return format.format(date);
	}
}
