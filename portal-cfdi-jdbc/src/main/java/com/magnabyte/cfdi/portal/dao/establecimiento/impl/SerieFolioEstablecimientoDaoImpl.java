package com.magnabyte.cfdi.portal.dao.establecimiento.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.SerieFolioEstablecimientoDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusGenerico;
import com.magnabyte.cfdi.portal.model.establecimiento.SerieFolioEstablecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mxs
 * Fecha:18/03/2014
 * Clase que representa el acceso a datos de la serie y el folio de un establecimiento
 */
@Repository("serieFolioEstableciminentoDao")
public class SerieFolioEstablecimientoDaoImpl extends GenericJdbcDao implements SerieFolioEstablecimientoDao {
	
	public static final Logger logger = Logger.getLogger(SerieFolioEstablecimientoDaoImpl.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public void save(SerieFolioEstablecimiento  serieFolioEstablecimiento) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(
					getJdbcTemplate());
			simpleInsert.setTableName(EstablecimientoSql.TABLE_ESTAB_SERIE);
			simpleInsert
					.setGeneratedKeyName(EstablecimientoSql.ID_SERIE_FOLIO);
			serieFolioEstablecimiento.setId(simpleInsert.executeAndReturnKey(
					getParameters(serieFolioEstablecimiento)).intValue());
		} catch (DataAccessException ex) {
			logger.debug(messageSource.getMessage("seriefolio.establecimiento.error.save", new Object[] {ex}, null));
			throw new PortalException(messageSource.getMessage("seriefolio.establecimiento.error.save", new Object[] {ex}, null));
		}
	}
	
	private MapSqlParameterSource getParameters (
			SerieFolioEstablecimiento serieFolioEstablecimiento) {
		MapSqlParameterSource params = new MapSqlParameterSource();
//		params.addValue(EstablecimientoSql.ID_SERIE_FOLIO, 
//				serieFolioEstablecimiento.getId());
		params.addValue(EstablecimientoSql.ID_ESTABLECIMIENTO, 
				serieFolioEstablecimiento.getEstablecimiento().getId());
		params.addValue(EstablecimientoSql.SERIE, 
				serieFolioEstablecimiento.getSerie());
		params.addValue(EstablecimientoSql.STATUS, 
				EstatusGenerico.ACTIVO.getId());
		params.addValue(EstablecimientoSql.FOLIO_INICIAL, 
				serieFolioEstablecimiento.getFolioInicial());
		params.addValue(EstablecimientoSql.FOLIO_CONSECUTIVO, 
				serieFolioEstablecimiento.getFolioInicial());
		params.addValue(EstablecimientoSql.ID_TIPO_DOCUMENTO, 
				serieFolioEstablecimiento.getTipoDocumento().getId());
		
		return params;
		
	}

}
