/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.AccessKey;

/**
 * @author CARLOS
 *
 */
@Repository
public interface AccessKeyDao extends CrudRepository<AccessKey, Long> {
	AccessKey findBySystemUserIdAndTypeAccessAndStatusTrueAndIsVerifiedCodeTrue(Long systemUserId, String tipoAcceso);
	List<AccessKey> findBySystemUserIdAndStatusTrueAndIsVerifiedCodeTrue(Long systemUserId);
	List<AccessKey> findBySystemUserIdAndStatusTrue(Long systemUserId);
	@Modifying
	@Query("update AccessKey a set a.status = false where a.systemUserId=?1 and a.typeAccess= ?2")
	int deleteLogicBySystemUserAndTypeAccess(Long id,String type);
	@Query("select a from AccessKey a where a.codeVerification = ?1")
	AccessKey findByCodeRegister(String codeRegister);
	AccessKey findBySystemUserIdAndTypeAccessAndStatusTrue(Long systemUserI, String typeAccess);
}