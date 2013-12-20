package com.magnabyte.cfdi.portal.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class GenericJdbcDao extends JdbcDaoSupport {

	@Autowired
	@Qualifier("dataSource")
	public void setJdbcDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	protected String assignValues(String string, Object... values) {
		String a = string;
		for(int i = 0; i < values.length; i++) {
			a = a.replaceAll("\\{" + i + "}", values[i].toString());
		}
		return a.toString();
	}
}
