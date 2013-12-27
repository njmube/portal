package com.magnabyte.cfdi.portal.dao.documento.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.documento.DocumentoSerieDao;
import com.magnabyte.cfdi.portal.dao.documento.sql.DocumentoSql;
import com.magnabyte.cfdi.portal.model.documento.Documento;

@Repository("documentoSerieDao")
public class DocumentoSerieDaoImpl extends GenericJdbcDao implements
		DocumentoSerieDao {

	private static final Logger logger = LoggerFactory.getLogger(DocumentoSerieDaoImpl.class);
	
	@Override
	public Map<String, Object> readSerieAndFolio(Documento documento) {
		return getJdbcTemplate().queryForMap(DocumentoSql.READ_SERIE_FOLIO, 
				documento.getEstablecimiento().getId(),
				documento.getTipoDocumento().getId());
	}
	
	@Override
	public void updateFolioSerie(Documento documento) {
		int folioActualizado = Integer.parseInt(documento.getComprobante().getFolio()) + 1;
		
		getJdbcTemplate().update(DocumentoSql.UPDATE_FOLIO_SERIE, 
				folioActualizado,
				documento.getEstablecimiento().getId(),
				documento.getTipoDocumento().getId());
	}

}
