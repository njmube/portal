package com.magnabyte.cfdi.portal.service.xml.util;

import java.util.Map;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class CustomNamespacePrefixMapper extends NamespacePrefixMapper {

	private final Map<String, String> prefixesMap;
	
	public CustomNamespacePrefixMapper(Map<String, String> prefixesMap) {
		this.prefixesMap = prefixesMap;
	}
	
	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		String uri = prefixesMap.get(namespaceUri);
		return (uri != null) ? uri : suggestion;
	}

	@Override
    public String[] getPreDeclaredNamespaceUris() {
		return prefixesMap.keySet().toArray(new String[0]);
    }

}
