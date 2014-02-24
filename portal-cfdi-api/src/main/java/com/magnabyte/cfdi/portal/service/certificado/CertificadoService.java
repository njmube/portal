package com.magnabyte.cfdi.portal.service.certificado;

import com.magnabyte.cfdi.portal.model.certificado.CertificadoDigital;
import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 *
 * Interfáz que representa el servicio de certificado
 */
public interface CertificadoService {

	CertificadoDigital readVigente(Comprobante comprobante);

}
