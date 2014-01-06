package com.magnabyte.cfdi.portal.service.xml.util;

import org.springframework.stereotype.Service;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

@Service("customNamespacePrefixMapper")
public class CustomNamespacePrefixMapper extends NamespacePrefixMapper {

	//TODO
	public static final String CFDI_URI = "http://www.sat.gob.mx/cfd/3";
	public static final String CFDI_PREFIX = "cfdi";
	public static final String TFD_URI = "http://www.sat.gob.mx/TimbreFiscalDigital";
	public static final String TFD_PREFIX = "tfd";

	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		if (CFDI_URI.equals(namespaceUri)) {
			return CFDI_PREFIX;
		}
		if (TFD_URI.equals(namespaceUri)) {
			return TFD_PREFIX;
		}
		return suggestion;
	}

	@Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] {CFDI_URI,TFD_URI};
    }
	
}
