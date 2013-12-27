package com.magnabyte.cfdi.portal.dao.documento.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.documento.DocumentoDao;
import com.magnabyte.cfdi.portal.dao.documento.sql.DocumentoSql;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.exception.PortalException;

@Repository("documentoDao")
public class DocumentoDaoImpl extends GenericJdbcDao implements DocumentoDao {

	@Override
	public void save(Documento documento) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(getJdbcTemplate());
			simpleInsert.setTableName(DocumentoSql.TABLE_NAME);
			simpleInsert.setGeneratedKeyName(DocumentoSql.ID_DOCUMENTO);
			documento.setId(simpleInsert.executeAndReturnKey(getParameters(documento)).intValue());
		} catch (DataAccessException ex) {			
			logger.debug("No se pudo registrar el Documento en la base de datos.", ex);
			throw new PortalException("No se pudo registrar el Documento en la base de datos.", ex);
		}
	}

	private MapSqlParameterSource getParameters(Documento documento) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(DocumentoSql.ID_ESTABLECIMIENTO, documento.getEstablecimiento().getId());
		params.addValue(DocumentoSql.ID_CLIENTE, documento.getCliente().getId());
		if(documento instanceof DocumentoSucursal) {
			params.addValue(DocumentoSql.ID_TICKET, ((DocumentoSucursal) documento)
					.getTicket().getTransaccion().getTransaccionHeader().getIdTicket());
		} else if(documento instanceof DocumentoCorporativo){
			params.addValue(DocumentoSql.FOLIO_SAP, ((DocumentoCorporativo) documento).getFolioSap());
		}
		params.addValue(DocumentoSql.FECHA_DOCUMENTO, documento.getFechaFacturacion());
		params.addValue(DocumentoSql.SUBTOTAL, documento.getComprobante().getSubTotal());
		params.addValue(DocumentoSql.IVA, documento.getComprobante().getImpuestos());
		params.addValue(DocumentoSql.TOTAL, documento.getComprobante().getTotal());
		params.addValue(DocumentoSql.STATUS, true);
		
		return params;
	}

}