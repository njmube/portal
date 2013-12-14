package com.magnabyte.cfdi.portal.service.samba;

import java.io.InputStream;
import java.util.List;

import com.magnabyte.cfdi.portal.model.documento.DocumentoFile;

public interface SambaService {

	InputStream getFile(String url, String fileName);

	List<DocumentoFile> getFilesFromDirectory(String url);

}
