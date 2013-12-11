package com.magnabyte.cfdi.portal.dao.cliente.sql;

public class ClienteSql {
	
	public static final String FIND_BY_NAME_RFC;

	static {
		StringBuilder qryBuilder = new StringBuilder();
		
		qryBuilder.append("select * from t_cliente where rfc like ? or nombre like ?");
		
		FIND_BY_NAME_RFC = qryBuilder.toString();
	}
}
