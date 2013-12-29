package com.magnabyte.cfdi.portal.dao.commons;

import java.util.Collection;

import com.magnabyte.cfdi.portal.model.commons.OpcionDeCatalogo;

public interface OpcionDeCatalogoDao {

	Collection<OpcionDeCatalogo> getCatalogo(String catalogo, String orderBy);
	
	Collection<OpcionDeCatalogo> getCatalogoParam(String catalogo, String campo, String param, String orderBy);
	
	void save(OpcionDeCatalogo opcionDeCatalogo,String catalogo, String campoId);
}
