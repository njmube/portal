package com.magnabyte.cfdi.portal.dao.cliente.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import mx.gob.sat.cfd._3.Comprobante.Receptor;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.cliente.ClienteDao;
import com.magnabyte.cfdi.portal.dao.cliente.sql.ClienteSql;

@Repository("clienteDao")
public class ClienteDaoImpl extends GenericJdbcDao implements ClienteDao {

	@Override
	public List<Receptor> findClientesByNameRfc(Receptor receptor) {
		return getJdbcTemplate().query(ClienteSql.FIND_BY_NAME_RFC,
				CLIENTE_MAPPER, receptor.getRfc(), receptor.getNombre());
	}

	private static final RowMapper<Receptor> CLIENTE_MAPPER = new RowMapper<Receptor>() {

		@Override
		public Receptor mapRow(ResultSet rs, int rowNum) throws SQLException {
			Receptor receptor = new Receptor();
			receptor.setRfc(rs.getString("rfc"));
			receptor.setNombre(rs.getString("nombre"));
			return receptor;
		}
	};
}
