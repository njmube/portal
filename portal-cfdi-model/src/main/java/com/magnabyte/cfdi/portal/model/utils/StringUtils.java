package com.magnabyte.cfdi.portal.model.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa las utilerias para formato de cadenas
 */
public class StringUtils {

	public static final Logger logger =
			LoggerFactory.getLogger(StringUtils.class);
	
    public static boolean isEmptyOrNull(String cadena) {
        return cadena == null || cadena.isEmpty();
    }

    public static boolean isReallyEmptyOrNull(String cadena) {
        return cadena == null || cadena.trim().isEmpty();
    }
    
    public static String convierteParam(String cadena) {
        if(cadena == null) {
            return null;
        } else {
            return cadena.trim().toUpperCase();
        }
    }
    
    public static Integer parseStringToInt(String string) {
        Integer numero = null;
        if(!isReallyEmptyOrNull(string)) {
            try {
                numero = Integer.parseInt(string);
            } catch (NumberFormatException ex) {
            	logger.info("Error al convertir la cadena");
                return null;
            }
        }
        return numero;
    }
    
    public static Double parseStringToDouble(String string) {
        Double numero = null;
        if(!isReallyEmptyOrNull(string)) {
            try {
                numero = Double.parseDouble(string);
            } catch (NumberFormatException ex) {
            	logger.info("Error al convertir la cadena");
                return null;
            }
        }
        return numero;
    }
    
    public static String getNullString(String cadena) {
        return isReallyEmptyOrNull(cadena) ? null : cadena;
    }

	public static String parseHexToString(String hex) {
		StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < hex.length() - 1; i += 2) {

            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);

            temp.append(decimal);
        }

        return sb.toString();
	}

	/**
	 * @param certificado
	 * @return
	 */
//	public static String toBase64(File certificado) {
//		byte[] arrCert = null;
//		String strCertificado = null;
//		arrCert = FileUtils.getBytes(certificado);
//		
//		strCertificado = new String(Base64.encode(arrCert));
////        strCertificado = b64.encode(arrCert);
//		return strCertificado;
//	}

	/**
	 * @param cadena
	 * @return
	 */
	public static String removeNewlines(String cadena) {
		return cadena.replaceAll("\\r|\\n", "");
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String parseDateToFilePath(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd");
		String path = formatter.format(date);
		
        return path;
	}
	
	public static String formatTicketClaveSucursal(String string) {
		NumberFormat nf = new DecimalFormat("000");
		Integer numeroSucursal = 0;
		try {
			numeroSucursal = Integer.valueOf(string);
		} catch (NumberFormatException nfe) {
			logger.error("El numero de sucursal es invalido:", nfe);
		}
		return nf.format(numeroSucursal);
	}
}
