package com.magnabyte.cfdi.portal.service.samba;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.magnabyte.cfdi.portal.model.documento.Documento;
import com.magnabyte.cfdi.portal.model.documento.DocumentoCorporativo;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
import com.magnabyte.cfdi.portal.model.ticket.Ticket;

public interface SambaService {

	InputStream getFileStream(String url, String fileName);

	List<DocumentoCorporativo> getFilesFromDirectory(String url);

	boolean ticketExists(Ticket ticket, Establecimiento establecimiento);

	void moveProcessedSapFile(DocumentoCorporativo documento);

	void writeProcessedCfdiXmlFile(byte[] xmlCfdi, Documento documento);

	void writePdfFile(Documento documento, HttpServletRequest request);

	void writeAcuseCfdiXmlFile(byte[] acuseCfdi, Documento documento);

}
