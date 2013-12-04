package com.magnabyte.cfdi.portal.service.samba.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import jcifs.Config;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.model.documento.DocumentoFile;
import com.magnabyte.cfdi.portal.service.samba.SambaService;

@Service
public class SambaServiceImpl implements SambaService {

	private static final Logger logger = LoggerFactory
			.getLogger(SambaServiceImpl.class);

	@Override
	public List<DocumentoFile> getFilesFromDirectory() {
		List<DocumentoFile> documentos = new ArrayList<DocumentoFile>();
		Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");
		logger.debug("sambaService...");
		try {
			SmbFile dir = new SmbFile("smb://10.1.200.125/compartido/modatelas/");
			if (dir.exists()) {
				SmbFile[] files = dir.listFiles();

				for (SmbFile file : files) {
					if(file.isFile()) {
						DocumentoFile documento = new DocumentoFile();
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
