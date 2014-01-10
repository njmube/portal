package com.magnabyte.cfdi.portal.dao.establecimiento.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.EstablecimientoDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.emisor.EmpresaEmisor;
import com.magnabyte.cfdi.portal.model.establecimiento.DomicilioEstablecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.RutaRepositorio;
import com.magnabyte.cfdi.portal.model.establecimiento.TipoEstablecimiento;
import com.magnabyte.cfdi.portal.model.establecimiento.factory.EstablecimientoFactory;
import com.magnabyte.cfdi.portal.model.exception.PortalException;

@Repository("establecimientoDao")
public class EstablecimientoDaoImpl extends GenericJdbcDao implements
		EstablecimientoDao {

	private static final Logger logger = LoggerFactory
			.getLogger(EstablecimientoDaoImpl.class);

	@Override
	public Establecimiento findByClave(Establecimiento establecimiento) {
		logger.debug(EstablecimientoSql.FIND_BY_CLAVE);
		try {
			return getJdbcTemplate().queryForObject(EstablecimientoSql.FIND_BY_CLAVE, MAPPER_ESTABLECIMIENTO,
					establecimiento.getClave());
		} catch (EmptyResultDataAccessException ex) {
			logger.debug("No existe el establecimiento");
			return null;
		}
	}
	
	@Override
	public Establecimiento readByClave(Establecimiento establecimiento) {
		logger.debug(EstablecimientoSql.READ_BY_CLAVE);

		return getJdbcTemplate().queryForObject(EstablecimientoSql.READ_BY_CLAVE, MAPPER_ESTAB_COMPLETO,
				establecimiento.getClave());
	}
	
	@Override
	public Establecimiento readById(Establecimiento establecimiento) {
		return getJdbcTemplate().queryForObject(EstablecimientoSql.READ_BY_ID, new RowMapper<Establecimiento>() {
			@Override
			public Establecimiento mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Establecimiento establecimiento = new Establecimiento();
				RutaRepositorio rutaRepositorio = new RutaRepositorio();

				rutaRepositorio.setRutaRepositorio(rs.getString("ruta_repo"));
				rutaRepositorio.setRutaRepoOut(rs.getString("ruta_out"));
				
				establecimiento.setRutaRepositorio(rutaRepositorio);
				return establecimiento;
			}
		}, establecimiento.getId());
	}

	@Override
	public String getRoles(Establecimiento establecimiento) {
		return getJdbcTemplate().queryForObject(EstablecimientoSql.GET_ROLES, String.class, establecimiento.getId());
	}
	
	@Override
	public List<Establecimiento> readAll() {
		logger.debug(EstablecimientoSql.READ_ALL);

		return getJdbcTemplate().query(EstablecimientoSql.READ_ALL, MAPPER_ESTABLECIMIENTO);
	}
	
	public Establecimiento readAllById (Establecimiento establecimiento) {
		String qry = EstablecimientoSql.READ_ALL_WITH_IDS;
		logger.debug("-- readAllById "+ qry);
		return getJdbcTemplate().queryForObject(qry, MAPPER_FOR_ESTAB, establecimiento.getId());
	}
	
	@Override
	public void update (Establecimiento establecimiento) {
		logger.debug("consulta --- "+EstablecimientoSql.UPDATE_ESTABLECIMIENTO);
		getJdbcTemplate().update(EstablecimientoSql.UPDATE_ESTABLECIMIENTO, new Object[] {
			
				establecimiento.getClave(),
				establecimiento.getNombre(),
				establecimiento.getPassword(),
				establecimiento.getId()
		});
	}
	@Override
	public void save(Establecimiento establecimiento) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(getJdbcTemplate());
			simpleInsert.setTableName(EstablecimientoSql.TABLE_NAME);
			simpleInsert.setGeneratedKeyName(EstablecimientoSql.ID_ESTABLECIMIENTO);
			establecimiento.setId(simpleInsert.executeAndReturnKey(getParameters(establecimiento)).intValue());
		} catch (DataAccessException ex) {			
			logger.debug("No se pudo registrar el Establecimiento en la base de datos.", ex);
			throw new PortalException("No se pudo registrar el Establecimiento en la base de datos.", ex);
		}
	}
	
	private MapSqlParameterSource getParameters(Establecimiento establecimiento) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(EstablecimientoSql.CLAVE, establecimiento.getClave());
		params.addValue(EstablecimientoSql.NOMBRE, establecimiento.getNombre());
		params.addValue(EstablecimientoSql.PASSWORD, establecimiento.getPassword());
		params.addValue(EstablecimientoSql.ID_DOM_ESTAB, establecimiento.getDomicilio().getId());
		params.addValue(EstablecimientoSql.ID_EMISOR, establecimiento.getEmisor().getId());
		params.addValue(EstablecimientoSql.ID_RUTA_ESTAB, establecimiento.getRutaRepositorio().getId());
		params.addValue(EstablecimientoSql.ID_TIPO_ESTAB, establecimiento.getTipoEstablecimiento().getId());

		return params;
	}
	
	private static final RowMapper<Establecimiento> MAPPER_ESTABLECIMIENTO = new RowMapper<Establecimiento>() {

		@Override
		public Establecimiento mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			Establecimiento establecimiento = new Establecimiento();
			
			establecimiento.setId(rs.getInt(EstablecimientoSql.ID_ESTABLECIMIENTO));
			establecimiento.setClave(rs.getString(EstablecimientoSql.CLAVE));
			establecimiento.setNombre(rs.getString(EstablecimientoSql.NOMBRE));
			establecimiento.setPassword(rs.getString(EstablecimientoSql.PASSWORD));

			return establecimiento;
		}
	};

	private static final RowMapper<Establecimiento> MAPPER_ESTAB_COMPLETO = new RowMapper<Establecimiento>() {

		@Override
		public Establecimiento mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			Establecimiento establecimiento = new Establecimiento();
			EmpresaEmisor empresa = new EmpresaEmisor();
			RutaRepositorio rutaRepo = new RutaRepositorio();
			DomicilioEstablecimiento domEstablecimiento = new DomicilioEstablecimiento();
			TipoEstablecimiento tipoEstablecimiento = new TipoEstablecimiento();

			establecimiento.setId(rs.getInt(EstablecimientoSql.ID_ESTABLECIMIENTO));
			establecimiento.setClave(String.valueOf(rs.getInt(EstablecimientoSql.CLAVE)));
			establecimiento.setNombre(rs.getString(EstablecimientoSql.NOMBRE));
			
			rutaRepo.setId(rs.getInt(EstablecimientoSql.ID_RUTA_ESTAB));
			rutaRepo.setRutaRepositorio(rs.getString(EstablecimientoSql.RUTA_REPOSITORIO));
			rutaRepo.setRutaRepoIn(rs.getString(EstablecimientoSql.RUTA_IN));
			rutaRepo.setRutaRepoOut(rs.getString(EstablecimientoSql.RUTA_OUT));
			rutaRepo.setRutaRepoInProc(rs.getString(EstablecimientoSql.RUTA_INPROC));
			
			domEstablecimiento.setLocalidad(rs.getString(EstablecimientoSql.LOCALIDAD));
			
			tipoEstablecimiento.setId(rs.getInt(EstablecimientoSql.ID_TIPO_ESTAB));
			tipoEstablecimiento.setNombre(rs.getString(EstablecimientoSql.NOM_ESTAB));
			tipoEstablecimiento.setRol(rs.getString(EstablecimientoSql.ROL));
			
			empresa.setId(rs.getInt(EstablecimientoSql.ID_EMISOR));
			
			establecimiento.setEmisor(empresa);
			establecimiento.setTipoEstablecimiento(tipoEstablecimiento);
			establecimiento.setRutaRepositorio(rutaRepo);
			establecimiento.setDomicilio(domEstablecimiento);

			return establecimiento;
		}
	};
	
	private static final RowMapper<Establecimiento> MAPPER_FOR_ESTAB = new RowMapper<Establecimiento>() {
		
		@Override
		public Establecimiento mapRow(ResultSet rs, int rowNum) throws SQLException {
			Establecimiento establecimiento = EstablecimientoFactory.newInstance();
			RutaRepositorio rutaRepositorio = new RutaRepositorio();
			DomicilioEstablecimiento domicilioEstablecimiento = new DomicilioEstablecimiento();
			TipoEstablecimiento tipoEstablecimiento = new TipoEstablecimiento();
			
			establecimiento.setId(rs.getInt(EstablecimientoSql.ID_ESTABLECIMIENTO));
			establecimiento.setClave(rs.getString(EstablecimientoSql.CLAVE));
			establecimiento.setNombre(rs.getString(EstablecimientoSql.NOMBRE));
			establecimiento.setPassword(rs.getString(EstablecimientoSql.PASSWORD));
			
			rutaRepositorio.setId(rs.getInt(EstablecimientoSql.ID_RUTA_ESTAB));
			establecimiento.setRutaRepositorio(rutaRepositorio);
			
			domicilioEstablecimiento.setId(rs.getInt(EstablecimientoSql.ID_DOM_ESTAB));
			establecimiento.setDomicilio(domicilioEstablecimiento);
			
			tipoEstablecimiento.setId(rs.getInt(EstablecimientoSql.ID_TIPO_ESTAB));
			establecimiento.setTipoEstablecimiento(tipoEstablecimiento);

			return establecimiento;
		}
	};

}
