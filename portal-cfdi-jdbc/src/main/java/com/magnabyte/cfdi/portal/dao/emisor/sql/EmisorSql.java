package com.magnabyte.cfdi.portal.dao.emisor.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;
import com.magnabyte.cfdi.portal.dao.cliente.sql.DomicilioSql;

public class EmisorSql extends GenericSql {

	public static final String TABLE_NAME = "t_emisor";
	public static final String ALIAS_TABLE = "tem";
	
	public static final String ID_EMISOR = "id_emisor";	
	public static final String NOMBRE = "nombre";
	public static final String RFC = "rfc";
	public static final String CALLE = "calle";
	public static final String NO_EXTERIOR = "no_exterior";
	public static final String NO_INTERIOR = "no_interior";
	public static final String PAIS = "nombre_pais";
	public static final String ESTADO = "nombre_estado";
	public static final String MUNICIPIO = "municipio";
	public static final String COLONIA = "colonia";
	public static final String CODIGO_POSTAL = "codigo_postal";
	public static final String LOCALIDAD = "localidad";
	public static final String REGIMEN_FISCAL = "regimen_fiscal";

	public static final String READ;
	
	static {
		StringBuilder qryBuilder = new StringBuilder();
		
		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(ALIAS_TABLE).append(DOT).append(ID_EMISOR).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TABLE).append(DOT).append(REGIMEN_FISCAL);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(REGIMEN_FISCAL).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TABLE).append(DOT).append(NOMBRE);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(NOMBRE).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TABLE).append(DOT).append(RFC);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(RFC).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TABLE).append(DOT).append(CALLE);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(CALLE).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TABLE).append(DOT).append(NO_EXTERIOR);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(NO_EXTERIOR).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TABLE).append(DOT).append(NO_INTERIOR);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(NO_INTERIOR).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TABLE).append(DOT).append(COLONIA);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(COLONIA).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TABLE).append(DOT).append(MUNICIPIO);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(MUNICIPIO).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TABLE).append(DOT).append(CODIGO_POSTAL);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(CODIGO_POSTAL).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TABLE).append(DOT).append(LOCALIDAD);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(LOCALIDAD).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(DomicilioSql.ALIAS_PAIS).append(DOT).append(NOMBRE);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(PAIS).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(DomicilioSql.ALIAS_ESTADO).append(DOT).append(NOMBRE);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(ESTADO).append(EOL);
		
		qryBuilder.append(FROM).append(EOL).append(TAB);
		qryBuilder.append(TABLE_NAME).append(AS).append(ALIAS_TABLE);
		
		qryBuilder.append(INNER).append(DomicilioSql.TABLE_ESTADO).append(AS).append(DomicilioSql.ALIAS_ESTADO);
		qryBuilder.append(ON).append(DomicilioSql.ALIAS_ESTADO).append(DOT).append(DomicilioSql.ID_ESTADO).append(EQ);
		qryBuilder.append(ALIAS_TABLE).append(DOT).append(DomicilioSql.ID_ESTADO).append(EOL).append(TAB);
		
		qryBuilder.append(INNER).append(DomicilioSql.TABLE_PAIS).append(AS).append(DomicilioSql.ALIAS_PAIS);
		qryBuilder.append(ON).append(DomicilioSql.ALIAS_PAIS).append(DOT).append(DomicilioSql.ID_PAIS).append(EQ);
		qryBuilder.append(DomicilioSql.ALIAS_ESTADO).append(DOT).append(DomicilioSql.ID_PAIS).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ALIAS_TABLE).append(DOT).append(ID_EMISOR).append(SET_PARAM);
		
		READ = qryBuilder.toString();
		qryBuilder = clearAndReuseStringBuilder(qryBuilder);
		
	}
	
}
