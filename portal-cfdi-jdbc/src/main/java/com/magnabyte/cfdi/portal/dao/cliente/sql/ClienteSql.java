package com.magnabyte.cfdi.portal.dao.cliente.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

public class ClienteSql extends GenericSql {
	
	public static final String TABLE_NAME = "t_cliente";
	public static final String GENERIC_RFC = "\'XEXX010101000\'";
	
	public static String RFC = "rfc";
	public static String NOMBRE = "nombre";
	public static String ID_CLIENTE = "id_cliente";
	public static String TIPO = "tipo_persona";
	
	public static final String GET_ALL;
	public static final String FIND_BY_ID;
	public static final String FIND_BY_NAME_RFC;
	public static final String UPDATE_CLIENTE;
	public static final String READ_BY_NAME_RFC;
	public static final String READ_BY_RFC;
	public static final String READ_BY_NAME;

	static {
		StringBuilder qryBuilder = new StringBuilder();
		
		qryBuilder.append(SELECT).append(EOL).append(TAB).append(ALL).append(EOL);
		qryBuilder.append(FROM).append(EOL).append(TAB).append(TABLE_NAME).append(EOL);
		qryBuilder.append(WHERE).append(EOL).append(TAB).append(RFC).append(DIF).append(GENERIC_RFC);
		
		GET_ALL = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select * from t_cliente where (rfc like ? OR nombre like ?) and rfc != "+ GENERIC_RFC);
		
		FIND_BY_NAME_RFC = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select * from t_cliente where rfc = ? AND nombre = ?");
		
		READ_BY_NAME_RFC = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select * from t_cliente where rfc = ?");
		
		READ_BY_RFC = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select * from t_cliente where nombre = ?");
		
		READ_BY_NAME = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(ID_CLIENTE).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(NOMBRE);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(NOMBRE).append(EOL_);
		qryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(RFC);
		qryBuilder.append(PARENTESIS_FIN).append(AS).append(RFC).append(EOL_);
		qryBuilder.append(TAB).append(TIPO).append(EOL);
		qryBuilder.append(FROM).append(EOL);
		qryBuilder.append(TAB).append(TABLE_NAME).append(EOL);
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ID_CLIENTE).append(SET_PARAM);
		
		FIND_BY_ID = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		
		qryBuilder.append(UPDATE).append(EOL).append(TAB).append(TABLE_NAME);
		qryBuilder.append(EOL).append(SET).append(EOL);
				
		qryBuilder.append(TAB).append(NOMBRE).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(RFC).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(TIPO).append(SET_PARAM).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ID_CLIENTE).append(SET_PARAM);
		
		UPDATE_CLIENTE = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
	}
}
