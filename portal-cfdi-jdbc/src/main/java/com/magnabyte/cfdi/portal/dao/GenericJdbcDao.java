package com.magnabyte.cfdi.portal.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase generia que permite la inyección del data source para establecer
 * la conexión de la aplicación con la base de datos
 */
public class GenericJdbcDao extends JdbcDaoSupport {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	@Qualifier("dataSource")
	public void setJdbcDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}
	
	protected String assignValues(String string, Object... values) {
		String a = string;
		for(int i = 0; i < values.length; i++) {
			a = a.replaceAll("\\{" + i + "}", values[i].toString());
		}
		return a.toString();
	}
}
