package com.magnabyte.cfdi.portal.dao.documento.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

public class TicketSql extends GenericSql {

	public static final String TABLE_NAME = "t_ticket";
	
	public static final String ID_TICKET = "id_ticket";
	public static final String ID_STATUS = "id_status_ticket";
	public static final String FECHA = "fecha_ticket";
	public static final String NO_TICKET = "no_ticket";
	public static final String NO_CAJA = "no_caja";

	public static final String READ;
	public static final String UPDATE_FACTURADO;
	public static final String READ_ART_SIN_PRECIO;

	
	static {
		StringBuilder qryBuilder = new StringBuilder();
		
		qryBuilder.append("select * from t_ticket where no_ticket = ? and id_establecimiento = ? and no_caja = ?");
		
		READ = qryBuilder.toString();
	
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("update t_ticket set id_status_ticket = ? where id_ticket = ?");
		
		UPDATE_FACTURADO = qryBuilder.toString();
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select dbo.TRIM(clave) as clave from t_articulos_sin_precio");
		
		READ_ART_SIN_PRECIO = qryBuilder.toString();
	}
}
