package com.magnabyte.cfdi.portal.dao.cliente.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

public class DomicilioSql extends GenericSql {

	public static final String TABLE_NAME = "t_domicilio_cliente";
	public static final String TABLE_ALIAS = "tdc";
	public static final String TABLE_ESTADO = "c_estado";
	public static final String TABLE_PAIS = "c_pais";
	
	public static final String ALIAS_ESTADO = "ce";
	public static final String AS_ESTADO = "nom_estado";
	public static final String ALIAS_PAIS = "cp";
	public static final String AS_PAIS = "nom_pais";
	public static final String NOMBRE = "nombre";
	
	public static final String ID_DOMICILIO = "id_domicilio";
	public static final String ID_ESTADO = "id_estado";
	public static final String ID_PAIS = "id_pais";
	public static final String ID_CLIENTE = "id_cliente";
	public static final String CALLE = "calle";
	public static final String NO_EXTERIOR = "no_exterior";
	public static final String NO_INTERIOR = "no_interior";
	public static final String MUNICIPIO = "municipio";
	public static final String COLONIA = "colonia";
	public static final String CODIGO_POSTAL = "codigo_postal";
	public static final String REFERENCIA = "referencia";
	public static final String LOCALIDAD = "localidad";
	
	public static final String FIND_DOM_BY_CLIENTE;
	
	static {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(SELECT).append(EOL).append(TAB);
		queryBuilder.append(ALL).append(EOL_).append(TAB);
		queryBuilder.append(ALIAS_ESTADO).append(DOT).append(NOMBRE);
		queryBuilder.append(AS).append(AS_ESTADO).append(EOL_).append(TAB);
		queryBuilder.append(ALIAS_PAIS).append(DOT).append(NOMBRE);
		queryBuilder.append(AS).append(AS_PAIS).append(EOL);
		
		queryBuilder.append(FROM).append(EOL).append(TAB);		
		queryBuilder.append(TABLE_NAME).append(AS).append(TABLE_ALIAS);
		queryBuilder.append(INNER).append(TABLE_ESTADO).append(AS);
		queryBuilder.append(ALIAS_ESTADO).append(ON).append(ALIAS_ESTADO);
		queryBuilder.append(DOT).append(ID_ESTADO).append(EQ).append(TABLE_ALIAS);
		queryBuilder.append(DOT).append(ID_ESTADO).append(EOL).append(TAB);
		
		queryBuilder.append(INNER).append(TABLE_PAIS).append(AS);
		queryBuilder.append(ALIAS_PAIS).append(ON).append(ALIAS_PAIS);
		queryBuilder.append(DOT).append(ID_PAIS).append(EQ).append(ALIAS_ESTADO);
		queryBuilder.append(DOT).append(ID_PAIS).append(EOL);
		
		queryBuilder.append(WHERE).append(EOL).append(TAB);
		queryBuilder.append(TABLE_ALIAS).append(DOT).append(ID_CLIENTE).append(SET_PARAM);
		
		FIND_DOM_BY_CLIENTE = queryBuilder.toString();
		
		queryBuilder = clearAndReuseStringBuilder(queryBuilder);
	}
	
}
