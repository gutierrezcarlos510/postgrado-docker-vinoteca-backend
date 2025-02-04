/**
 * 
 */
package com.casavieja.platform.services;

import javax.servlet.http.HttpServletRequest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Management;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;

/**
 * @author CARLOS
 * Controla las gestiones de sucursales
 */
public interface ManagementS {
	/**
	 * Lista las gestiones activas de todas las sucursales
	 * @return
	 */
	ResponseEntity<DataResponse> listActive() throws Exception;
	/**
	 * Lista las gestiones de una sucursal mediante su ID
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> findByBranchOffice(Integer value) throws Exception;
	/**
	 * Lista las gestiones mediante Datatable
	 * @param request
	 * @return
	 */
	DataTableResults list(HttpServletRequest request, Integer branchOfficeId) throws Exception;
	/**
	 * Guarda una gestion
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> save(Management value) throws Exception;
	/**
	 * Elimina una sucursal mediante su ID
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> delete(Management value) throws Exception;
}
