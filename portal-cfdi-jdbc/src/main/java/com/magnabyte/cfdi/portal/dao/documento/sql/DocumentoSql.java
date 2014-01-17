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
	public static final String ID_DOMICILIO_CLIENTE = "id_domicilio_cliente";
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
	public static final String XML_FILE = "xml_file";
	
	public static final String READ_DOCUMENTO_BY_ID;
	public static final String READ_DOCUMENTOS_FACTURADOS;
	public static final String READ_DOCUMENTOS_PENDIENTES;
	public static final String READ_DOCUMENTO_RUTA;
	public static final String READ_NEXT_SERIE_FOLIO;
	public static final String UPDATE_FOLIO_SERIE;
	public static final String READ_ACUSE_PEND;
	public static final String UPDATE_DOC_CTE;
	public static final String READ_SERIE_FOLIO_DOC;
	public static final String DELETE_DOCUMENTO_PENDIENTE;
	public static final String READ_DOC_BY_SERIE_FOLIO;
	
	static {
		StringBuilder qryBuilder = new StringBuilder();
		
		qryBuilder.append("select dbo.trim(serie) as serie, dbo.trim(folio_consecutivo) as folio_consecutivo").append(EOL);  
		qryBuilder.append("from t_establecimiento_serie").append(EOL); 
		qryBuilder.append("where id_establecimiento = ?").append(EOL);
		qryBuilder.append("and id_tipo_documento = ?").append(EOL);
		qryBuilder.append("and status = 'A'");
		
		READ_NEXT_SERIE_FOLIO = qryBuilder.toString();
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select dbo.trim(serie) as serie, dbo.trim(folio) as folio").append(EOL);  
		qryBuilder.append("from t_documento_folio").append(EOL); 
		qryBuilder.append("where id_documento = ?");
		
		READ_SERIE_FOLIO_DOC = qryBuilder.toString();
		
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
		qryBuilder.append("where tdp.id_estado_documento = ?").append(EOL);
		
		READ_ACUSE_PEND = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB).append("doc.id_documento, doc.id_establecimiento").append(EOL);
		qryBuilder.append(FROM).append(EOL).append("t_documento as doc").append(EOL).append(TAB);		
		qryBuilder.append(INNER).append("t_cliente as cte on doc.id_cliente = cte.id_cliente").append(EOL).append(TAB);
		qryBuilder.append(INNER).append("t_establecimiento as estab on doc.id_establecimiento = estab.id_establecimiento").append(EOL).append(TAB);
		qryBuilder.append(WHERE).append(EOL).append(TAB).append("cte.rfc like ?");
		
		
		READ_DOCUMENTO_RUTA = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB).append("folio.id_documento, folio.serie, folio.folio, folio.id_tipo_documento").append(EOL);
		qryBuilder.append(FROM).append(EOL).append("t_documento_cfdi as cfdi").append(EOL).append(TAB);
		qryBuilder.append(INNER).append(EOL).append(TAB).append("t_documento as doc on doc.id_documento = cfdi.id_documento").append(EOL).append(TAB);
		qryBuilder.append(INNER).append(EOL).append(TAB).append("t_documento_folio as folio on cfdi.id_documento = folio.id_documento").append(EOL);
		qryBuilder.append(WHERE).append(EOL).append(TAB).append("cfdi.id_documento in (:idDocumentos)");
		
		READ_DOCUMENTOS_FACTURADOS = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB).append("id_documento, id_establecimiento, status_doc, fecha_doc, id_cliente, subtotal,");
		qryBuilder.append(" iva, total_doc, dbo.TRIM(folio_sap) as folio_sap, total_descuento, id_domicilio_cliente, xml_file").append(EOL);
		qryBuilder.append(FROM).append(EOL).append(TAB).append("t_documento").append(EOL);
		qryBuilder.append(WHERE).append(EOL).append(TAB).append("id_documento = ?");
		
		READ_DOCUMENTO_BY_ID = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB).append("id_documento, serie, folio, id_tipo_documento, id_establecimiento").append(EOL);
		qryBuilder.append(FROM).append(EOL).append(TAB).append("t_documento_pendiente").append(EOL);
		qryBuilder.append(WHERE).append(EOL).append(TAB).append("id_estado_documento = ?");
		
		READ_DOCUMENTOS_PENDIENTES = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("update t_documento set id_cliente = ? where id_documento = ?");
		
		UPDATE_DOC_CTE = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(DELETE).append(" " + FROM).append(EOL);
		qryBuilder.append(TABLE_DOC_PEND).append(EOL).append(WHERE);
		qryBuilder.append(EOL).append(TAB).append(ID_DOCUMENTO).append(SET_PARAM);
		
		DELETE_DOCUMENTO_PENDIENTE = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select doc.id_documento "); 
		qryBuilder.append("from t_documento as doc "); 
		qryBuilder.append("inner join t_documento_folio as docfolio "); 
		qryBuilder.append("on doc.id_documento = docfolio.id_documento ");
		qryBuilder.append("where docfolio.serie = ? ");
		qryBuilder.append("and docfolio.folio = ? ");
		
		READ_DOC_BY_SERIE_FOLIO = qryBuilder.toString();
	}
	
}
