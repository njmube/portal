package com.magnabyte.cfdi.portal.service.CodigoQR;

import java.io.InputStream;

import mx.gob.sat.cfd._3.Comprobante;

public interface CodigoQRService {
	public InputStream generaCodigoQR(Comprobante comp);
}
