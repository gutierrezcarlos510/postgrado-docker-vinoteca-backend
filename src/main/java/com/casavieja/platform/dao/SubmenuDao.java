/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.Submenu;

/**
 * @author CARLOS
 *
 */
@Repository
public interface SubmenuDao extends CrudRepository<Submenu, Integer>{
	@Modifying
	@Query("update Submenu s set s.status = false where s.id = ?1")
	int deleteLogic(Integer id);
	
	@Query("select s from Submenu s join MenuSubmenu ms on ms.submenuId = s.id and ms.menuId = ?1 where s.status=true")
	List<Submenu> findByMenu(Integer menuId);

	List<Submenu> findByStatusTrue();
}
