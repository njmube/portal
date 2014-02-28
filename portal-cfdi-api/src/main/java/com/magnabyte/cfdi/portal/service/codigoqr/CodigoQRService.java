package com.magnabyte.cfdi.portal.service.codigoqr;

import java.io.InputStream;

import com.magnabyte.cfdi.portal.model.documento.Documento;

/**
*
* @author  Magnabyte, S.A. de C.V.
* magnabyte.com.mx
* Fecha:31/01/2014
* [Interfaz que hace referencia al servicio CodigoQRService]
*/
public interface CodigoQRService {
	InputStream generaCodigoQR(Documento documento);
}
