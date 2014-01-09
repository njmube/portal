package com.magnabyte.cfdi.portal.dao.certificado.sql;

import com.magnabyte.cfdi.portal.dao.GenericSql;

public class CertificadoSql extends GenericSql {
	
	public static final String READ_VIGENTE;
	
	static {
		StringBuilder qryBuilder = new StringBuilder();
		
		qryBuilder.append("select top 1 id_cfdi_cer, fecha_inicio, fecha_fin, dbo.TRIM(certificado) as certificado, "); 
		qryBuilder.append("dbo.TRIM(ruta_cer) as ruta_cer, dbo.TRIM(nombre_cer) as nombre_cer, dbo.TRIM(ruta_key) as ruta_key, ");
		qryBuilder.append("dbo.TRIM(nombre_key) as nombre_key, dbo.TRIM(passwd) as passwd ");
		qryBuilder.append("from c_cfdi_cer where ? between fecha_inicio and fecha_fin order by fecha_inicio asc");
		
		READ_VIGENTE = qryBuilder.toString();
		clearAndReuseStringBuilder(qryBuilder);
		
	}

}
