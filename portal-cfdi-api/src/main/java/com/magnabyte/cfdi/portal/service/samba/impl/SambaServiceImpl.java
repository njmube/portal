package com.magnabyte.cfdi.portal.service.samba.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import jcifs.Config;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import mx.gob.sat.cfd._3.Comprobante;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.documento.DocumentoSucursal;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.service.codigoqr.CodigoQRService;
import com.magnabyte.cfdi.portal.service.samba.SambaService;
import com.magnabyte.cfdi.portal.service.util.NumerosALetras;
import com.magnabyte.cfdi.portal.service.xml.DocumentoXmlService;

@Service("sambaService")
public class SambaServiceImpl implements SambaService {

	private static final Logger logger = LoggerFactory.getLogger(SambaServiceImpl.class);

	@Autowired
	private DocumentoXmlService documentoXmlService;
	
	@Autowired
	private CodigoQRService codigoQRService;
	
	@Override
	public NtlmPasswordAuthentication getAuthentication(Establecimiento establecimiento) {
		NtlmPasswordAuthentication authentication = null;
		if (establecimiento.getSmbDomain() != null && !establecimiento.getSmbDomain().isEmpty()
				&& establecimiento.getSmbUsername() != null && !establecimiento.getSmbUsername().isEmpty()
				&& establecimiento.getSmbPassword() != null && !establecimiento.getSmbPassword().isEmpty()) {
			authentication = new NtlmPasswordAuthentication(establecimiento.getSmbDomain(), 
					establecimiento.getSmbUsername(), establecimiento.getSmbPassword());
		}
		return authentication;
	}
	
	@Override
	public InputStream getFileStream(String url, String fileName, NtlmPasswordAuthentication authentication) {
		SmbFileInputStream smbIs = null;
		BufferedInputStream bis = null;
		Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");
		SmbFile file;
		try {
			file = new SmbFile(url, fileName, authentication);
			if (file.exists()) {
				smbIs = new SmbFileInputStream(file);
				bis = new BufferedInputStream(smbIs);
				return bis;
			} else {
				logger.error("El archivo seleccionado no existe: {}", file.getPath());
				throw new PortalException("El archivo seleccionado no existe: " + file.getPath());
			}
		} catch (MalformedURLException e) {
			logger.error("La URL del archivo no es valida: {}", e);
			throw new PortalException("La URL del archivo no es válida: " + e.getMessage());
		} catch (SmbException e) {
			logger.error("Ocurrió un error al intentar recuperar el archivo: {}", e);
			throw new PortalException("Ocurrió un error al intentar recuperar el archivo: " + e.getMessage());
		} catch (UnknownHostException e) {
			logger.error("La direccion IP es inválida: {}", e);
			throw new PortalException("La direccion IP es inválida: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Ocurrió un error: {}", e);
			throw new PortalException("Ocurrió un error: " + e.getMessage());
		}
		
	}

	@Override
	public List<DocumentoCorporativo> getFilesFromDirectory(String url, NtlmPasswordAuthentication authentication) {
		List<DocumentoCorporativo> documentos = new ArrayList<DocumentoCorporativo>();
		String regex = "^[F|N]\\d{10}[A-Z].+(\\.(?i)(xml))$";
		Pattern pattern = Pattern.compile(regex);
		Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");
		logger.debug("sambaService getFilesFromDirectory...");
		logger.debug(url);
		try {
			SmbFile dir = new SmbFile(url, authentication);
			if (dir.exists()) {
				SmbFile[] files = dir.listFiles();
				logger.debug("archivos en carpeta: {}", files.length);
				for (SmbFile file : files) {
					if (file.isFile()) {
						Matcher matcher = pattern.matcher(file.getName());
						if (matcher.matches() && file.getName().length() > 11) {
							DocumentoCorporativo documento = new DocumentoCorporativo();
							documento.setFolioSap(file.getName().substring(1, 11));
							documento.setNombreXmlPrevio(file.getName());
							documentos.add(documento);
						}
					}
				}
			} else {
				logger.debug("El directorio no existe...");
				throw new PortalException("El directorio no existe...");
			}
		} catch (MalformedURLException e) {
			logger.error("La URL del archivo no es valida: {}", e);
			throw new PortalException("La URL del archivo no es válida: " + e.getMessage());
		} catch (SmbException e) {
			logger.error("Error al leer la carpeta compartida: {}", e);
			throw new PortalException("Error al leer la carpeta compartida: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return documentos;
	}

	@Override
	public void moveProcessedSapFile(DocumentoCorporativo documento) {
		logger.debug("en moveProcessedSapFile");
		try {
			String rutaXmlPrevio = documento.getEstablecimiento().getRutaRepositorio().getRutaRepositorio() +
					documento.getEstablecimiento().getRutaRepositorio().getRutaRepoIn();
			String rutaXmlProcesado = documento.getEstablecimiento().getRutaRepositorio().getRutaRepositorio() +
					documento.getEstablecimiento().getRutaRepositorio().getRutaRepoInProc();
			SmbFile sapFile = new SmbFile(rutaXmlPrevio, documento.getNombreXmlPrevio());
			if (sapFile.exists()) {
				SmbFile smbFileProc = new SmbFile(rutaXmlProcesado, sapFile.getName());
				sapFile.renameTo(smbFileProc);
				if (smbFileProc.exists()) {
					logger.debug("El archivo se procesó y se movió con éxito");
				}
			}
		} catch (MalformedURLException e) {
			logger.error("La URL del archivo a mover no es valida: ", e);
			throw new PortalException("La URL del archivo a mover no es válida: " + e.getMessage());
		} catch (SmbException e) {
			logger.error("Ocurrió un error al mover el archivo SAP procesado: ", e);
			throw new PortalException("Ocurrió un error al mover el archivo SAP procesado: " + e.getMessage());
		}
	}
	
	@Override
	public void writeProcessedCfdiXmlFile(byte[] xmlCfdi, Documento documento) {
		logger.debug("Escribir archivo XML CFDI, writeProcessedCfdiFile");
		String rutaXmlCfdiDestino = documento.getEstablecimiento().getRutaRepositorio().getRutaRepositorio() +
				documento.getEstablecimiento().getRutaRepositorio().getRutaRepoOut();
		logger.debug("Ruta: {}", rutaXmlCfdiDestino);
		String nombreXmlCfdiDestino = documento.getTipoDocumento() + "_" + documento.getComprobante().getSerie() + "_" + documento.getComprobante().getFolio() + ".xml";
		writeFile(xmlCfdi, rutaXmlCfdiDestino, nombreXmlCfdiDestino);
	}
	
	@Override
	public void writeAcuseCfdiXmlFile(byte[] acuseCfdi, Documento documento) {
		logger.debug("Escribir archivo ACUSE XML CFDI, writeAcuseCfdiXmlFile");
		String rutaAcuseXmlCfdiDestino = documento.getEstablecimiento().getRutaRepositorio().getRutaRepositorio() +
				documento.getEstablecimiento().getRutaRepositorio().getRutaRepoOut();
		logger.debug("Ruta: {}", rutaAcuseXmlCfdiDestino);
		String nombreAcuseXmlCfdiDestino = documento.getTipoDocumento() + "_" + documento.getComprobante().getSerie() + "_" + documento.getComprobante().getFolio() + "_acuse" + ".xml";
		writeFile(acuseCfdi, rutaAcuseXmlCfdiDestino, nombreAcuseXmlCfdiDestino);
		
	}
	
	public void writeFile(byte[] file, String destino, String nombreFile) {
		try {
			SmbFile xmlDirectory = new SmbFile(destino);
			SmbFile xmlFile = new SmbFile(xmlDirectory, nombreFile);
			if (!xmlDirectory.exists()) {
				xmlDirectory.mkdir();
			}
			SmbFileOutputStream smbFileOutputStream = new SmbFileOutputStream(xmlFile);
			if (!xmlFile.exists()) {
				xmlFile.createNewFile();
			}
			
			smbFileOutputStream.write(file);
			smbFileOutputStream.flush();
			smbFileOutputStream.close();
			logger.debug("Se ha escrito el xml correctamente.");
		} catch (MalformedURLException e) {
			logger.error("Ocurrió un error, la URL del archivo es inválida: {}", e);
			throw new PortalException("Ocurrió un error, la URL del archivo es inválida: " + e.getMessage());
		} catch (SmbException e) {
			logger.error("Ocurrió un error al escribir el archivo: {}", e);
			throw new PortalException("Ocurrió un error al escribir el archivo: " + e.getMessage());
		} catch (UnknownHostException e) {
			logger.error("Ocurrió un error al escribir el archivo: {}", e);
			throw new PortalException("Ocurrió un error al escribir el archivo: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Ocurrió un error al escribir el archivo: {}", e);
			throw new PortalException("Ocurrió un error al escribir el archivo: " + e.getMessage());
		}
	}
	
	@Override
	public void writePdfFile(Documento documento, HttpServletRequest request) {
		logger.debug("Creando reporte");
		JasperPrint reporteCompleto = null;
		String reporteCompilado = request.getSession().getServletContext().getRealPath("WEB-INF/reports/ReporteFactura.jasper");

		Locale locale = new Locale("es", "MX");
		List<Comprobante> comprobantes = new ArrayList<Comprobante>();
		comprobantes.add(documento.getComprobante());
		String pathImages = request.getSession().getServletContext().getRealPath("resources/img");
		Map<String, Object> map = new HashMap<String, Object>();
		if (documento instanceof DocumentoCorporativo) {
			map.put("FOLIO_SAP", ((DocumentoCorporativo) documento).getFolioSap());
		} else if (documento instanceof DocumentoSucursal) {
			map.put("SUCURSAL", documento.getEstablecimiento().getNombre());
		}
		
		map.put("TIPO_DOC", documento.getTipoDocumento().getNombre());
		map.put(JRParameter.REPORT_LOCALE, locale);
		map.put("NUM_SERIE_CERT", documentoXmlService.obtenerNumCertificado(documento.getXmlCfdi()));
		map.put("SELLO_CFD", documento.getTimbreFiscalDigital().getSelloCFD());
		map.put("SELLO_SAT", documento.getTimbreFiscalDigital().getSelloSAT());
		map.put("FECHA_TIMBRADO", documento.getTimbreFiscalDigital().getFechaTimbrado());
		map.put("FOLIO_FISCAL", documento.getTimbreFiscalDigital().getUUID());
		map.put("CADENA_ORIGINAL", documento.getCadenaOriginal());
		map.put("PATH_IMAGES", pathImages);
		map.put("QRCODE", codigoQRService.generaCodigoQR(documento));
		map.put("LETRAS", NumerosALetras.convertNumberToLetter(documento.getComprobante().getTotal().toString()));
		map.put("REGIMEN", documento.getComprobante().getEmisor().getRegimenFiscal().get(0).getRegimen());

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(comprobantes);

		try {
			reporteCompleto = JasperFillManager.fillReport(reporteCompilado, map, dataSource);
			byte[] bytesReport = JasperExportManager.exportReportToPdf(reporteCompleto);
			SmbFile filePdf = new SmbFile(documento.getEstablecimiento().getRutaRepositorio().getRutaRepositorio() 
					+ documento.getEstablecimiento().getRutaRepositorio().getRutaRepoOut(), 
					documento.getTipoDocumento() + "_" + documento.getComprobante().getSerie() + "_" + documento.getComprobante().getFolio() + ".pdf");
			SmbFileOutputStream out = new SmbFileOutputStream(filePdf);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			bos.write(bytesReport);
			bos.flush();
			bos.close();
		} catch (JRException e) {
			logger.error("Ocurrió un error al crear el PDF: {}", e);
			throw new PortalException("Ocurrió un error al crear el PDF: " + e.getMessage());
		} catch (FileNotFoundException e) {
			logger.error("Ocurrió un error al crear el PDF: {}", e);
			throw new PortalException("Ocurrió un error al crear el PDF: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Ocurrió un error al crear el PDF: {}", e);
			throw new PortalException("Ocurrió un error al crear el PDF: " + e.getMessage());
		}
		
	}
	
}
