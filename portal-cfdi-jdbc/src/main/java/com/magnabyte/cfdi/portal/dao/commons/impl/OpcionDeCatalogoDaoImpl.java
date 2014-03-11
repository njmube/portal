package com.magnabyte.cfdi.portal.dao.commons.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.commons.OpcionDeCatalogoDao;
import com.magnabyte.cfdi.portal.model.commons.OpcionDeCatalogo;
import com.magnabyte.cfdi.portal.model.exception.PortalException;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el acceso a datos para opción de catálogo 
 */
@Repository("opcionDeCatalogoDao")
public class OpcionDeCatalogoDaoImpl extends GenericJdbcDao 
	implements OpcionDeCatalogoDao {

	private static final Logger logger = 
			Logger.getLogger(OpcionDeCatalogoDaoImpl.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public Collection<OpcionDeCatalogo> getCatalogo(String catalogo,
			String orderBy) {
		String qry = assignValues("SELECT * FROM {0} ORDER BY {1}", catalogo, orderBy);
		logger.debug(qry);
		return getJdbcTemplate().query(qry, OPCION_DE_CATALOGO_MAPPER);
	}

	@Override
	public Collection<OpcionDeCatalogo> getCatalogoParam(String catalogo,
			String campo, String param, String orderBy) {
		String qry = assignValues("SELECT * FROM {0} WHERE {1} = ? ORDER BY {2}",
				catalogo, campo, orderBy);
		logger.debug(qry);
		return getJdbcTemplate().query(qry, OPCION_DE_CATALOGO_MAPPER, param);
	}
	
	@Override
	public void save(OpcionDeCatalogo opcionDeCatalogo, String catalogo, String campoId) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(getJdbcTemplate());
			simpleInsert.setTableName(catalogo);
			simpleInsert.setGeneratedKeyName(campoId);
			opcionDeCatalogo.setId(simpleInsert.executeAndReturnKey(getParameters(opcionDeCatalogo)).intValue());
		} catch (DataAccessException ex) {			
			logger.debug(messageSource.getMessage("opcatalogo.error.save", new Object[] {ex}, null));
			throw new PortalException(messageSource.getMessage("opcatalogo.error.save", new Object[] {ex}, null));
		}
	}
	
	private static final RowMapper<OpcionDeCatalogo> OPCION_DE_CATALOGO_MAPPER = 
			new RowMapper<OpcionDeCatalogo>() {
		
		@Override
		public OpcionDeCatalogo mapRow(ResultSet rs, int rowNum) throws SQLException {
			OpcionDeCatalogo opcion = new OpcionDeCatalogo();
			opcion.setId(rs.getInt(1));
			opcion.setNombre(rs.getString(2));
			return opcion;
		}
	};
	
	private MapSqlParameterSource getParameters(OpcionDeCatalogo opcionDeCatalogo) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("nombre", opcionDeCatalogo.getNombre());
		return params;
	}
}
