/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.RolMenu;
import com.casavieja.platform.entities.RolMenuPK;

/**
 * @author CARLOS
 *
 */
@Repository
public interface RolMenuDao extends CrudRepository<RolMenu, RolMenuPK>{
	public List<RolMenu> findByRolId(Integer rolId);
	@Modifying
	@Query("delete from RolMenu rm where rm.rolId = ?1")
	void deleteAllByRol(Integer id);
	@Modifying
	@Query(value = "insert into rol_menu(rol_id,menu_id) values(?1,?2)", nativeQuery = true)
	void saveAssign(Integer rol,Integer menu);
}
