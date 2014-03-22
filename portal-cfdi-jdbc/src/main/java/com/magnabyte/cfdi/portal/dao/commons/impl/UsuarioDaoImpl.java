package com.magnabyte.cfdi.portal.dao.commons.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.commons.UsuarioDao;
import com.magnabyte.cfdi.portal.dao.commons.sql.UsuarioSql;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.commons.Usuario;
import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusGenerico;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el acceso a datos de usuario
 */
@Repository("usuarioDao")
public class UsuarioDaoImpl extends GenericJdbcDao implements UsuarioDao {

	public static final Logger logger = 
			LoggerFactory.getLogger(UsuarioDaoImpl.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public void save(Usuario usuario) {
		try {
			SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(getJdbcTemplate());
			simpleInsert.setTableName(UsuarioSql.TABLE_NAME);
			simpleInsert.setGeneratedKeyName(UsuarioSql.ID_USUARIO);
			usuario.setId(simpleInsert.executeAndReturnKey(getParameters(usuario)).intValue());
		} catch (DataAccessException ex) {			
			logger.debug(messageSource.getMessage("usuario.error.save", new Object[] {ex}, null));
			throw new PortalException(messageSource.getMessage("usuario.error.save", new Object[] {ex}, null));
		}
	}
	
	private MapSqlParameterSource getParameters(Usuario usuario) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue(UsuarioSql.USUARIO, usuario.getUsuario());
		params.addValue(UsuarioSql.PASSWORD, usuario.getPassword());
		params.addValue(EstablecimientoSql.ID_ESTABLECIMIENTO, 
				usuario.getEstablecimiento().getId());
		params.addValue(UsuarioSql.ID_STATUS, usuario.getEstatus().getId());

		return params;
	}

	@Override
	public Usuario getUsuarioByEstablecimiento(Usuario usuario) {
		logger.debug("sql usuario :  "+UsuarioSql.GET_BY_ESTABLECIMIENTO);
		String qry = UsuarioSql.GET_BY_ESTABLECIMIENTO;
		Usuario object = null;
		try {
			object = getJdbcTemplate().queryForObject(qry, 
					USUARIO_MAPPER, usuario.getEstablecimiento().getId(), usuario.getUsuario());
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
		return object;
				
	}
	
	@Override
	public Usuario read(Usuario usuario) {
		return getJdbcTemplate().queryForObject(UsuarioSql.READ, 
				USUARIO_MAPPER, usuario.getId());
	}
	
	@Override
	public List<Usuario> getAllUsuarios() {
		return getJdbcTemplate().query(UsuarioSql.GET_ALL, USUARIO_MAPPER);
	}
	
	@Override
	public void update(Usuario usuario) {
		String qry = UsuarioSql.UPDATE_USUARIO;
		
		getJdbcTemplate().update(qry, new Object [] {
			usuario.getUsuario(),
			usuario.getPassword(),
			usuario.getEstablecimiento().getId(),
			usuario.getEstatus().getId(),
			usuario.getId()
		});
	}
	
	private static final RowMapper<Usuario> USUARIO_MAPPER = new RowMapper<Usuario>() {

		@Override
		public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
			Usuario usuario = new Usuario();
			Establecimiento estable = new Establecimiento();
			
			usuario.setId(rs.getInt(UsuarioSql.ID_USUARIO));
			usuario.setUsuario(rs.getString(UsuarioSql.USUARIO));
			usuario.setPassword(rs.getString(UsuarioSql.PASSWORD));
			estable.setId(rs.getInt(EstablecimientoSql.ID_ESTABLECIMIENTO));
			estable.setNombre(rs.getString(EstablecimientoSql.NOM_ESTAB));
			usuario.setEstablecimiento(estable);
			usuario.setEstatus(EstatusGenerico.getById(rs.getInt(UsuarioSql.ID_STATUS)));
			return usuario;
		}
	};

}
