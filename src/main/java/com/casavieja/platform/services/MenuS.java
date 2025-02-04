/**
 * 
 */
package com.casavieja.platform.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Menu;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;

/**
 * @author CARLOS
 * Controla los menus de roles
 */
public interface MenuS {
	/**
	 * Guarda un menu
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> save(Menu value) throws Exception;
	/**
	 * Elimina un menu mediante un ID
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> delete(Menu value) throws Exception;
	/**
	 * Lista los menus mediante Datatable
	 * @param request
	 * @return
	 */
	DataTableResults list(HttpServletRequest request) throws Exception;
	/**
	 * Guarda la asignacion de submenus a un menu
	 * @param value
	 * @param submenuList
	 * @return
	 */
	ResponseEntity<DataResponse> saveAssign(Integer menuId, List<Integer> submenuList) throws Exception;
	/**
	 * Lista los menus activos
	 * @return
	 */
	ResponseEntity<DataResponse> listActive() throws Exception;
	/**
	 * Obtiene los menus asignados a un rol
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> findByRol(Integer value) throws Exception;
}
