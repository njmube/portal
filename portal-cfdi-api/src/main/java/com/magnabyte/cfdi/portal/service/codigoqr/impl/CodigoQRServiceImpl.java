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

import javax.imageio.ImageIO;

import mx.gob.sat.cfd._3.Comprobante;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.magnabyte.cfdi.portal.service.codigoqr.CodigoQRService;

@Service("CodigoQRService")
public class CodigoQRServiceImpl implements CodigoQRService {

	private static final Logger logger = LoggerFactory
			.getLogger(CodigoQRServiceImpl.class);

	public InputStream generaCodigoQR(Comprobante comp)

	{
		logger.debug("Generando Codigo QR");
		InputStream stream = null;
		try {

			Charset charset = Charset.forName("UTF-8");

			CharsetEncoder encoder = charset.newEncoder();

			byte[] b = null;

			String message = "?re=" + comp.getEmisor().getRfc() + "&rr"
					+ comp.getReceptor().getRfc() + "&tt"
					+ comp.getTotal().setScale(6, BigDecimal.ROUND_HALF_UP)
					+ "&id=" + comp.getFolioFiscalOrig();

			ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(message));
			logger.debug("Mensajee" + message);

			b = bbuf.array();

			String data;

			data = new String(b, "UTF-8");

			BitMatrix matrix = null;

			int h = 550;

			int w = 550;

			Writer writer = new QRCodeWriter();

			matrix = writer.encode(data, BarcodeFormat.QR_CODE, w, h);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(baos);
			MatrixToImageWriter.writeToStream(matrix, "PNG", bos);
			bos.flush();
			bos.close();

			BufferedImage bi = ImageIO.read(new ByteArrayInputStream(baos
					.toByteArray()));
			BufferedImage subBi = bi.getSubimage(50, 50, w - 100, h - 100);

			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);
			ImageIO.write(subBi, "PNG", bos);
			bos.flush();
			bos.close();

			stream = new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception e) {
			logger.debug("Error al crear el codigoQr");
			throw new RuntimeException("Error al crear el codigoQr");
		}
		return stream;
	}

}
