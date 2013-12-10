package com.magnabyte.cfdi.portal.service.samba.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.dao.SucursalDao;
import com.magnabyte.cfdi.portal.service.samba.SucursalService;

@Service("sucursalService")
public class SucursalServiceImpl implements SucursalService {

	@Autowired
	private SucursalDao sucursalDao;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		return sucursalDao.loadUserByUsername(username);
	}

}
