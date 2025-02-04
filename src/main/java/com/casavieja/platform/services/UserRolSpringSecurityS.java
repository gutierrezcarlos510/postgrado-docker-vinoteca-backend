/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.platform.dao.AccessKeyDao;
import com.casavieja.platform.dao.RolDao;
import com.casavieja.platform.dao.SystemUserDao;
import com.casavieja.platform.entities.AccessKey;
import com.casavieja.platform.entities.Rol;
import com.casavieja.platform.entities.SystemUser;
import com.casavieja.platform.enums.TypeAccessEnum;
import com.casavieja.utils.UtilValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CARLOS
 * Controla los usuarios y roles de Spring security
 */
@Slf4j
@RequiredArgsConstructor
@Service("userRolSpringSecurityS")
public class UserRolSpringSecurityS implements UserDetailsService {
	private final SystemUserDao systemUserDao;
	private final RolDao rolDao;
	private final AccessKeyDao accessKeyDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SystemUser systemUser = systemUserDao.findByUsername(username);
		AccessKey pass = null;
		if(systemUser != null) {
			pass = accessKeyDao.findBySystemUserIdAndTypeAccessAndStatusTrueAndIsVerifiedCodeTrue(
					systemUser.getId(), TypeAccessEnum.USER_PASS.value);
		}
		if(systemUser == null || pass == null) {
			systemUser = systemUserDao.findByEmail(username);
			if(systemUser != null) {
				pass = accessKeyDao.findBySystemUserIdAndTypeAccessAndStatusTrueAndIsVerifiedCodeTrue(
						systemUser.getId(), TypeAccessEnum.GMAIL.value);
			}
		}
		if(systemUser == null || pass == null) {
			systemUser = systemUserDao.findByCelular(username);
			if(systemUser != null) {
				pass = accessKeyDao.findBySystemUserIdAndTypeAccessAndStatusTrueAndIsVerifiedCodeTrue(
						systemUser.getId(), TypeAccessEnum.CELULAR.value);
				if(pass != null) {
					systemUser.setUsername(username);
				}
			}
		} else {
			systemUser.setUsername(username);
		}
		if (systemUser == null || pass == null) {
			log.error("Error en el login: no existe el usuario " + username + " en el sistema");
			throw new UsernameNotFoundException(
					"Error en el login: no existe el usuario " + username + " en el sistema");
		}
		List<Rol> rolList = rolDao.findBySystemUser(systemUser.getId());
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (UtilValidation.exist(rolList)) {
			for (Rol rol : rolList) {
				authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
			}
		}
		if (authorities.isEmpty()) {
			log.error("Error en el Login: Usuario '" + username + "' no tiene roles asignados!");
        	throw new UsernameNotFoundException("Error en el Login: usuario '" + username + "' no tiene roles asignados!");
		}
		return new User(systemUser.getUsername(), pass.getValueAccess(), systemUser.getStatus(), true, true, true, authorities);
	}

}
