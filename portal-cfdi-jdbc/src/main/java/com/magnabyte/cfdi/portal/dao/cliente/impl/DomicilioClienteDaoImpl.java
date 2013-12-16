package com.magnabyte.cfdi.portal.dao.cliente.impl;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.cliente.DomicilioClienteDao;
import com.magnabyte.cfdi.portal.dao.cliente.sql.DomicilioSql;
import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.commons.Estado;
import com.magnabyte.cfdi.portal.model.commons.Pais;

@Repository("domicilioClienteDao")
public class DomicilioClienteDaoImpl extends GenericJdbcDao implements
		DomicilioClienteDao {

	public static final Logger logger = Logger
			.getLogger(DomicilioClienteDaoImpl.class);

	@Override
	public List<DomicilioCliente> readByCliente(Cliente cliente) {
		List<DomicilioCliente> domiciliosCte = new ArrayList<DomicilioCliente>();
		
		domiciliosCte = getJdbcTemplate().query(DomicilioSql.FIND_DOM_BY_CLIENTE,
				DOMICILIO_MAPPER, cliente.getId()); 
		return domiciliosCte;
	}

	private static final RowMapper<DomicilioCliente> DOMICILIO_MAPPER = new RowMapper<DomicilioCliente>() {

		@Override
		public DomicilioCliente mapRow(ResultSet rs, int rowNum) throws SQLException {
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
			domicilio.setLocalidad(rs.getString(DomicilioSql.LOCALIDAD));
			domicilio.setReferencia(rs.getString(DomicilioSql.REFERENCIA));
			
			return domicilio;
		}
	};
}
