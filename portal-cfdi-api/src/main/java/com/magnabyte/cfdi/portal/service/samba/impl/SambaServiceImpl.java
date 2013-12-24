package com.magnabyte.cfdi.portal.service.samba.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.stream.StreamSource;

import jcifs.Config;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.exception.PortalException;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.service.samba.SambaService;

@Service("sambaService")
public class SambaServiceImpl implements SambaService {

	private static final Logger logger = LoggerFactory.getLogger(SambaServiceImpl.class);

	@Autowired
	private Unmarshaller unmarshaller;
	
	@Override
	public InputStream getFileStream(String url, String fileName) {
		logger.debug("sambaService getFileStream...");
		SmbFileInputStream smbIs = null;
		BufferedInputStream bis = null;
		Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");
		SmbFile file;
		try {
			file = new SmbFile(url, fileName);
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
	public List<DocumentoCorporativo> getFilesFromDirectory(String url) {
		List<DocumentoCorporativo> documentos = new ArrayList<DocumentoCorporativo>();
		Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");
		logger.debug("sambaService getFilesFromDirectory...");
		logger.debug(url);
		try {
			SmbFile dir = new SmbFile(url);
			if (dir.exists()) {
				SmbFile[] files = dir.listFiles();
				logger.debug("si hay {}", files.length);
				for (SmbFile file : files) {
					if (file.isFile()) {
						DocumentoCorporativo documento = new DocumentoCorporativo();
						documento.setFolioSap(file.getName().substring(1, 11));
						documento.setNombreXmlPrevio(file.getName());
						documentos.add(documento);
					}
				}
			} else {
				logger.debug("El directorio no existe...");
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
	public boolean ticketExists(Ticket ticket, Establecimiento establecimiento) {
		logger.debug("buscando ticket ticketExists");
		Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");
		String noSucursal = establecimiento.getClave();
		String noCaja = ticket.getTransaccion().getTransaccionHeader().getIdCaja();
		String noTicket = ticket.getTransaccion().getTransaccionHeader().getIdTicket();
		String fechaXml = ticket.getTransaccion().getTransaccionHeader().getFecha();
		BigDecimal importe = ticket.getTransaccion().getTransaccionTotal().getTotalVenta();
		String fecha = fechaXml.substring(6, 10) + fechaXml.substring(3, 5) + fechaXml.substring(0, 2);
		String regex = noSucursal + "_" + noCaja + "_" + noTicket + "_" + fecha + "\\d\\d\\d\\d\\d\\d\\.xml";
		String urlTicketFiles = establecimiento.getRutaRepositorio().getRutaRepositorio() 
				+ establecimiento.getRutaRepositorio().getRutaRepoIn() + fecha + File.separator ; 
		logger.debug("Ruta ticket {}", urlTicketFiles);
		Pattern pattern = Pattern.compile(regex);
		SmbFile dir = null;
		try {
			dir = new SmbFile(urlTicketFiles);
			if(dir.exists()) {
				SmbFile[] files = dir.listFiles();
				for (SmbFile file : files) {
					Matcher matcher = pattern.matcher(file.getName());
					if (matcher.matches()) {
						
						Ticket ticketXml = (Ticket) unmarshaller.unmarshal(new StreamSource(getFileStream(urlTicketFiles, file.getName())));
						
						if (!ticketXml.getTransaccion().getTransaccionHeader().getTipoTransaccion().equalsIgnoreCase("SA") 
								|| ticketXml.getTransaccion().getTransaccionTotal().getTotalVenta().compareTo(importe) != 0) {
							return false;
						}
						ticket.setTransaccion(ticketXml.getTransaccion());
						return true;
					}
				}
			}
		} catch (MalformedURLException e) {
			logger.error("La URL del archivo no es valida: {}", e);
			throw new PortalException("La URL del archivo no es válida: "+ e.getMessage());
		} catch (SmbException e) {
			logger.error("Ocurrió un error al intentar recuperar el ticket: {}", e);
			throw new PortalException("Ocurrió un error al intentar recuperar el ticket: " + e.getMessage());
		} catch (XmlMappingException e) {
			logger.error("Ocurrió un error al leer ticket: {}", e);
			throw new PortalException("Ocurrió un error al leer el ticket: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Ocurrió un error al intentar recuperar el ticket: {}", e);
			throw new PortalException("Ocurrió un error al intentar recuperar el ticket: " + e.getMessage());
		}
		return false;
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
	public void writeProcessedCfdiFile(byte[] xmlCfdi, DocumentoCorporativo documento) {
		logger.debug("Escribir archivo XML CFDI, writeProcessedCfdiFile");
		try {
			String rutaXmlCfdi = documento.getEstablecimiento().getRutaRepositorio().getRutaRepositorio() +
					documento.getEstablecimiento().getRutaRepositorio().getRutaRepoOut();
			//FIXME Modificar nombre cfdi xml
			SmbFile xmlFile = new SmbFile(rutaXmlCfdi, "cfdi.xml");
			SmbFileOutputStream smbFileOutputStream = new SmbFileOutputStream(xmlFile);
			if (!xmlFile.exists()) {
				xmlFile.createNewFile();
			}
			
			smbFileOutputStream.write(xmlCfdi);
			smbFileOutputStream.flush();
			smbFileOutputStream.close();
			logger.debug("Se ha escrito el xml correctamente.");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
