package com.magnabyte.cfdi.portal.dao.establecimiento.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

public class EstablecimientoSql extends GenericSql {
	
	public static final String TABLE_NAME = "t_establecimiento";
	
	public static final String 	ID_ESTABLECIMIENTO = "id_establecimiento";
	public static final String  CLAVE = "clave";
	public static final String  NOMBRE = "nombre";
	public static final String  PASSWORD = "password";
	public static final String  RUTA_REPOSITORIO = "ruta_repositorio";
	
	public static final String FIND_BY_CLAVE;
	
	static {
		StringBuilder qryBuilder = new StringBuilder();
		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(ID_ESTABLECIMIENTO).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(CLAVE)
		.append(PARENTESIS_FIN).append(AS).append(CLAVE).append(EOL_).append(TAB);
		qryBuilder.append(NOMBRE).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(PASSWORD)
		.append(PARENTESIS_FIN).append(AS).append(PASSWORD).append(EOL_).append(TAB);
		qryBuilder.append(RUTA_REPOSITORIO).append(EOL);
		
		qryBuilder.append(FROM).append(EOL).append(TAB);
		qryBuilder.append(TABLE_NAME).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(CLAVE).append(SET_PARAM);
		
		FIND_BY_CLAVE = qryBuilder.toString();
		
		qryBuilder = clearAndReuseStringBuilder(qryBuilder);
	}	
}
