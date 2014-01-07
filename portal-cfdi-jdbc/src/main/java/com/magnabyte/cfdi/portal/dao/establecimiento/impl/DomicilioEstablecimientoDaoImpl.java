package com.magnabyte.cfdi.portal.dao.establecimiento.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.cliente.sql.DomicilioSql;
import com.magnabyte.cfdi.portal.dao.establecimiento.DomicilioEstablecimientoDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.DomicilioEstablecimientoSql;
import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.commons.Pais;
import com.magnabyte.cfdi.portal.model.establecimiento.DomicilioEstablecimiento;

@Repository("domicilioEstablecimientoDao")
public class DomicilioEstablecimientoDaoImpl extends GenericJdbcDao implements
		DomicilioEstablecimientoDao {

	public static final Logger logger = Logger
			.getLogger(DomicilioEstablecimientoDaoImpl.class);

	@Override
	public void save(DomicilioEstablecimiento domicilioEstablecimiento) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(
					getJdbcTemplate());
			simpleInsert.setTableName(DomicilioEstablecimientoSql.TABLE_NAME);
			simpleInsert
					.setGeneratedKeyName(DomicilioEstablecimientoSql.ID_DOMICILIO);
			domicilioEstablecimiento.setId(simpleInsert.executeAndReturnKey(
					getParameters(domicilioEstablecimiento)).intValue());
		} catch (DataAccessException ex) {
			logger.debug(
					"No se pudo registrar el DomicilioEstablecimiento en la base de datos.",
					ex);
		}
	}

	private MapSqlParameterSource getParameters(
			DomicilioEstablecimiento domicilioEstablecimiento) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(DomicilioSql.ID_DOMICILIO,
				domicilioEstablecimiento.getId());
		params.addValue(DomicilioSql.CALLE, domicilioEstablecimiento.getCalle());
		params.addValue(DomicilioSql.NO_EXTERIOR,
				domicilioEstablecimiento.getNoExterior());
		params.addValue(DomicilioSql.NO_INTERIOR,
				domicilioEstablecimiento.getNoInterior());
		params.addValue(DomicilioSql.COLONIA,
				domicilioEstablecimiento.getColonia());
		params.addValue(DomicilioSql.MUNICIPIO,
				domicilioEstablecimiento.getMunicipio());
		params.addValue(DomicilioSql.ID_ESTADO, domicilioEstablecimiento
				.getEstado().getId());
		params.addValue(DomicilioSql.CODIGO_POSTAL,
				domicilioEstablecimiento.getCodigoPostal());
		params.addValue(DomicilioSql.LOCALIDAD,
				domicilioEstablecimiento.getLocalidad());
		return params;
	}

	@Override
	public Estado readEstado(Estado estado) {
		String qry = "select ce.id_estado, ce.id_pais, dbo.TRIM(ce.nombre) as nom_estado, dbo.TRIM(cp.nombre)"
				+ " as nom_pais from c_estado as ce inner join c_pais as cp on "
				+ "ce.id_pais = cp.id_pais where ce.nombre = ?";
		try {
			return getJdbcTemplate().queryForObject(qry, ESTADO_MAPPER,
					estado.getNombre());
		} catch (EmptyResultDataAccessException ex) {
			logger.debug("El estado no existe, se insertará.");
			return null;
		}
	}
	
	@Override
	public DomicilioEstablecimiento readById(DomicilioEstablecimiento domicilioEstablecimiento) {
		String qry = DomicilioEstablecimientoSql.FIND_DOM_BY_ID; 
		domicilioEstablecimiento = getJdbcTemplate().queryForObject(qry, DOMICILIO_MAPPER, domicilioEstablecimiento.getId());
		
		return domicilioEstablecimiento;
	}
	
	@Override
	public void update(DomicilioEstablecimiento domicilio) {
		getJdbcTemplate().update(DomicilioEstablecimientoSql.UPDATE_DOMICILIO_ESTABLECIMIENTO, new Object[] {
				
//				domicilio.getEstado().getId(),
				domicilio.getCalle(),
				domicilio.getNoExterior(),
				domicilio.getNoInterior(),
				domicilio.getMunicipio(),
				domicilio.getColonia(),
				domicilio.getCodigoPostal(),
				domicilio.getLocalidad(),
				domicilio.getId()
			});
	}
	
	
	private static final RowMapper<DomicilioEstablecimiento> DOMICILIO_MAPPER = new RowMapper<DomicilioEstablecimiento>() {

		@Override
		public DomicilioEstablecimiento mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			Pais pais = new Pais();
			Estado estado = new Estado();
			DomicilioEstablecimiento domicilio = new DomicilioEstablecimiento();


			domicilio.setId(rs.getInt(DomicilioEstablecimientoSql.ID_DOMICILIO));
			domicilio.setCalle(rs.getString(DomicilioEstablecimientoSql.CALLE));
			domicilio.setNoExterior(rs.getString(DomicilioEstablecimientoSql.NO_EXTERIOR));
			domicilio.setNoInterior(rs.getString(DomicilioEstablecimientoSql.NO_INTERIOR));

			pais.setId(rs.getInt(DomicilioEstablecimientoSql.ID_PAIS));
			pais.setNombre(rs.getString(DomicilioEstablecimientoSql.AS_PAIS));
			
			estado.setId(rs.getInt(DomicilioEstablecimientoSql.ID_ESTADO));
			estado.setNombre(rs.getString(DomicilioEstablecimientoSql.AS_ESTADO));
			estado.setPais(pais);
			
			domicilio.setEstado(estado);
			domicilio.setMunicipio(rs.getString(DomicilioEstablecimientoSql.MUNICIPIO));
			domicilio.setColonia(rs.getString(DomicilioEstablecimientoSql.COLONIA));
			domicilio.setCodigoPostal(rs.getString(DomicilioEstablecimientoSql.CODIGO_POSTAL));
			domicilio.setLocalidad(rs.getString(DomicilioEstablecimientoSql.LOCALIDAD));

			return domicilio;
		}
	};

	private static final RowMapper<Estado> ESTADO_MAPPER = new RowMapper<Estado>() {

		@Override
		public Estado mapRow(ResultSet rs, int rowNum) throws SQLException {
			Pais pais = new Pais();
			Estado estado = new Estado();

			pais.setId(rs.getInt(DomicilioSql.ID_PAIS));
			pais.setNombre(rs.getString(DomicilioSql.AS_PAIS));

			estado.setId(rs.getInt(DomicilioSql.ID_ESTADO));
			estado.setNombre(rs.getString(DomicilioSql.AS_ESTADO));
			estado.setPais(pais);

			return estado;
		}
	};

}