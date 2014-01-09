package com.magnabyte.cfdi.portal.dao.certificado;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;

public interface CertificadoDao {
	
	CertificadoDigital readVigente(String fechaComprobante);

}
