package com.magnabyte.cfdi.portal.dao.commons.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.commons.UsuarioDao;
import com.magnabyte.cfdi.portal.dao.commons.sql.UsuarioSql;
import com.magnabyte.cfdi.portal.dao.establecimiento.sql.EstablecimientoSql;
import com.magnabyte.cfdi.portal.model.commons.Usuario;
import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusUsuario;

@Repository("usuarioDao")
public class UsuarioDaoImpl extends GenericJdbcDao implements UsuarioDao {

	@Override
	public Usuario getUsuarioByEstablecimiento(Usuario usuario) {
		String qry = UsuarioSql.GET_BY_ESTABLECIMIENTO;
		return getJdbcTemplate().queryForObject(qry, 
				UASUARIO_MAPPER, usuario.getEstablecimiento().getId());
	}
	
	private static final RowMapper<Usuario> UASUARIO_MAPPER = new RowMapper<Usuario>() {

		@Override
		public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
			Usuario usuario = new Usuario();
			
			usuario.setId(rs.getInt(UsuarioSql.ID_USUARIO));
			usuario.setUsuario(rs.getString(UsuarioSql.USUARIO));
			usuario.setPassword(rs.getString(UsuarioSql.PASSWORD));
			usuario.setId(rs.getInt(EstablecimientoSql.ID_ESTABLECIMIENTO));
			usuario.setEstatus(EstatusUsuario.getById(rs.getInt(UsuarioSql.ID_STATUS)));
			
			return usuario;
		}
	};

}
