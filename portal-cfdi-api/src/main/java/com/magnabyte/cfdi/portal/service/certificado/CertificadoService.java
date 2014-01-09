package com.magnabyte.cfdi.portal.service.certificado;

import mx.gob.sat.cfd._3.Comprobante;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;

public interface CertificadoService {

	CertificadoDigital readVigente(Comprobante comprobante);

}
