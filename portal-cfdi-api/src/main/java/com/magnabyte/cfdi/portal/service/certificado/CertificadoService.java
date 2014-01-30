package com.magnabyte.cfdi.portal.service.certificado;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;

public interface CertificadoService {

	CertificadoDigital readVigente(Comprobante comprobante);

}
