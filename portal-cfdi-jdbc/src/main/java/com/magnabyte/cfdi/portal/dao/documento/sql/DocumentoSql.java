package com.magnabyte.cfdi.portal.dao.documento.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

public class DocumentoSql extends GenericSql {
	
	public static final String TABLE_NAME = "t_documento";
	public static final String TABLE_ESTAB_SERIE = "t_establecimiento_serie";
	
	public static final String ID_DOCUMENTO = "id_documento";
	public static final String ID_ESTABLECIMIENTO = "id_establecimiento";
	public static final String ID_CLIENTE = "id_cliente";
	public static final String ID_TICKET = "id_ticket";
	public static final String FOLIO_SAP = "folio_sap";
	public static final String FECHA_DOCUMENTO = "fecha_doc";
	public static final String SUBTOTAL = "subtotal";
	public static final String IVA = "iva";
	public static final String TOTAL = "total_doc";
	public static final String TOTAL_DESCUENTO = "total_descuento";
	public static final String STATUS = "status_doc";

	public static final String SERIE = "serie";
	public static final String FOLIO = "folio";
	public static final String FOLIO_CONSECUTIVO = "folio_consecutivo";
	public static final String ID_TIPO_DOCUMENTO = "id_tipo_documento";

	public static final String READ_SERIE_FOLIO;
	public static final String UPDATE_FOLIO_SERIE;

	
	static {
		StringBuilder qryBuilder = new StringBuilder();
		
		qryBuilder.append("select dbo.trim(serie) as serie, dbo.trim(folio_consecutivo) as folio_consecutivo").append(EOL);  
		qryBuilder.append("from t_establecimiento_serie").append(EOL); 
		qryBuilder.append("where id_establecimiento = ?").append(EOL);
		qryBuilder.append("and id_tipo_documento = ?").append(EOL);
		qryBuilder.append("and status = 'A'");
		
		READ_SERIE_FOLIO = qryBuilder.toString();
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("update t_establecimiento_serie set folio_consecutivo = ?").append(EOL);
		qryBuilder.append("where id_establecimiento = ?").append(EOL);
		qryBuilder.append("and id_tipo_documento = ?").append(EOL);
		qryBuilder.append("and status = 'A'");
		
		UPDATE_FOLIO_SERIE = qryBuilder.toString();
	}
	
}
