package com.magnabyte.cfdi.portal.service.samba;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SucursalService {
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
