package com.magnabyte.cfdi.portal.dao.establecimiento.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

public class EstablecimientoSql extends GenericSql {
	public static final String FIND_BY_CLAVE;
	
	static {
		StringBuilder qryBuilder = new StringBuilder();
		qryBuilder.append("select id_establecimiento, TRIM(clave) AS clave, nombre, TRIM(password) AS password, ruta_repositorio from t_establecimiento where clave = ?");
		FIND_BY_CLAVE = qryBuilder.toString();
	}
}
