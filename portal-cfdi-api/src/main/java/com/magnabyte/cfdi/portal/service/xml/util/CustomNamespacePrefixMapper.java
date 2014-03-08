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
	
	@Value("${cfdi.leyfisc.uri}")
	private String leyfiscUri;
	
	@Value("${cfdi.leyfisc.prefix}")
	private String leyfiscPrefix;

	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		if (cfdiUri.equals(namespaceUri)) {
			return cfdiPrefix;
		}
		if (tfdUri.equals(namespaceUri)) {
			return tfdPrefix;
		}
		if (leyfiscPrefix.equals(namespaceUri)) {
			return leyfiscPrefix;
		}
		return suggestion;
	}

	@Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] {cfdiUri, tfdUri, leyfiscPrefix};
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
	
	public String getLeyfiscUri() {
		return leyfiscUri;
	}
	
	public void setLeyfiscUri(String leyfiscUri) {
		this.leyfiscUri = leyfiscUri;
	}
	
	public String getLeyfiscPrefix() {
		return leyfiscPrefix;
	}
	
	public void setLeyfiscPrefix(String leyfiscPrefix) {
		this.leyfiscPrefix = leyfiscPrefix;
	}

}
