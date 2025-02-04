/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.Menu;

/**
 * @author CARLOS
 *
 */
@Repository
public interface MenuDao extends CrudRepository<Menu, Integer> {
	@Modifying
	@Query("update Menu m set m.status=false where m.id = ?1")
	int deleteLogic(Integer id);
	@Query("select m from Menu m join RolMenu rm on rm.menuId=m.id and rm.rolId = ?1 where m.status = true")
	List<Menu> findByRol(Integer id);
	List<Menu> findByStatusTrue();
}
