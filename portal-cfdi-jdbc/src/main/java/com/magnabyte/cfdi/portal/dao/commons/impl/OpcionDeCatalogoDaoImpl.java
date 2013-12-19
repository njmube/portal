package com.magnabyte.cfdi.portal.dao.commons.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.magnabyte.cfdi.portal.dao.GenericJdbcDao;
import com.magnabyte.cfdi.portal.dao.commons.OpcionDeCatalogoDao;
import com.magnabyte.cfdi.portal.model.commons.OpcionDeCatalogo;

@Repository("opcionDeCatalodoDao")
public class OpcionDeCatalogoDaoImpl extends GenericJdbcDao 
	implements OpcionDeCatalogoDao {

	private static final Logger logger = 
			Logger.getLogger(OpcionDeCatalogoDaoImpl.class);
	
	@Override
	public Collection<OpcionDeCatalogo> getCatalogo(String catalogo,
			String orderBy) {
		String qry = assignValues("SELECT * FROM {0} ORDER BY {1}",
				new Object[]{catalogo, orderBy});
		logger.debug(qry);
		return getJdbcTemplate().query(qry, OPCION_DE_CATALOGO_MAPPER);
	}

	@Override
	public Collection<OpcionDeCatalogo> getCatalogoParam(String catalogo,
			String campo, String param, String orderBy) {
		String qry = assignValues("SELECT * FROM {0} WHERE {1} = ? ORDER BY {2}",
				catalogo, campo, orderBy);
		logger.debug(qry);
		return getJdbcTemplate().query(qry, OPCION_DE_CATALOGO_MAPPER, param);
	}
	
	private static final RowMapper<OpcionDeCatalogo> OPCION_DE_CATALOGO_MAPPER = 
			new RowMapper<OpcionDeCatalogo>() {
		
		@Override
		public OpcionDeCatalogo mapRow(ResultSet rs, int rowNum) throws SQLException {
			OpcionDeCatalogo opcion = new OpcionDeCatalogo();
			opcion.setId(rs.getInt(1));
			opcion.setNombre(rs.getString(2));
			return opcion;
		}
	};

}
