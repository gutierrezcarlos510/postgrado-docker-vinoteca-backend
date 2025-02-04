/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.Company;

/**
 * @author CARLOS
 *
 */
@Repository
public interface CompanyDao extends CrudRepository<Company, Integer> {
	@Modifying
	@Query("update Company c set c.status=false where c.id = ?1")
	int deleteLogic(Integer id);
	List<Company> findByStatusTrue();
	@Query("select distinct c from Company c join BranchOffice b on b.companyId=c.id and c.status = true join Management m on m.branchOfficeId=b.id and b.status=true join UserManagement um on um.managementId=m.id and um.systemUserId = ?1 and um.status=true where c.status=true")
	List<Company> findBySystemUser(Long id);
	@Modifying
	@Query(value="insert into users_managements(user_management_id,management_id,system_user_id,type_operation,status) values((select coalesce(max(user_management_id),0)+1 from users_managements),?1,?2,'READ_WRITE',true)", nativeQuery = true)
	void addUserManagement(Integer managementId, Long userId);
}
