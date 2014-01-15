package com.magnabyte.cfdi.portal.dao.documento.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

public class TicketSql extends GenericSql {

	public static final String TABLE_NAME = "t_ticket";
	
	public static final String ID_TICKET = "id_ticket";
	public static final String ID_STATUS = "id_status_ticket";
	public static final String FECHA = "fecha_ticket";
	public static final String NO_TICKET = "no_ticket";
	public static final String NO_CAJA = "no_caja";
	public static final String FILENAME = "nombre_archivo";

	public static final String READ;
	public static final String READ_BY_STATUS;
	public static final String UPDATE_STATUS;
	public static final String READ_ART_SIN_PRECIO;
	public static final String READ_ID_DOC_BY_TICKET;
	public static final String READ_FACTURADOS_DIA;
	public static final String SAVE_TICKETS_CIERRE;
	public static final String READ_PROCESADO;
	
	static {
		StringBuilder qryBuilder = new StringBuilder();
		
		qryBuilder.append("select * from t_ticket where no_ticket = ? and id_establecimiento = ? and no_caja = ?");
		
		READ = qryBuilder.toString();
	
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(READ).append(EOL);
		qryBuilder.append("and id_status_ticket = ?");
		
		READ_BY_STATUS = qryBuilder.toString();
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("update t_ticket set id_status_ticket = ? where id_ticket = ?");
		
		UPDATE_STATUS = qryBuilder.toString();
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select dbo.TRIM(clave) as clave from t_articulos_sin_precio");
		
		READ_ART_SIN_PRECIO = qryBuilder.toString();
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select id_documento from t_ticket where no_ticket = ? and id_establecimiento = ? and no_caja = ?").append(EOL);
		qryBuilder.append("and id_status_ticket = ?");
		
		READ_ID_DOC_BY_TICKET = qryBuilder.toString();
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select dbo.TRIM(nombre_archivo) as nombre_archivo from t_ticket where convert(varchar(10), fecha_ticket, 120) = ?");
		
		READ_FACTURADOS_DIA = qryBuilder.toString();
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("insert into t_ticket (no_ticket, id_establecimiento, no_caja, fecha_ticket, id_status_ticket, id_documento, nombre_archivo) ");
		qryBuilder.append("values (?, ?, ?, ?, ?, ?, ?)");
		
		SAVE_TICKETS_CIERRE = qryBuilder.toString();
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select count(*) from t_ticket where nombre_archivo = ? and (id_status_ticket = ? or id_status_ticket = ?)");
		
		READ_PROCESADO = qryBuilder.toString();
	}
}
