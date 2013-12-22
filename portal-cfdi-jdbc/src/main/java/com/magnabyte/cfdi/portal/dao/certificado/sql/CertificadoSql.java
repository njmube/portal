package com.magnabyte.cfdi.portal.dao.certificado.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

public class CertificadoSql extends GenericSql {
	public static final String TABLE_NAME = "c_cfdi_cer_pac";

	public static final String CERTIFICADO_PAC = "certificado_pac";
	
	public static final String READ_CERTIFICADO;
	
	static {
		StringBuilder qryBuilder = new StringBuilder();
		
		qryBuilder.append(SELECT).append(EOL).append(TAB);
		qryBuilder.append(CERTIFICADO_PAC).append(EOL);
		qryBuilder.append(FROM).append(EOL);
		qryBuilder.append(TABLE_NAME).append(EOL);
		
		READ_CERTIFICADO = qryBuilder.toString();
		qryBuilder = clearAndReuseStringBuilder(qryBuilder);
		
		
	}

}
