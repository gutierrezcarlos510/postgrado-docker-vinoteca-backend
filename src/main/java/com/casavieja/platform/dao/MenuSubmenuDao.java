/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.MenuSubmenu;
import com.casavieja.platform.entities.MenuSubmenuPK;

/**
 * @author CARLOS
 *
 */
@Repository
public interface MenuSubmenuDao extends CrudRepository<MenuSubmenu, MenuSubmenuPK> {
	List<MenuSubmenu> findByMenuId(Integer menuId);
	@Modifying
	@Query("delete from MenuSubmenu m where m.menuId = ?1")
	int deleteAllByMenu(Integer id);
	@Modifying
	@Query(value = "insert into menu_submenu(menu_id,submenu_id) values(?1,?2)", nativeQuery = true)
	void saveAssign(Integer menuId,Integer submenuId);
}
