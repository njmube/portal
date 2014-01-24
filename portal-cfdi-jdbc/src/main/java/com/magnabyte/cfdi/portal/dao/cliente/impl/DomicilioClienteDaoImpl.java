package com.magnabyte.cfdi.portal.dao.cliente.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.cliente.DomicilioClienteDao;
import com.magnabyte.cfdi.portal.dao.cliente.sql.DomicilioSql;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.commons.Pais;
import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusDomiciolioCliente;

@Repository("domicilioClienteDao")
public class DomicilioClienteDaoImpl extends GenericJdbcDao implements
		DomicilioClienteDao {

	public static final Logger logger = Logger
			.getLogger(DomicilioClienteDaoImpl.class);

	@Override
	public void save(DomicilioCliente domicilio) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(getJdbcTemplate());
			simpleInsert.setTableName(DomicilioSql.TABLE_NAME);
			simpleInsert.setGeneratedKeyName(DomicilioSql.ID_DOMICILIO);
			domicilio.setId(simpleInsert.executeAndReturnKey(getParameters(domicilio)).intValue());
		} catch (DataAccessException ex) {
			logger.debug("No se pudo registrar el DomicilioCliente en la base de datos.",ex);
		}
	}

	@Override
	public List<DomicilioCliente> readByCliente(Cliente cliente) {
		List<DomicilioCliente> domiciliosCte = new ArrayList<DomicilioCliente>();

		domiciliosCte = getJdbcTemplate().query(
				DomicilioSql.FIND_DOM_BY_CLIENTE, DOMICILIO_MAPPER,
				cliente.getId());
		return domiciliosCte;
	}
	
	@Override
	public void update(DomicilioCliente domicilio) {
		getJdbcTemplate().update(DomicilioSql.UPDATE_DOMICILIO_CTE, new Object[] {
				domicilio.getEstado().getId(),
				domicilio.getCliente().getId(),
				domicilio.getCalle(),
				domicilio.getNoExterior(),
				domicilio.getNoInterior(),
				domicilio.getMunicipio(),
				domicilio.getColonia(),
				domicilio.getCodigoPostal(),
				domicilio.getEstatus().getId(),
//				domicilio.getReferencia(),
//				domicilio.getLocalidad(),
				domicilio.getId()
			});
	}
	
	@Override
	public void delete(DomicilioCliente domicilio) {
		String qry = DomicilioSql.DELETE_DOMICILIO_CTE;
		getJdbcTemplate().update(qry, domicilio.getId());
	}

	@Override
	public Estado readEstado(Estado estado) {
		try {
			return getJdbcTemplate().queryForObject(DomicilioSql.READ_EDO_PAIS, ESTADO_MAPPER, estado.getNombre());
		} catch (EmptyResultDataAccessException ex) {
			logger.debug("El estado no existe, se insertará.");
			return null;
		}
	}
	
	@Override
	public Pais readPais(Pais pais) {
		try {
			return getJdbcTemplate().queryForObject(DomicilioSql.READ_PAIS, PAIS_MAPPER, pais.getNombre());			
		} catch (EmptyResultDataAccessException ex) {
			logger.debug("El país no existe, se insertará.");
			return null;
		}
	}

	@Override
	public void saveEstado(Estado estado) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(getJdbcTemplate());
			simpleInsert.setTableName(DomicilioSql.TABLE_ESTADO);
			simpleInsert.setGeneratedKeyName(DomicilioSql.ID_ESTADO);
			estado.setId(simpleInsert.executeAndReturnKey(getParametersEstado(estado)).intValue());
		} catch (DataAccessException ex) {
			logger.debug("No se pudo registrar el Estado en la base de datos.",ex);
		}
	}
	
	@Override
	public void savePaisSinEstado(DomicilioCliente domicilio, Pais pais) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(getJdbcTemplate());
			simpleInsert.setTableName(DomicilioSql.TABLE_PAIS_SIN_ESTADO);
			simpleInsert.setGeneratedKeyName(DomicilioSql.ID_ESTADO);
			pais.setId(simpleInsert.executeAndReturnKey(getParamsPaisSinEstado(domicilio, pais)).intValue());
		} catch (DataAccessException ex) {
			logger.debug("No se pudo registrar el País sin Estado en la base de datos.",ex);
		}
	}
	
	private MapSqlParameterSource getParamsPaisSinEstado(DomicilioCliente domicilio
			, Pais pais) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(DomicilioSql.ID_DOMICILIO, pais.getId());
		params.addValue(DomicilioSql.ID_PAIS, domicilio.getId());

		return params;
	}
	
	private MapSqlParameterSource getParametersEstado(Estado estado) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(DomicilioSql.NOMBRE, estado.getNombre());
		params.addValue(DomicilioSql.ID_PAIS, estado.getPais().getId());

		return params;
	}
	
	private MapSqlParameterSource getParameters(DomicilioCliente domicilio) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(DomicilioSql.ID_CLIENTE, domicilio.getCliente().getId());
		params.addValue(DomicilioSql.CALLE, domicilio.getCalle());
		params.addValue(DomicilioSql.NO_EXTERIOR, domicilio.getNoExterior());
		params.addValue(DomicilioSql.NO_INTERIOR, domicilio.getNoInterior());
		params.addValue(DomicilioSql.COLONIA, domicilio.getColonia());
		params.addValue(DomicilioSql.MUNICIPIO, domicilio.getMunicipio());
		params.addValue(DomicilioSql.ID_ESTADO, domicilio.getEstado().getId());
		params.addValue(DomicilioSql.CODIGO_POSTAL, domicilio.getCodigoPostal());
		params.addValue(DomicilioSql.ID_ESTATUS, domicilio.getEstatus().getId());
//		params.addValue(DomicilioSql.LOCALIDAD, domicilio.getLocalidad());
//		params.addValue(DomicilioSql.REFERENCIA, 
//				domicilio.getReferencia().isEmpty() ? null : domicilio.getReferencia());

		return params;
	}

	private static final RowMapper<DomicilioCliente> DOMICILIO_MAPPER = new RowMapper<DomicilioCliente>() {

		@Override
		public DomicilioCliente mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			Pais pais = new Pais();
			Estado estado = new Estado();
			DomicilioCliente domicilio = new DomicilioCliente();

			Cliente cliente = new Cliente();
			cliente.setId((rs.getInt(DomicilioSql.ID_CLIENTE)));

			domicilio.setId(rs.getInt(DomicilioSql.ID_DOMICILIO));
			domicilio.setCliente(cliente);
			domicilio.setCalle(rs.getString(DomicilioSql.CALLE));
			domicilio.setNoExterior(rs.getString(DomicilioSql.NO_EXTERIOR));
			domicilio.setNoInterior(rs.getString(DomicilioSql.NO_INTERIOR));

			pais.setId(rs.getInt(DomicilioSql.ID_PAIS));
			pais.setNombre(rs.getString(DomicilioSql.AS_PAIS));
			
			estado.setId(rs.getInt(DomicilioSql.ID_ESTADO));
			estado.setNombre(rs.getString(DomicilioSql.AS_ESTADO));
			estado.setPais(pais);
			
			domicilio.setEstado(estado);
			domicilio.setMunicipio(rs.getString(DomicilioSql.MUNICIPIO));
			domicilio.setColonia(rs.getString(DomicilioSql.COLONIA));
			domicilio.setCodigoPostal(rs.getString(DomicilioSql.CODIGO_POSTAL));
			domicilio.setEstatus(EstatusDomiciolioCliente.getById(
					rs.getInt(DomicilioSql.ID_ESTATUS)));
//			domicilio.setLocalidad(rs.getString(DomicilioSql.LOCALIDAD));
//			domicilio.setReferencia(rs.getString(DomicilioSql.REFERENCIA));

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
	
	private static final RowMapper<Pais> PAIS_MAPPER = new RowMapper<Pais>() {

		@Override
		public Pais mapRow(ResultSet rs, int rowNum) throws SQLException {
			Pais pais = new Pais();
			
			pais.setId(rs.getInt(DomicilioSql.ID_PAIS));
			pais.setNombre(rs.getString(DomicilioSql.AS_PAIS));
			
			return pais;
		}
		
	};

}
