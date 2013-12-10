package com.magnabyte.cfdi.portal.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SucursalDao {
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
