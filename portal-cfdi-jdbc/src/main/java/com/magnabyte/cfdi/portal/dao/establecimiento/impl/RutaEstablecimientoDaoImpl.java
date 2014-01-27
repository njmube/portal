package com.magnabyte.cfdi.portal.dao.establecimiento.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.RutaEstablecimientoDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.RutaRepositorioSql;
import com.magnabyte.cfdi.portal.model.establecimiento.RutaRepositorio;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el acceso a datos de ruta de establecimiento
 */
@Repository("rutaEstablecimientoDao")
public class RutaEstablecimientoDaoImpl extends GenericJdbcDao implements
		RutaEstablecimientoDao {

	public static final Logger logger = LoggerFactory
			.getLogger(RutaEstablecimientoDaoImpl.class);

	@Override
	public void save(RutaRepositorio rutaRepositorio) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(
					getJdbcTemplate());
			simpleInsert.setTableName(RutaRepositorioSql.TABLE_NAME);
			simpleInsert.setGeneratedKeyName(RutaRepositorioSql.ID_RUTA_ESTAB);
			rutaRepositorio.setId(simpleInsert.executeAndReturnKey(
					getParameters(rutaRepositorio)).intValue());
		} catch (DataAccessException ex) {
			logger.debug(
					"No se pudo registrar la ruta del Establecimiento en la base de datos.",
					ex);
		}
	}
	
	@Override
	public void update(RutaRepositorio rutaRepositorio) {
		getJdbcTemplate().update(RutaRepositorioSql.UPDATE_RUTA, new Object[] {
				
				rutaRepositorio.getRutaRepositorio(),
				rutaRepositorio.getRutaRepoIn(),
				rutaRepositorio.getRutaRepoOut(),
				rutaRepositorio.getRutaRepoInProc(),
				rutaRepositorio.getId()
			});
	}
	
	@Override
	public RutaRepositorio readById (RutaRepositorio rutaRepositorio) {
		String qry = RutaRepositorioSql.FIND_BY_ID;
		logger.debug("-- readById "+ qry);
		return getJdbcTemplate().queryForObject(qry, MAPPER_RUTA_REPO, rutaRepositorio.getId());
	}

	private MapSqlParameterSource getParameters(RutaRepositorio rutaRepositorio) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(RutaRepositorioSql.ID_RUTA_ESTAB,
				rutaRepositorio.getId());
		params.addValue(RutaRepositorioSql.RUTA_REPOSITORIO,
				rutaRepositorio.getRutaRepositorio());
		params.addValue(RutaRepositorioSql.RUTA_IN,
				rutaRepositorio.getRutaRepoIn());
		params.addValue(RutaRepositorioSql.RUTA_OUT,
				rutaRepositorio.getRutaRepoOut());
		params.addValue(RutaRepositorioSql.RUTA_INPROC,
				rutaRepositorio.getRutaRepoInProc());

		return params;
	}

	private static final RowMapper<RutaRepositorio> MAPPER_RUTA_REPO = new RowMapper<RutaRepositorio>() {

		@Override
		public RutaRepositorio mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			RutaRepositorio rutaRepositorio = new RutaRepositorio();
			//
			rutaRepositorio.setId(rs.getInt(RutaRepositorioSql.ID_RUTA_ESTAB));
			rutaRepositorio.setRutaRepositorio(rs.getString(RutaRepositorioSql.RUTA_REPOSITORIO));
			rutaRepositorio.setRutaRepoIn(rs.getString(RutaRepositorioSql.RUTA_IN));
			rutaRepositorio.setRutaRepoOut(rs.getString(RutaRepositorioSql.RUTA_OUT));
			rutaRepositorio.setRutaRepoInProc(rs.getString(RutaRepositorioSql.RUTA_INPROC));

			return rutaRepositorio;
		}
	};
}
