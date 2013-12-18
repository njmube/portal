package com.magnabyte.cfdi.portal.service.samba;

import java.io.InputStream;
import java.util.List;

import com.magnabyte.cfdi.portal.model.documento.DocumentoFile;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public interface SambaService {

	InputStream getFileStream(String url, String fileName);

	List<DocumentoFile> getFilesFromDirectory(String url);

	boolean ticketExists(Ticket ticket, Establecimiento establecimiento);

}
