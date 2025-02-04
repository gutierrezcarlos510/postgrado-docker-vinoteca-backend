/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.UserRol;
import com.casavieja.platform.entities.UserRolPK;

/**
 * @author CARLOS
 *
 */
@Repository
public interface UserRolDao extends CrudRepository<UserRol, UserRolPK> {

	public List<UserRol> findBySystemUserId(Long systemUserId);
	@Modifying
	@Query("delete from UserRol ur where ur.systemUserId = ?1")
	void deleteBySystemUser(Long id);
}
