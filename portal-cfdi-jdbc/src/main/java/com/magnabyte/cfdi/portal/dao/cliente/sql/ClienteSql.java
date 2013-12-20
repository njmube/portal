package com.magnabyte.cfdi.portal.dao.cliente.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

public class ClienteSql extends GenericSql {
	
	public static final String TABLE_NAME = "t_cliente";
	
	public static String RFC = "rfc";
	public static String NOMBRE = "nombre";
	public static String ID_CLIENTE = "id_cliente";
	
	public static final String FIND_BY_ID;
	public static final String FIND_BY_NAME_RFC;
	public static final String UPDATE_CLIENETE;

	static {
		StringBuilder qryBuilder = new StringBuilder();
		
		qryBuilder.append("select * from t_cliente where rfc like ? or nombre like ?");
		
		FIND_BY_NAME_RFC = qryBuilder.toString();
		qryBuilder = clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(ALL).append(EOL).append(FROM);
		qryBuilder.append(EOL).append(TAB).append(TABLE_NAME).append(EOL);
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ID_CLIENTE).append(SET_PARAM);
		
		FIND_BY_ID = qryBuilder.toString();
		qryBuilder = clearAndReuseStringBuilder(qryBuilder);
		
		
		qryBuilder.append(UPDATE).append(EOL).append(TAB).append(TABLE_NAME);
		qryBuilder.append(EOL).append(SET).append(EOL);
				
		qryBuilder.append(TAB).append(NOMBRE).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(RFC).append(SET_PARAM).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ID_CLIENTE).append(SET_PARAM);
		
		UPDATE_CLIENETE = qryBuilder.toString();
		qryBuilder = clearAndReuseStringBuilder(qryBuilder);
	}
}
