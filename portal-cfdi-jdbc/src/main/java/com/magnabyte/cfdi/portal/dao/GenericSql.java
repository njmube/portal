package com.magnabyte.cfdi.portal.dao;

public class GenericSql {
	public static StringBuilder clearAndReuseStringBuilder(final StringBuilder qry) {
		qry.delete(0, qry.length());
		return qry;
	}
}
