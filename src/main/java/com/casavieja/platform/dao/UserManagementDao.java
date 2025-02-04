/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.UserManagement;

/**
 * @author CARLOS
 *
 */
@Repository
public interface UserManagementDao extends CrudRepository<UserManagement, Long> {
	public List<UserManagement> findBySystemUserIdAndStatusTrue(Long systemUserId);
	public UserManagement findByManagementId(Integer managementId);
	@Modifying
	@Query("delete from UserManagement um where um.systemUserId=?1")
	void deleteBySystemUser(Long id);
}
