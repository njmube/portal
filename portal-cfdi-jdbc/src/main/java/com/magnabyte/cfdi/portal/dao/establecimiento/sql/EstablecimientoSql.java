package com.magnabyte.cfdi.portal.dao.establecimiento.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;
import com.magnabyte.cfdi.portal.dao.cliente.sql.DomicilioSql;

public class EstablecimientoSql extends GenericSql {
	
	public static final String TABLE_NAME = "t_establecimiento";
	public static final String TABLE_DOM_ESTAB = "t_domicilio_establecimiento";
	public static final String TABLE_RUTA_ESTAB = "t_ruta_establecimiento";
	public static final String CATALOGO_TIPO_ESTAB = "c_tipo_establecimiento";
	
	public static final String ALIAS_TABLE = "te";
	public static final String ALIAS_TIPO_ESTAB = "cte";
	public static final String ALIAS_DOM_ESTAB = "tde";
	public static final String ALIAS_RUTA_ESTAB = "tre";
	
	public static final String ROL = "rol";
	public static final String CLAVE = "clave";
	public static final String NOMBRE = "nombre";
	public static final String PASSWORD = "password";
	public static final String ID_ESTABLECIMIENTO = "id_establecimiento";
	public static final String ID_EMISOR = "id_emisor";
	
	public static final String ID_TIPO_ESTAB = "id_tipo_establecimiento";
	public static final String NOM_ESTAB = "nombre_estab";
	public static final String ROL_ESTAB = "rol_estab";
	
	public static final String LOCALIDAD = "localidad";
	public static final String ID_DOM_ESTAB = "id_domicilio_establecimiento";
	
	public static final String RUTA_IN = "ruta_in";
	public static final String RUTA_OUT = "ruta_out";
	public static final String RUTA_INPROC = "ruta_inproc";
	public static final String RUTA_REPOSITORIO = "ruta_repo";
	public static final String ID_RUTA_ESTAB = "id_ruta_establecimiento";
	
	public static final String GET_ROLES;
	public static final String FIND_BY_CLAVE;
	public static final String READ_BY_CLAVE;
	public static final String READ_LUGAR_EXP;
	public static final String READ_ALL;
	public static final String READ_ALL_WITH_IDS;
	public static final String UPDATE_ESTABLECIMIENTO;
	
	static {
		StringBuilder qryBuilder = new StringBuilder();
		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(ID_ESTABLECIMIENTO).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(CLAVE)
		.append(PARENTESIS_FIN).append(AS).append(CLAVE).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(NOMBRE)
		.append(PARENTESIS_FIN).append(AS).append(NOMBRE).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(PASSWORD)
		.append(PARENTESIS_FIN).append(AS).append(PASSWORD).append(EOL);
		
		qryBuilder.append(FROM).append(EOL).append(TAB);
		qryBuilder.append(TABLE_NAME).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(CLAVE).append(SET_PARAM);
		
		FIND_BY_CLAVE = qryBuilder.toString();
		qryBuilder = clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TIPO_ESTAB);
		qryBuilder.append(DOT).append(ROL).append(PARENTESIS_FIN).append(AS).append(ROL);
		qryBuilder.append(EOL).append(FROM).append(EOL).append(TAB);
		qryBuilder.append(TABLE_NAME).append(AS).append(ALIAS_TABLE).append(EOL).append(TAB);
		qryBuilder.append(INNER).append(CATALOGO_TIPO_ESTAB).append(AS).append(ALIAS_TIPO_ESTAB);
		qryBuilder.append(ON).append(ALIAS_TABLE).append(DOT).append(ID_TIPO_ESTAB);
		qryBuilder.append(EQ).append(ALIAS_TIPO_ESTAB).append(DOT).append(ID_TIPO_ESTAB);
		
		qryBuilder.append(EOL).append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ALIAS_TABLE).append(DOT).append(ID_ESTABLECIMIENTO).append(SET_PARAM);
		
		GET_ROLES = qryBuilder.toString();
		qryBuilder = clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(ALIAS_TABLE).append(DOT).append(ID_ESTABLECIMIENTO).append(EOL_).append(TAB);
		qryBuilder.append(ALIAS_TABLE).append(DOT).append(ID_EMISOR).append(EOL_).append(TAB);
		qryBuilder.append(ALIAS_TIPO_ESTAB).append(DOT).append(ID_TIPO_ESTAB).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TIPO_ESTAB).append(DOT);
		qryBuilder.append(NOMBRE).append(PARENTESIS_FIN).append(AS).append(NOM_ESTAB).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TIPO_ESTAB).append(DOT);
		qryBuilder.append(ROL).append(PARENTESIS_FIN).append(AS).append(ROL).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TABLE).append(DOT);
		qryBuilder.append(CLAVE).append(PARENTESIS_FIN).append(AS).append(CLAVE).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_TABLE).append(DOT);
		qryBuilder.append(NOMBRE).append(PARENTESIS_FIN).append(AS).append(NOMBRE).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_DOM_ESTAB).append(DOT);
		qryBuilder.append(LOCALIDAD).append(PARENTESIS_FIN).append(AS).append(LOCALIDAD).append(EOL_).append(TAB);
		qryBuilder.append(ALIAS_RUTA_ESTAB).append(DOT).append(ID_RUTA_ESTAB).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_RUTA_ESTAB).append(DOT);
		qryBuilder.append(RUTA_INPROC).append(PARENTESIS_FIN).append(AS).append(RUTA_INPROC).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_RUTA_ESTAB).append(DOT);
		qryBuilder.append(RUTA_IN).append(PARENTESIS_FIN).append(AS).append(RUTA_IN).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_RUTA_ESTAB).append(DOT);
		qryBuilder.append(RUTA_OUT).append(PARENTESIS_FIN).append(AS).append(RUTA_OUT).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ALIAS_RUTA_ESTAB).append(DOT);
		qryBuilder.append(RUTA_REPOSITORIO).append(PARENTESIS_FIN).append(AS).append(RUTA_REPOSITORIO).append(EOL);
		
		qryBuilder.append(FROM).append(EOL).append(TAB);
		qryBuilder.append(TABLE_NAME).append(AS).append(ALIAS_TABLE).append(EOL).append(TAB);
		
		qryBuilder.append(INNER).append(CATALOGO_TIPO_ESTAB).append(AS).append(ALIAS_TIPO_ESTAB);
		qryBuilder.append(ON).append(ALIAS_TABLE).append(DOT).append(ID_TIPO_ESTAB).append(EQ).append(ALIAS_TIPO_ESTAB);
		qryBuilder.append(DOT).append(ID_TIPO_ESTAB).append(EOL).append(TAB);
		
		qryBuilder.append(INNER).append(TABLE_RUTA_ESTAB).append(AS).append(ALIAS_RUTA_ESTAB);
		qryBuilder.append(ON).append(ALIAS_TABLE).append(DOT).append(ID_RUTA_ESTAB).append(EQ).append(ALIAS_RUTA_ESTAB);
		qryBuilder.append(DOT).append(ID_RUTA_ESTAB).append(EOL).append(TAB);
		
		qryBuilder.append(INNER).append(TABLE_DOM_ESTAB).append(AS).append(ALIAS_DOM_ESTAB);
		qryBuilder.append(ON).append(ALIAS_TABLE).append(DOT).append(ID_DOM_ESTAB).append(EQ).append(ALIAS_DOM_ESTAB);
		qryBuilder.append(DOT).append(ID_DOM_ESTAB).append(EOL);
				
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ALIAS_TABLE).append(DOT).append(CLAVE).append(SET_PARAM);
		
		READ_BY_CLAVE = qryBuilder.toString();
		qryBuilder = clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB);

		qryBuilder.append(TAB).append(ALIAS_DOM_ESTAB).append(DOT).append(ID_DOM_ESTAB).append(EOL_);
		qryBuilder.append(TAB).append(DomicilioSql.ALIAS_ESTADO).append(DOT).append(DomicilioSql.ID_ESTADO).append(EOL_);
		qryBuilder.append(TAB).append(DomicilioSql.ALIAS_PAIS).append(DOT).append(DomicilioSql.ID_PAIS).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(ALIAS_DOM_ESTAB).append(DOT).append(DomicilioSql.CALLE);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(DomicilioSql.CALLE).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(ALIAS_DOM_ESTAB).append(DOT).append(DomicilioSql.NO_EXTERIOR);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(DomicilioSql.NO_EXTERIOR).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(ALIAS_DOM_ESTAB).append(DOT).append(DomicilioSql.NO_INTERIOR);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(DomicilioSql.NO_INTERIOR).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(ALIAS_DOM_ESTAB).append(DOT).append(DomicilioSql.COLONIA);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(DomicilioSql.COLONIA).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(ALIAS_DOM_ESTAB).append(DOT).append(DomicilioSql.MUNICIPIO);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(DomicilioSql.MUNICIPIO).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(ALIAS_DOM_ESTAB).append(DOT).append(DomicilioSql.CODIGO_POSTAL);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(DomicilioSql.CODIGO_POSTAL).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(ALIAS_DOM_ESTAB).append(DOT).append(LOCALIDAD);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(LOCALIDAD).append(EOL_);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(DomicilioSql.ALIAS_ESTADO).append(DOT).append(NOMBRE);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(DomicilioSql.AS_ESTADO).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(DomicilioSql.ALIAS_PAIS).append(DOT).append(NOMBRE);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(DomicilioSql.AS_PAIS).append(EOL);
		
		qryBuilder.append(FROM).append(EOL).append(TAB);
		qryBuilder.append(TABLE_DOM_ESTAB).append(AS).append(ALIAS_DOM_ESTAB).append(EOL).append(TAB);
		qryBuilder.append(INNER).append(DomicilioSql.TABLE_ESTADO).append(AS);
		qryBuilder.append(DomicilioSql.ALIAS_ESTADO).append(ON).append(DomicilioSql.ALIAS_ESTADO);
		qryBuilder.append(DOT).append(DomicilioSql.ID_ESTADO).append(EQ).append(ALIAS_DOM_ESTAB);
		qryBuilder.append(DOT).append(DomicilioSql.ID_ESTADO).append(EOL).append(TAB);
		
		qryBuilder.append(INNER).append(DomicilioSql.TABLE_PAIS).append(AS);
		qryBuilder.append(DomicilioSql.ALIAS_PAIS).append(ON).append(DomicilioSql.ALIAS_PAIS);
		qryBuilder.append(DOT).append(DomicilioSql.ID_PAIS).append(EQ).append(DomicilioSql.ALIAS_ESTADO);
		qryBuilder.append(DOT).append(DomicilioSql.ID_PAIS).append(EOL).append(TAB);
		
		qryBuilder.append(INNER).append(TABLE_NAME).append(AS).append(ALIAS_TABLE);
		qryBuilder.append(ON).append(ALIAS_DOM_ESTAB);
		qryBuilder.append(DOT).append(ID_DOM_ESTAB).append(EQ).append(ALIAS_TABLE);
		qryBuilder.append(DOT).append(ID_DOM_ESTAB).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(ALIAS_TABLE).append(DOT).append(ID_ESTABLECIMIENTO).append(SET_PARAM);
		
		READ_LUGAR_EXP = qryBuilder.toString();
		qryBuilder = clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(ALL).append(FROM).append(EOL);
		qryBuilder.append(TAB).append(TABLE_NAME);
		
		
		READ_ALL = qryBuilder.toString();
		qryBuilder = clearAndReuseStringBuilder(qryBuilder);
		
		// ----- Consulta del establecimiento coo ids ----- //
		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(ID_ESTABLECIMIENTO).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(CLAVE)
		.append(PARENTESIS_FIN).append(AS).append(CLAVE).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(NOMBRE)
		.append(PARENTESIS_FIN).append(AS).append(NOMBRE).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(PASSWORD)
		.append(PARENTESIS_FIN).append(AS).append(PASSWORD).append(EOL_);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ID_DOM_ESTAB)
		.append(PARENTESIS_FIN).append(AS).append(ID_DOM_ESTAB).append(EOL_);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(ID_RUTA_ESTAB)
		.append(PARENTESIS_FIN).append(AS).append(ID_RUTA_ESTAB).append(EOL);
		
		qryBuilder.append(FROM).append(EOL).append(TAB);
		qryBuilder.append(TABLE_NAME).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ID_ESTABLECIMIENTO).append(SET_PARAM);
		
		READ_ALL_WITH_IDS = qryBuilder.toString();
		qryBuilder = clearAndReuseStringBuilder(qryBuilder);
		
		//---- Actualizar-----//
		/*
		UPDATE
		t_establecimiento
		SET
		clave = ?,
		nombre = ?,
		password = ?
		WHERE
		id_establecimiento = ?
		*/
		qryBuilder.append(UPDATE).append(EOL).append(TAB).append(TABLE_NAME);
		qryBuilder.append(EOL).append(SET).append(EOL);
		
		qryBuilder.append(TAB).append(CLAVE).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(NOMBRE).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(PASSWORD).append(SET_PARAM).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ID_ESTABLECIMIENTO).append(SET_PARAM);
		
		UPDATE_ESTABLECIMIENTO = qryBuilder.toString();		
				
	}	
}
