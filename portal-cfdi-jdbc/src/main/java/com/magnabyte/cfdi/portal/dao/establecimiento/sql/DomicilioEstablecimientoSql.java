package com.magnabyte.cfdi.portal.dao.establecimiento.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa las variables con las consultas de acceso a datos
 * de domicilio de establecimiento
 */
public class DomicilioEstablecimientoSql extends GenericSql   {

	public static final String TABLE_NAME = "t_domicilio_establecimiento";
	public static final String TABLE_ALIAS = "tde";
	public static final String TABLE_ESTADO = "c_estado";
	public static final String TABLE_PAIS = "c_pais";
	
	public static final String ALIAS_ESTADO = "ce";
	public static final String AS_ESTADO = "nom_estado";
	public static final String ALIAS_PAIS = "cp";
	public static final String AS_PAIS = "nom_pais";
	public static final String NOMBRE = "nombre";
	
	public static final String ID_DOMICILIO = "id_domicilio_establecimiento";
	public static final String ID_ESTADO = "id_estado";
	public static final String ID_PAIS = "id_pais";
	public static final String CALLE = "calle";
	public static final String NO_EXTERIOR = "no_exterior";
	public static final String NO_INTERIOR = "no_interior";
	public static final String MUNICIPIO = "municipio";
	public static final String COLONIA = "colonia";
	public static final String CODIGO_POSTAL = "codigo_postal";
	public static final String LOCALIDAD = "localidad";
	
	public static final String FIND_DOM_BY_ID;
	public static final String UPDATE_DOMICILIO_ESTABLECIMIENTO;
	
	
	static {
		StringBuilder qryBuilder = new StringBuilder();
		
		qryBuilder.append(SELECT).append(EOL);
		qryBuilder.append(TAB).append(TABLE_ALIAS).append(DOT).append(ID_DOMICILIO).append(EOL_);
		qryBuilder.append(TAB).append(ALIAS_ESTADO).append(DOT).append(ID_ESTADO).append(EOL_);
		qryBuilder.append(TAB).append(ALIAS_PAIS).append(DOT).append(ID_PAIS).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(TABLE_ALIAS).append(DOT).append(CALLE);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(CALLE).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(TABLE_ALIAS).append(DOT).append(NO_EXTERIOR);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(NO_EXTERIOR).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(TABLE_ALIAS).append(DOT).append(NO_INTERIOR);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(NO_INTERIOR).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(TABLE_ALIAS).append(DOT).append(COLONIA);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(COLONIA).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(TABLE_ALIAS).append(DOT).append(MUNICIPIO);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(MUNICIPIO).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(TABLE_ALIAS).append(DOT).append(CODIGO_POSTAL);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(CODIGO_POSTAL).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(TABLE_ALIAS).append(DOT).append(LOCALIDAD);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(LOCALIDAD).append(EOL_);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_ESTADO).append(DOT).append(NOMBRE);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(AS_ESTADO).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_PAIS).append(DOT).append(NOMBRE);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(AS_PAIS).append(EOL);
		
		qryBuilder.append(FROM).append(EOL).append(TAB);		
		qryBuilder.append(TABLE_NAME).append(AS).append(TABLE_ALIAS);
		
		qryBuilder.append(INNER).append(TABLE_ESTADO).append(AS);
		qryBuilder.append(ALIAS_ESTADO).append(ON).append(ALIAS_ESTADO);
		qryBuilder.append(DOT).append(ID_ESTADO).append(EQ).append(TABLE_ALIAS);
		qryBuilder.append(DOT).append(ID_ESTADO).append(EOL).append(TAB);
		
		qryBuilder.append(INNER).append(TABLE_PAIS).append(AS);
		qryBuilder.append(ALIAS_PAIS).append(ON).append(ALIAS_PAIS);
		qryBuilder.append(DOT).append(ID_PAIS).append(EQ).append(ALIAS_ESTADO);
		qryBuilder.append(DOT).append(ID_PAIS).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(TABLE_ALIAS).append(DOT).append(ID_DOMICILIO).append(SET_PARAM);
		qryBuilder.append(EOL).append(ORDER).append(ID_DOMICILIO);
		
		FIND_DOM_BY_ID = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(UPDATE).append(EOL).append(TAB).append(TABLE_NAME);
		qryBuilder.append(EOL).append(SET).append(EOL);
		
		qryBuilder.append(TAB).append(ID_ESTADO).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(CALLE).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(NO_EXTERIOR).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(NO_INTERIOR).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(MUNICIPIO).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(COLONIA).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(CODIGO_POSTAL).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(LOCALIDAD).append(SET_PARAM).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ID_DOMICILIO).append(SET_PARAM);
		
		UPDATE_DOMICILIO_ESTABLECIMIENTO = qryBuilder.toString();
		
	}
}
