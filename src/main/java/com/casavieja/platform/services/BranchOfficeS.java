/**
 * 
 */
package com.casavieja.platform.services;

import javax.servlet.http.HttpServletRequest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.BranchOffice;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;

/**
 * @author CARLOS
 * Controla las sucursales de una empresa
 */
public interface BranchOfficeS {
	/**
	 * Lista todas las sucursales activas
	 * @return
	 */
	ResponseEntity<DataResponse> listActive() throws Exception;
	/**
	 * Obtiene todas las sucursales de una id de empresa
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> findByCompany(Integer value) throws Exception;
	/**
	 * Lista las sucursales por medio de Datatble
	 * @param request
	 * @return
	 */
	DataTableResults list(HttpServletRequest request, Integer companyId) throws Exception;
	/**
	 * Guarda una sucursal
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> save(BranchOffice value) throws Exception;
	/**
	 * Elimina una sucursal por medio del id
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> delete(BranchOffice value) throws Exception;
}
