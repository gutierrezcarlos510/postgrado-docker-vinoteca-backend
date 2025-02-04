/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.BranchOffice;

/**
 * @author CARLOS
 *
 */
@Repository
public interface BranchOfficeDao extends CrudRepository<BranchOffice, Integer> {
	@Modifying
	@Query("update BranchOffice b set b.status=false where b.id= ?1")
	int deleteLogic(Integer id);
	List<BranchOffice> findByStatusTrue();
	List<BranchOffice> findByCompanyIdAndStatusTrue(Integer id);
}
