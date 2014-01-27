package com.magnabyte.cfdi.portal.dao.establecimiento.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa las variables con las consultas de acceso a datos
 * de ruta de establecimiento
 */
public class RutaRepositorioSql extends GenericSql {

	public static final String TABLE_NAME = "t_ruta_establecimiento";
	public static final String ALIAS_TABLE = "tre";

	public static final String ID_RUTA_ESTAB = "id_ruta_establecimiento";
	public static final String RUTA_REPOSITORIO = "ruta_repo";
	public static final String RUTA_IN = "ruta_in";
	public static final String RUTA_OUT = "ruta_out";
	public static final String RUTA_INPROC = "ruta_inproc";

	public static final String FIND_BY_ID;
	public static final String UPDATE_RUTA;

	static {

		StringBuilder qryBuilder = new StringBuilder();

		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(ALL).append(FROM).append(EOL);
		qryBuilder.append(TAB).append(TABLE_NAME).append(EOL);

		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ID_RUTA_ESTAB).append(SET_PARAM);

		FIND_BY_ID = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);

		qryBuilder.append(UPDATE).append(EOL).append(TAB);
		qryBuilder.append(TABLE_NAME).append(EOL).append(SET).append(EOL);
		
//		qryBuilder.append(TAB).append(ID_RUTA_ESTAB).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(RUTA_REPOSITORIO).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(RUTA_IN).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(RUTA_OUT).append(SET_PARAM).append(EOL_);
		qryBuilder.append(TAB).append(RUTA_INPROC).append(SET_PARAM).append(EOL);
		
		qryBuilder.append(WHERE).append(EOL).append(TAB);
		qryBuilder.append(ID_RUTA_ESTAB).append(SET_PARAM);
		
		UPDATE_RUTA = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);

	}

}
