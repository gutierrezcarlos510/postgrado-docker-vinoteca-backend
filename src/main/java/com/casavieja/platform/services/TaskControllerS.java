/**
 * 
 */
package com.casavieja.platform.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.TaskController;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;

/**
 * @author CARLOS
 * Controla las tareas de los controladores
 */
public interface TaskControllerS {
	/**
	 * Lista las tareas de controladores activas
	 * @return
	 */
	ResponseEntity<DataResponse> listActive() throws Exception;
	/**
	 * Obtiene las tareas de controladores de un modulo
	 * @param value
	 * @return
	 */
	List<TaskController> findByModuleSystem(Integer value) throws Exception;
	/**
	 * Lista las tareas de controladores mediante DataTable
	 * @param request
	 * @return
	 */
	DataTableResults list(HttpServletRequest request) throws Exception;
	/**
	 * Guarda la tarea de un controlador
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> save(TaskController value) throws Exception;
	/**
	 * Elimina la tarea de un controlador mediante su ID
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> delete(TaskController value) throws Exception;
}
