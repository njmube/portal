package com.magnabyte.cfdi.portal.service.login.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magnabyte.cfdi.portal.dao.establecimiento.EstablecimientoDao;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;
/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:31/01/2014
 *
 * Clase que representa el servicio de login de usuario
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private EstablecimientoDao establecimientoDao;
	
	@Autowired
	private MessageSource messageSource;
	
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		logger.debug("Obteniendo acceso...");
		Establecimiento establecimiento = new Establecimiento();
		establecimiento.setClave(username);
		establecimiento = establecimientoDao.findByClave(establecimiento);
		logger.debug("Recuperando establecimiento" + establecimiento);
		
		if (establecimiento == null) {
			throw new UsernameNotFoundException(messageSource.getMessage("establecimiento.inexistente", null, null));
		}
		
		return buildUserFromEstablecimiento(establecimiento);
	}

	private UserDetails buildUserFromEstablecimiento(Establecimiento establecimiento) {
		String username = establecimiento.getClave();
		String password = establecimiento.getPassword();
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		List<SimpleGrantedAuthority> authorities = getAuthorities(establecimiento);

		User user = new User(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		
		return user;
	}

	private List<SimpleGrantedAuthority> getAuthorities(Establecimiento establecimiento) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(establecimientoDao.getRoles(establecimiento)));
		return authorities;
	}

}
