package com.magnabyte.cfdi.portal.dao;

public class GenericSql {
	
	public static final String SELECT = "SELECT";
	public static final String INSERT = "INSERT INTO";
	public static final String UPDATE = "UPDATE";
	public static final String VALUES = "VALUES";
	public static final String ALL = " * ";
	public static final String PARENTESIS_INIT = "(";
	public static final String PARENTESIS_FIN = ")";
	public static final String TRIM= "dbo.TRIM";
	public static final String SET_PARAM = " = ?";
	public static final String PARAM = "?,";
	public static final String LAST_PARAM = "?";
	public static final String FROM = "FROM";
	public static final String WHERE = "WHERE";
	public static final String INNER = " INNER JOIN ";
	public static final String ORDER = "ORDER BY ";
	public static final String DELETE = "DELETE";
	public static final String AS = " AS ";
	public static final String ON = " ON ";
	public static final String EQ = " = ";
	public static final String DIF = " != ";
	public static final String TAB = "	";
	public static final String EOL = "\n";
	public static final String EOL_ = ",\n";
	public static final String DOT = ".";
	public static final String SET = "SET";
	public static final String AND = "AND";
	
	
	public static void clearAndReuseStringBuilder(final StringBuilder qry) {
		qry.delete(0, qry.length());
	}
}
