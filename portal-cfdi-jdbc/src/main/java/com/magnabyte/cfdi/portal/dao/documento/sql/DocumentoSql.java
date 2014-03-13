package com.magnabyte.cfdi.portal.dao.documento.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa las variables con las consultas de acceso a datos
 * de documento
 */
public class DocumentoSql extends GenericSql {
	
	public static final String TABLE_NAME = "t_documento";
	public static final String TABLE_DOC_FOLIO = "t_documento_folio";
	public static final String TABLE_DOC_CFDI = "t_documento_cfdi";
	public static final String TABLE_ESTAB_SERIE = "t_establecimiento_serie";
	public static final String TABLE_DOC_PEND = "t_documento_pendiente";
	
	public static final String ID_DOCUMENTO = "id_documento";
	public static final String ID_DOCUMENTO_ORIGEN = "id_documento_origen";
	public static final String ID_ESTABLECIMIENTO = "id_establecimiento";
	public static final String ID_CLIENTE = "id_cliente";
	public static final String ID_DOMICILIO_CLIENTE = "id_domicilio_cliente";
	public static final String ID_TICKET = "id_ticket";
	public static final String FOLIO_SAP = "folio_sap";
	public static final String NIT = "nit";
	public static final String FECHA_DOCUMENTO = "fecha_doc";
	public static final String SUBTOTAL = "subtotal";
	public static final String IVA = "iva";
	public static final String TOTAL = "total_doc";
	public static final String TOTAL_DESCUENTO = "total_descuento";
	public static final String ID_STATUS = "id_status_doc";

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
	public static final String DELETE_DOCUMENTO_PENDIENTE_BY_STATUS;
	public static final String READ_DOCFOLIO_BY_SERIE_FOLIO;
	public static final String UPDATE_DOC_XML_FILE;
	public static final String UPDATE_DOC_STATUS;
	public static final String READ_DOC_CFDI;
	public static final String SAVE_ACUSE;
	public static final String READ_DOC_BY_ID_AND_ESTADO;
	public static final String READ_DOCFOLIO_BY_ID;
	public static final String READ_CLIENTE_FROM_DOC;
	public static final String READ_BY_SERIE;
	public static final String READ_BY_SERIE_FOLIO_IMPORTE;
	public static final String UPDATE_DOC_PENDIENTE_STATUS;
	
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
		qryBuilder.append(WHERE).append(EOL).append(TAB).append("cte.rfc like ?").append(EOL).append(TAB);
		qryBuilder.append("and convert(varchar(10), fecha_doc, 120) >= ?").append(EOL).append(TAB);
		qryBuilder.append("and convert(varchar(10), fecha_doc, 120) <= ?").append(EOL).append(TAB);
		qryBuilder.append("and id_status_doc = ?").append(EOL).append(TAB);
		
		READ_DOCUMENTO_RUTA = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB).append("folio.id_documento, folio.serie, folio.folio, folio.id_tipo_documento, doc.id_domicilio_cliente").append(EOL);
		qryBuilder.append(FROM).append(EOL).append("t_documento_cfdi as cfdi").append(EOL).append(TAB);
		qryBuilder.append(INNER).append(EOL).append(TAB).append("t_documento as doc on doc.id_documento = cfdi.id_documento").append(EOL).append(TAB);
		qryBuilder.append(INNER).append(EOL).append(TAB).append("t_documento_folio as folio on cfdi.id_documento = folio.id_documento").append(EOL);
		qryBuilder.append(WHERE).append(EOL).append(TAB).append("cfdi.id_documento in (:idDocumentos)").append(EOL);
		qryBuilder.append("and folio.id_tipo_documento = :idTipoDocumento").append(EOL);
		qryBuilder.append("order by folio.id_documento");
		READ_DOCUMENTOS_FACTURADOS = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append(SELECT).append(EOL).append(TAB).append("id_documento, id_documento_origen,id_establecimiento, id_status_doc, fecha_doc, id_cliente, subtotal,");
		qryBuilder.append(" iva, total_doc, dbo.TRIM(folio_sap) as folio_sap, dbo.TRIM(nit) as nit, total_descuento, id_domicilio_cliente, xml_file").append(EOL);
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
		qryBuilder.append(EOL).append(TAB).append(AND).append(EOL);
		qryBuilder.append(EOL).append(TAB).append(ID_ESTADO_DOC).append(SET_PARAM);
		
		DELETE_DOCUMENTO_PENDIENTE_BY_STATUS = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select doc.id_documento, docfolio.id_tipo_documento "); 
		qryBuilder.append("from t_documento as doc "); 
		qryBuilder.append("inner join t_documento_folio as docfolio "); 
		qryBuilder.append("on doc.id_documento = docfolio.id_documento ");
		qryBuilder.append("where docfolio.serie = ? ");
		qryBuilder.append("and docfolio.folio = ? ");
		
		READ_DOCFOLIO_BY_SERIE_FOLIO = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("update t_documento set xml_file = ? where id_documento = ?");
		
		UPDATE_DOC_XML_FILE = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("update t_documento set id_status_doc = ? where id_documento = ?");
		
		UPDATE_DOC_STATUS = qryBuilder.toString();
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select id_documento, dbo.TRIM(sello_emisor) as sello_emisor,").append(EOL);
		qryBuilder.append("dbo.TRIM(sello_cfdi) as sello_cfdi, dbo.TRIM(uuid) as uuid,").append(EOL);
		qryBuilder.append("dbo.TRIM(cadena) as cadena, fecha_hora").append(EOL);
		qryBuilder.append("from t_documento_cfdi").append(EOL);
		qryBuilder.append("where id_documento = ?").append(EOL);
		
		READ_DOC_CFDI = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("update t_documento set xml_acuse_file = ? where id_documento = ?");
		
		SAVE_ACUSE = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select id_documento").append(EOL);
		qryBuilder.append(FROM).append(EOL).append(TAB).append("t_documento_pendiente").append(EOL);
		qryBuilder.append(WHERE).append(EOL).append(TAB).append("id_documento = ?").append(EOL);
		qryBuilder.append(AND).append(EOL).append(TAB).append("id_estado_documento = ?");
		
		READ_DOC_BY_ID_AND_ESTADO = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("update t_documento_pendiente set id_estado_documento = ?").append(EOL);
		qryBuilder.append("where id_documento = ?").append(EOL);
		
		UPDATE_DOC_PENDIENTE_STATUS = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select doc.id_documento, docfolio.id_tipo_documento, ");
		qryBuilder.append("dbo.trim(serie) as serie, dbo.trim(folio) as folio ");
		qryBuilder.append("from t_documento as doc ");
		qryBuilder.append("inner join t_documento_folio as docfolio "); 
		qryBuilder.append("on doc.id_documento = docfolio.id_documento ");
		qryBuilder.append("where doc.id_documento = ?");
		
		READ_DOCFOLIO_BY_ID = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select cte.id_cliente, dbo.TRIM(cte.rfc) as rfc, dbo.TRIM(cte.nombre) as nombre, doc.id_domicilio_cliente").append(EOL);
		qryBuilder.append("from t_documento as doc").append(EOL);
		qryBuilder.append("inner join t_cliente as cte on doc.id_cliente = cte.id_cliente").append(EOL);
		qryBuilder.append("where doc.id_documento = ?").append(EOL);
		
		READ_CLIENTE_FROM_DOC = qryBuilder.toString();
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select te.id_establecimiento, te.id_tipo_establecimiento, dbo.TRIM(cte.rol) as rol, tes.id_tipo_documento").append(EOL);
		qryBuilder.append("from t_establecimiento_serie as tes inner join t_establecimiento as te").append(EOL);
		qryBuilder.append("on tes.id_establecimiento = te.id_establecimiento").append(EOL);
		qryBuilder.append("inner join c_tipo_establecimiento as cte").append(EOL);
		qryBuilder.append("on te.id_tipo_establecimiento = cte.id_tipo_establecimiento").append(EOL); 
		qryBuilder.append("where tes.serie = ?").append(EOL);
		
		READ_BY_SERIE = qryBuilder.toString();
		
		clearAndReuseStringBuilder(qryBuilder);
		
		qryBuilder.append("select td.id_documento, td.id_cliente, td.id_domicilio_cliente, td.id_status_doc, td.xml_file").append(EOL);
		qryBuilder.append("from t_documento as td inner join t_documento_folio as tdf").append(EOL);
		qryBuilder.append("on td.id_documento = tdf.id_documento").append(EOL);
		qryBuilder.append("inner join t_documento_cfdi as tdc").append(EOL);
		qryBuilder.append("on td.id_documento = tdc.id_documento").append(EOL);
		qryBuilder.append("where tdf.serie = ?").append(EOL);
		qryBuilder.append("and tdf.folio = ?").append(EOL);
		qryBuilder.append("and td.total_doc = ?").append(EOL);
		
		READ_BY_SERIE_FOLIO_IMPORTE = qryBuilder.toString();
				
	}
}
