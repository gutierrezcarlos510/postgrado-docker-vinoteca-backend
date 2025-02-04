/**
 * 
 */
package com.casavieja.platform.services;

import javax.servlet.http.HttpServletRequest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.ModuleSystem;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;

/**
 * @author CARLOS
 * Controla los modulos del sistema
 */
public interface ModuleSystemS {
	/**
	 * Guarda un modulo de un sistema
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> save(ModuleSystem value) throws Exception;
	/**
	 * Elimina un modulo del sistema mediante un ID
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> delete(ModuleSystem value) throws Exception;
	/**
	 * Lista los modulos del sistema mediante Datatable
	 * @param request
	 * @return
	 */
	DataTableResults list(HttpServletRequest request) throws Exception;
	/**
	 * Lista los modulos activos del sistema
	 * @return
	 */
	ResponseEntity<DataResponse> listActive() throws Exception;
}
