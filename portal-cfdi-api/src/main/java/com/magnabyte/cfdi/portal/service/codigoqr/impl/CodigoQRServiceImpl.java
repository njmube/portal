package com.magnabyte.cfdi.portal.service.codigoqr.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.utils.PortalUtils;
import com.magnabyte.cfdi.portal.service.codigoqr.CodigoQRService;

/**
*
* @author  Magnabyte, S.A. de C.V.
* magnabyte.com.mx
* Fecha:31/01/2014
* [Servicio para la generación del codigo QR para la factura]
*/
@Service("CodigoQRService")
public class CodigoQRServiceImpl implements CodigoQRService {

	private static final Logger logger = LoggerFactory
			.getLogger(CodigoQRServiceImpl.class);
	
	@Autowired
	private MessageSource messageSource;

	public InputStream generaCodigoQR(Documento documento) {
		logger.debug("Generando Codigo QR");
		InputStream stream = null;
		try {

			Charset charset = Charset.forName(PortalUtils.encodingUTF8);
			CharsetEncoder encoder = charset.newEncoder();
			byte[] b = null;
			StringBuilder message = new StringBuilder();
			
			message.append("?re=").append(documento.getComprobante().getEmisor().getRfc())
				.append("&rr=").append(documento.getComprobante().getReceptor().getRfc())
				.append("&tt=").append(documento.getComprobante().getTotal().setScale(6, BigDecimal.ROUND_HALF_UP))
				.append("&id=").append(documento.getTimbreFiscalDigital().getUUID());

			ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(message.toString()));
			logger.debug("Mensajee" + message);

			b = bbuf.array();
			String data = new String(b, PortalUtils.encodingUTF8);

			BitMatrix matrix = null;

			int h = 600;
			int w = 600;
			Writer writer = new QRCodeWriter();

			matrix = writer.encode(data, BarcodeFormat.QR_CODE, w, h);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(baos);
			MatrixToImageWriter.writeToStream(matrix, "PNG", bos);
			bos.flush();
			bos.close();

			BufferedImage bi = ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));
			BufferedImage subBi = bi.getSubimage(50, 50, w - 100, h - 100);

			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);
			ImageIO.write(subBi, "PNG", bos);
			bos.flush();
			bos.close();

			stream = new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception e) {
			logger.error(messageSource.getMessage("codigo.qr.error", null, null));
			throw new PortalException(messageSource.getMessage("codigo.qr.error", null, null));
		}
		return stream;
	}

}
