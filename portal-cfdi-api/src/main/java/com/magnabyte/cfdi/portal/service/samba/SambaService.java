package com.magnabyte.cfdi.portal.service.samba;

import java.util.List;

import com.magnabyte.cfdi.portal.model.documento.DocumentoFile;

public interface SambaService {

	List<DocumentoFile> getFilesFromDirectory();

}
