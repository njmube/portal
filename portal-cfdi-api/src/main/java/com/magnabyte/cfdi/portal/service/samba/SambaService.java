package com.magnabyte.cfdi.portal.service.samba;

import java.util.List;

import mx.gob.sat.cfd._3.Comprobante;

import com.magnabyte.cfdi.portal.model.documento.DocumentoFile;

public interface SambaService {

	Comprobante getFile(String url, String fileName);

	List<DocumentoFile> getFilesFromDirectory(String url);

}
