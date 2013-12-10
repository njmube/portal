package com.magnabyte.cfdi.portal.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.SucursalDao;
import com.magnabyte.cfdi.portal.model.sucursal.Sucursal;


@Repository("sucursalDao")
@SuppressWarnings("deprecation")
public class SucursalDaoImpl extends GenericJdbcDao implements SucursalDao, UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(SucursalDaoImpl.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Sucursal sucursal = null;
		String query = "select * from t_establecimiento where clave = ?";
		sucursal = getJdbcTemplate().queryForObject(query, MAPPER_ESTABLECIMIENTO, username);
		return sucursal;
	}
	
	private static final RowMapper<Sucursal> MAPPER_ESTABLECIMIENTO = new RowMapper<Sucursal>() {
		
		@Override
		public Sucursal mapRow(ResultSet rs, int rowNum) throws SQLException {
			String username = rs.getString("clave");
			String password = rs.getString("password");
			Sucursal sucursal = new Sucursal(username, password, Arrays.asList(new GrantedAuthority [] { new GrantedAuthorityImpl("ROLE_CORP")}));
			sucursal.setRutaRepositorio(rs.getString("ruta_repositorio"));
			return sucursal;
		}
	};

}
