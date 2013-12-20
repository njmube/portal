package com.magnabyte.cfdi.portal.service.samba.impl;

import java.io.BufferedInputStream;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.model.documento.DocumentoFile;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;
import com.magnabyte.cfdi.portal.model.ticket.TicketForm;
import com.magnabyte.cfdi.portal.service.samba.SambaService;

@Service("sambaService")
public class SambaServiceImpl implements SambaService {

	private static final Logger logger = LoggerFactory.getLogger(SambaServiceImpl.class);

	@Autowired
	private Unmarshaller unmarshaller;
	
	@Override
	public InputStream getFileStream(String url, String fileName) {
		logger.debug("sambaService filename...");
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
			}
		} catch (MalformedURLException e) {
			logger.error("La URL proporcionada no es valida.");
		} catch (SmbException e) {
			logger.error("Ocurrió un error al intentar recuperar el archivo");
		} catch (UnknownHostException e) {
			logger.error("La direccion ip es invalida");
		} 
		
		return null;
	}

	@Override
	public List<DocumentoFile> getFilesFromDirectory(String url) {
		List<DocumentoFile> documentos = new ArrayList<DocumentoFile>();
		Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");
		logger.debug("sambaService documentos...");
		try {
			SmbFile dir = new SmbFile(url);
			if (dir.exists()) {
				SmbFile[] files = dir.listFiles();

				for (SmbFile file : files) {
					if (file.isFile()) {
						DocumentoFile documento = new DocumentoFile();
						documento.setFolio(file.getName().substring(1, 11));
						documento.setNombre(file.getName());
						documentos.add(documento);
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			e.printStackTrace();
		}
		return documentos;
	}

	@Override
	public boolean ticketExists(TicketForm ticketForm, Establecimiento establecimiento) {
		logger.debug("buscando ticket");
		Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");
		String noSucursal = establecimiento.getClave();
		String noCaja = ticketForm.getNoCaja();
		String noTicket = ticketForm.getNoTicket();
		String fechaXml = ticketForm.getFecha();
		String fecha = fechaXml.substring(6, 10) + fechaXml.substring(3, 5) + fechaXml.substring(0, 2);
		logger.debug("fecha {}", fecha);
		String regex = noSucursal + "_" + noCaja + "_" + noTicket + "_" + fecha + "\\d\\d\\d\\d\\d\\d\\.xml";

		Pattern pattern = Pattern.compile(regex);
		SmbFile dir;
		try {
			dir = new SmbFile(establecimiento.getRutaRepositorio());
			if(dir.exists()) {
				SmbFile[] files = dir.listFiles();
				for (SmbFile file : files) {
					Matcher matcher = pattern.matcher(file.getName());
					if (matcher.matches()) {
						Ticket ticketXml = (Ticket) unmarshaller.unmarshal(new StreamSource(getFileStream(establecimiento.getRutaRepositorio(), file.getName())));
						
						if (ticketXml.getTransaccion().getTransaccionTotal().getTotalVenta().compareTo(importe) != 0) {
							return false;
						}
						
						ticketForm.setTicket(ticketXml);
						return true;
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			e.printStackTrace();
		} catch (XmlMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
