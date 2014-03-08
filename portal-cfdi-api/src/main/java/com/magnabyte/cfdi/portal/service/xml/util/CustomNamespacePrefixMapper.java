package com.magnabyte.cfdi.portal.service.xml.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

@Component("customNamespacePrefixMapper")
public class CustomNamespacePrefixMapper extends NamespacePrefixMapper {

	@Value("${cfdi.uri}")
	private String cfdiUri;
	
	@Value("${cfdi.prefix}")
	private String cfdiPrefix;
	
	@Value("${cfdi.tfd.uri}")
	private String tfdUri;
	
	@Value("${cfdi.tfd.prefix}")
	private String tfdPrefix;

	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		if (cfdiUri.equals(namespaceUri)) {
			return cfdiPrefix;
		}
		if (tfdUri.equals(namespaceUri)) {
			return tfdPrefix;
		}
		return suggestion;
	}

	@Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] {cfdiUri,tfdUri};
    }

	public String getCfdiUri() {
		return cfdiUri;
	}

	public void setCfdiUri(String cfdiUri) {
		this.cfdiUri = cfdiUri;
	}

	public String getCfdiPrefix() {
		return cfdiPrefix;
	}

	public void setCfdiPrefix(String cfdiPrefix) {
		this.cfdiPrefix = cfdiPrefix;
	}

	public String getTfdUri() {
		return tfdUri;
	}

	public void setTfdUri(String tfdUri) {
		this.tfdUri = tfdUri;
	}

	public String getTfdPrefix() {
		return tfdPrefix;
	}

	public void setTfdPrefix(String tfdPrefix) {
		this.tfdPrefix = tfdPrefix;
	}

}
