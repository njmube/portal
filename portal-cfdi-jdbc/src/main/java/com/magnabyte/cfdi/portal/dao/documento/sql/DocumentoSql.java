package com.magnabyte.cfdi.portal.dao.documento.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

public class DocumentoSql extends GenericSql {
	
	public static final String TABLE_NAME = "t_documento";
	public static final String TABLE_DOC_FOLIO = "t_documento_folio";
	public static final String TABLE_DOC_CFDI = "t_documento_cfdi";
	public static final String TABLE_ESTAB_SERIE = "t_establecimiento_serie";
	public static final String TABLE_DOC_PEND = "t_documento_pendiente";
	
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
	public static final String SELLO_CFDI = "sello_cfdi";
	public static final String CADENA = "cadena";
	public static final String SELLO_EMISOR = "sello_emisor";
	public static final String UUID = "uuid";
	public static final String FECHA_HORA = "fecha_hora";

	public static final String ID_ESTADO_DOC = "id_estado_documento";
	
	public static final String READ_SERIE_FOLIO;
	public static final String UPDATE_FOLIO_SERIE;
	public static final String READ_ACUSE_PEND;

	
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
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select *").append(EOL);
		qryBuilder.append("from t_documento_pendiente as tdp").append(EOL);
		qryBuilder.append("inner join t_documento_cfdi as tdc on tdp.id_documento = tdc.id_documento").append(EOL);
		qryBuilder.append("where tdp.id_estado_documento = 3").append(EOL);
		
		READ_ACUSE_PEND = qryBuilder.toString();
	}
	
}
