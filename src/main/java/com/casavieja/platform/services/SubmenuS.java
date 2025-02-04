/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.entities.Submenu;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author CARLOS
 * Controla los submenus del sistema
 */
public interface SubmenuS {
	/**
	 * Lista los submenus activos
	 * @return
	 */
	ResponseEntity<List<Submenu>> listActive();
	/**
	 * Guarda los datos de un submenu
	 * @param value
	 * @return
	 */
	ResponseEntity<Submenu> save(Submenu value);
	/**
	 * Eliminar submenu
	 * @param id
	 * @return
	 */
	ResponseEntity delete(Integer id);
	/**
	 * Lista de menus
	 * @param request
	 * @return
	 */
	DataTableResults list(HttpServletRequest request);

	/**
	 * Lsita de submenus por menu
	 * @param value
	 * @return
	 */
	ResponseEntity<List<Submenu>> findByMenu(Integer value);
}
