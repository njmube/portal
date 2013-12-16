package com.magnabyte.cfdi.portal.dao.establecimiento.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.EstablecimientoDao;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;


@Repository("establecimientoDao")
public class EstablecimientoDaoImpl extends GenericJdbcDao implements EstablecimientoDao {

	private static final Logger logger = LoggerFactory.getLogger(EstablecimientoDaoImpl.class);

	@Override
	public Establecimiento findByClave(Establecimiento establecimiento) {
		String qry = EstablecimientoSql.FIND_BY_CLAVE;
		logger.debug(qry);
		return getJdbcTemplate().queryForObject(qry, MAPPER_ESTABLECIMIENTO, establecimiento.getClave());
	}
	
	private static final RowMapper<Establecimiento> MAPPER_ESTABLECIMIENTO = new RowMapper<Establecimiento>() {
		
		@Override
		public Establecimiento mapRow(ResultSet rs, int rowNum) throws SQLException {
			Establecimiento establecimiento = new Establecimiento();
			establecimiento.setId(rs.getInt("id_establecimiento"));
			establecimiento.setClave(rs.getString("clave"));
			establecimiento.setNombre(rs.getString("nombre"));
			establecimiento.setPassword(rs.getString("password"));
			establecimiento.setRutaRepositorio(rs.getString("ruta_repositorio"));
			return establecimiento;
		}
	};

}
