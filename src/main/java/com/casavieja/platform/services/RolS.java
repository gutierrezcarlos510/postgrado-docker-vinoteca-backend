/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.data.DataSecurityMatcher;
import com.casavieja.platform.entities.Menu;
import com.casavieja.platform.entities.Rol;
import com.casavieja.platform.models.RolAccesoM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author CARLOS
 * Controla un rol del sistema
 */
public interface RolS {
	/**
	 * Lista los roles activos
	 * @return
	 */
	ResponseEntity<List<Rol>> listActive();
	/**
	 * Lista los roles mediante datatable
	 * @param request
	 * @return
	 */
	DataTableResults list(HttpServletRequest request);
	/**
	 * Obtiene los roles mediante el ID de un usuario
	 * @param id
	 * @return
	 */
	ResponseEntity<List<Rol>> findBySystemUser(Long id);
	/**
	 * Guarda un rol
	 * @param value
	 * @return
	 */
	ResponseEntity<Rol> save(Rol value);
	/**
	 * Elimina un rol mediante su ID
	 * @param value
	 * @return
	 */
	ResponseEntity delete(Rol value);
	/**
	 * Guarda la asignacion de menus a un rol
	 * @param rolId
	 * @param menuList
	 * @return
	 */
	ResponseEntity saveAssign(Integer rolId, List<Integer> menuList);
	/**
	 * Guarda la asignacion de tareas a un rol
	 * @param value
	 * @param vecTask
	 * @return
	 */
	ResponseEntity saveAssignTask(Rol value, List<Integer> vecTask);
	/**
	 * Lista las tareas relacionadas a los roles para el control de seguridad de Spring secutity
	 * @return
	 */
	List<DataSecurityMatcher> listActiveSecurity();

    ResponseEntity<List<Menu>> listarMenuPorRol(Integer rolId);

	ResponseEntity saveRolAcceso(@RequestBody RolAccesoM rolAccesoM);
}
