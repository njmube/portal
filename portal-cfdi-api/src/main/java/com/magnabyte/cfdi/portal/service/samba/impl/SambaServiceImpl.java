package com.magnabyte.cfdi.portal.service.samba.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import jcifs.Config;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import mx.gob.sat.cfd._3.Comprobante;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.model.documento.DocumentoFile;
import com.magnabyte.cfdi.portal.service.samba.SambaService;

@Service
public class SambaServiceImpl implements SambaService {

	private static final Logger logger = LoggerFactory.getLogger(SambaServiceImpl.class);
	
	@Autowired
	private Unmarshaller unmarshaller;

	@Override
	public Comprobante getFile(String url, String fileName) {
		SmbFileInputStream smbIs = null;
		BufferedInputStream bis = null;
		Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");
		logger.debug("sambaService filename...");
		try {
			SmbFile file = new SmbFile(url, fileName);
			if (file.exists()) {
				smbIs = new SmbFileInputStream(file);
				bis = new BufferedInputStream(smbIs);
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				int length = (int) file.length();
//				byte [] arrBuffer = new byte [length];
//				int leido = 0;
//				while ((leido = bis.read(arrBuffer)) >= 0) {
//					baos.write(arrBuffer, 0, leido);
//				}
				Comprobante comprobante = (Comprobante) unmarshaller.unmarshal(new StreamSource(bis));
				return comprobante;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (smbIs != null) {
				try {
					smbIs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
					if(file.isFile()) {
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
