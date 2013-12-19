package com.magnabyte.cfdi.portal.dao.cliente.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.cliente.ClienteDao;
import com.magnabyte.cfdi.portal.dao.cliente.sql.ClienteSql;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;

@Repository("clienteDao")
public class ClienteDaoImpl extends GenericJdbcDao implements ClienteDao {

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
