/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.Management;

/**
 * @author CARLOS
 *
 */
@Repository
public interface ManagementDao extends CrudRepository<Management, Integer> {
	@Modifying
	@Query("update Management m set m.status=false where m.id=?1")
	void deleteLogic(Integer id);
	List<Management> findByStatusTrue();
	List<Management> findByBranchOfficeIdAndStatusTrue(Integer branchOfficeId);
	@Query("select m from Management m join BranchOffice b on m.branchOfficeId=b.id and b.status=true join Company c on c.id=b.companyId and c.status=true and c.id = ?1 where m.status=true")
	List<Management> findByCompany(Integer companyId);
}
