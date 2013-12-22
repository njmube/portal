package com.magnabyte.cfdi.portal.service.codigoqr;

import java.io.InputStream;

import com.magnabyte.cfdi.portal.model.documento.Documento;

public interface CodigoQRService {
	public InputStream generaCodigoQR(Documento documento);
}
