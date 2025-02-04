/**
 * 
 */
package com.casavieja.platform.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Task;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;

/**
 * @author CARLOS
 * Controla las tareas del sistema
 */
public interface TaskS {
	/**
	 * Lista las tareas activas del sistema
	 * @return
	 */
	ResponseEntity<DataResponse> listActive() throws Exception;
	/**
	 * Obtiene las tareas de un controlador
	 * @param value
	 * @return
	 */
	List<Task> findByTaskController(Integer value) throws Exception;
	/**
	 * Lista las tareas mediante DataTable
	 * @param request
	 * @return
	 */
	DataTableResults list(HttpServletRequest request) throws Exception;
	/**
	 * Guarda la tarea de un sistema
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> save(Task value) throws Exception;
	/**
	 * Elimina la tarea de un sistema mediante su ID
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> delete(Task value) throws Exception;
	/**
	 * Obtiene las tareas de un rol ID
	 * @param rolId
	 * @return
	 */
	List<Task> findByRol(Integer rolId) throws Exception;
}
