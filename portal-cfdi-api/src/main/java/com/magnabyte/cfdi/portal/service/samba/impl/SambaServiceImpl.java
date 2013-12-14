package com.magnabyte.cfdi.portal.service.samba.impl;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import jcifs.Config;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.model.documento.DocumentoFile;
import com.magnabyte.cfdi.portal.service.samba.SambaService;

@Service("sambaService")
public class SambaServiceImpl implements SambaService {

	private static final Logger logger = LoggerFactory.getLogger(SambaServiceImpl.class);

	@Override
	public InputStream getFile(String url, String fileName) {
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

}
