package com.magnabyte.cfdi.portal.dao.commons.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;

public class UsuarioSql extends GenericSql {

	public static final String TABLE_NAME = "t_usuario";
	
	public static final String ID_USUARIO = "id_usuario";
	public static final String ID_STATUS = "id_estatus";
	public static final String USUARIO = "usuario";
	public static final String PASSWORD = "password";
	
	public static final String GET_BY_ESTABLECIMIENTO;
	public static final String GET_ALL;
	public static final String UPDATE_USUARIO;

	
	static {
		StringBuilder qryBuilder = new StringBuilder();
		
		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(ID_USUARIO).append(EOL_).append(TAB);
		qryBuilder.append(ID_STATUS).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(USUARIO);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(USUARIO).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(PASSWORD);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(PASSWORD).append(EOL_).append(TAB);
		qryBuilder.append(EstablecimientoSql.ID_ESTABLECIMIENTO).append(EOL);
		
		qryBuilder.append(FROM).append(EOL).append(TAB);
		qryBuilder.append(TABLE_NAME).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(EstablecimientoSql.ID_ESTABLECIMIENTO).append(SET_PARAM);
		qryBuilder.append(EOL).append(AND).append(EOL).append(TAB);
		qryBuilder.append(USUARIO).append(SET_PARAM);
		
		
		GET_BY_ESTABLECIMIENTO = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(UPDATE).append(EOL).append(TAB).append(TABLE_NAME);
		qryBuilder.append(EOL).append(SET).append(EOL);
				
		qryBuilder.append(TAB).append(USUARIO).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(PASSWORD).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(EstablecimientoSql.ID_ESTABLECIMIENTO).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(ID_STATUS).append(SET_PARAM).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ID_USUARIO).append(SET_PARAM);
		
		UPDATE_USUARIO = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(ID_USUARIO).append(EOL_).append(TAB);
		qryBuilder.append(ID_STATUS).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(USUARIO);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(USUARIO).append(EOL_).append(TAB);
		qryBuilder.append(TRIM).append(PARENTESIS_INIT).append(PASSWORD);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(PASSWORD).append(EOL_).append(TAB);
		qryBuilder.append(EstablecimientoSql.ID_ESTABLECIMIENTO).append(EOL);
		
		qryBuilder.append(FROM).append(EOL).append(TAB);
		qryBuilder.append(TABLE_NAME);
		
		GET_ALL = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
	}
}
