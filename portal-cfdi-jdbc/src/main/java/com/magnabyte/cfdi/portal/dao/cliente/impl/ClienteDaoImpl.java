package com.magnabyte.cfdi.portal.dao.cliente.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.cliente.ClienteDao;
import com.magnabyte.cfdi.portal.dao.cliente.sql.ClienteSql;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.exception.PortalException;

@Repository("clienteDao")
public class ClienteDaoImpl extends GenericJdbcDao implements ClienteDao {

	private static final Logger logger = LoggerFactory.getLogger(ClienteDaoImpl.class);
	
	@Override
	public void save(Cliente cliente) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(getJdbcTemplate());
			simpleInsert.setTableName(ClienteSql.TABLE_NAME);
			simpleInsert.setGeneratedKeyName(ClienteSql.ID_CLIENTE);
			cliente.setId(simpleInsert.executeAndReturnKey(getParameters(cliente)).intValue());
		} catch (DataAccessException ex) {			
			logger.debug("No se pudo registrar el Cliente en la base de datos.", ex);
			throw new PortalException("No se pudo registrar el Cliente en la base de datos.", ex);
		}
	}

	@Override
	public List<Cliente> findClientesByNameRfc(Cliente cliente) {
		return getJdbcTemplate().query(ClienteSql.FIND_BY_NAME_RFC,
				CLIENTE_MAPPER, cliente.getRfc(), cliente.getNombre());
	}

	@Override
	public Cliente read(Cliente cliente) {
		return getJdbcTemplate().queryForObject(ClienteSql.FIND_BY_ID,
				CLIENTE_MAPPER, cliente.getId());
	}

	@Override
	public void update(Cliente cliente) {
		getJdbcTemplate().update(ClienteSql.UPDATE_CLIENETE, new Object[] {
			cliente.getNombre(),
			cliente.getRfc(),
			cliente.getId()
		});
	}

	private MapSqlParameterSource getParameters(Cliente cliente) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(ClienteSql.NOMBRE, cliente.getNombre());
		params.addValue(ClienteSql.RFC, cliente.getRfc());

		return params;
	}

	private static final RowMapper<Cliente> CLIENTE_MAPPER = new RowMapper<Cliente>() {

		@Override
		public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
			Cliente cliente = new Cliente();
			cliente.setId(rs.getInt(ClienteSql.ID_CLIENTE));
			cliente.setRfc(rs.getString(ClienteSql.RFC));
			cliente.setNombre(rs.getString(ClienteSql.NOMBRE));
			return cliente;
		}
	};
}
