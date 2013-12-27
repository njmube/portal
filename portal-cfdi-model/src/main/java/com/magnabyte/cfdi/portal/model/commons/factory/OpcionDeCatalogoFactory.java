package com.magnabyte.cfdi.portal.model.commons.factory;

import com.magnabyte.cfdi.portal.model.commons.OpcionDeCatalogo;

public class OpcionDeCatalogoFactory {

	public static OpcionDeCatalogo newInstance() {
		return new OpcionDeCatalogo();
	}
	
	public static OpcionDeCatalogo newInstance(int id){
		OpcionDeCatalogo op = newInstance();
		op.setId(id);
		return op; 
	}
	
	public static OpcionDeCatalogo newInstance(String id){
		return newInstance(Integer.parseInt(id));
	}
}
