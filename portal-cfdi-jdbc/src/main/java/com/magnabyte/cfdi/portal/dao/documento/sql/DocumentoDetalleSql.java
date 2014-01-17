package com.magnabyte.cfdi.portal.dao.documento.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

public class DocumentoDetalleSql extends GenericSql {

public static final String TABLE_NAME = "t_documento_detalle";
	
	public static final String ID_DOCUMENTO_DETALLE = "id_documento_detalle";
	
	public static final String PRECIO_TOTAL = "precio_total";
	public static final String PRECIO_UNITARIO = "precio_unitario";
	public static final String DESCRIPCION = "descripcion";
	public static final String UNIDAD = "unidad";
	public static final String CANTIDAD = "cantidad";
	
	public static final String INSERT_DETALLE_DOC;
	public static final String READ_DOC_DETALLE;

	
	static {
		StringBuilder queryBuilder = new StringBuilder();
		
		queryBuilder.append(INSERT).append(EOL).append(TAB);
		queryBuilder.append(TABLE_NAME).append(EOL).append(TAB).append(PARENTESIS_INIT);
		queryBuilder.append(DocumentoSql.ID_DOCUMENTO).append(EOL_).append(TAB);
		queryBuilder.append(PRECIO_TOTAL).append(EOL_).append(TAB);
		queryBuilder.append(PRECIO_UNITARIO).append(EOL_).append(TAB);
		queryBuilder.append(DESCRIPCION).append(EOL_).append(TAB);
		queryBuilder.append(UNIDAD).append(EOL_).append(TAB);
		queryBuilder.append(CANTIDAD);
		queryBuilder.append(PARENTESIS_FIN).append(EOL);
		queryBuilder.append(VALUES).append(EOL).append(TAB);
		queryBuilder.append(PARENTESIS_INIT);
		queryBuilder.append(PARAM).append(PARAM).append(PARAM);
		queryBuilder.append(PARAM).append(PARAM).append(LAST_PARAM);
		queryBuilder.append(PARENTESIS_FIN);
		
		INSERT_DETALLE_DOC = queryBuilder.toString();
		clearAndReuseStringBuilder(queryBuilder);
		
		queryBuilder.append(SELECT).append(EOL).append(TAB);
		queryBuilder.append(CANTIDAD).append(EOL_);
		queryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(UNIDAD);
		queryBuilder.append(PARENTESIS_FIN).append(AS).append(UNIDAD).append(EOL_);
		queryBuilder.append(TAB).append(TRIM).append(PARENTESIS_INIT).append(DESCRIPCION);
		queryBuilder.append(PARENTESIS_FIN).append(AS).append(DESCRIPCION).append(EOL_);
		queryBuilder.append(TAB).append(PRECIO_UNITARIO).append(EOL_);
		queryBuilder.append(TAB).append(PRECIO_TOTAL).append(EOL);
		
		queryBuilder.append(FROM).append(EOL).append(TAB);
		queryBuilder.append(TABLE_NAME).append(EOL);
		
		queryBuilder.append(WHERE).append(EOL).append(TAB);
		queryBuilder.append(DocumentoSql.ID_DOCUMENTO).append(SET_PARAM);
		
		
		
		READ_DOC_DETALLE = queryBuilder.toString();
		clearAndReuseStringBuilder(queryBuilder);
	}
}
