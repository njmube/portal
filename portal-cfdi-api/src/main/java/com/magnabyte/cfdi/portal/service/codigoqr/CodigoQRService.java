package com.magnabyte.cfdi.portal.service.codigoqr;

import java.io.InputStream;

import com.magnabyte.cfdi.portal.model.documento.Documento;

public interface CodigoQRService {
	InputStream generaCodigoQR(Documento documento);
}
