/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.ModuleSystem;

/**
 * @author CARLOS
 *
 */
@Repository
public interface ModuleSystemDao extends CrudRepository<ModuleSystem, Integer> {
	@Modifying
	@Query("update ModuleSystem m set m.status=false where m.id = ?1")
	int deleteLogic(Integer id);
	List<ModuleSystem> findByStatusTrue();
	@Query("select m from ModuleSystem m where m.name = ?1 and m.id = ?2")
	ModuleSystem findByCodeModule(String code,Integer id);
}
