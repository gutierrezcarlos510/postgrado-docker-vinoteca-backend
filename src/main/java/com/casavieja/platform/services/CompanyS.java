/**
 * 
 */
package com.casavieja.platform.services;

import javax.servlet.http.HttpServletRequest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Company;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author CARLOS
 *
 */
public interface CompanyS {
	/**
	 * Lista todas las empresas activas
	 * @return
	 */
	ResponseEntity<List<Company>> listActive() throws Exception;
	/**
	 * Lista las empresas por medio de Datatable
	 * @param request
	 * @return
	 */
	DataTableResults list(HttpServletRequest request) throws Exception;
	/**
	 * Guarda una empresa
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> save(Company value) throws Exception;
	/**
	 * Elimina una empresa por medio del id
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> delete(Company value) throws Exception;
	/**
	 * Obtiene las empresas por medio del id del usuario
	 * @param systemUserId
	 * @return
	 */
	ResponseEntity<DataResponse> findBySystemUser(Long systemUserId) throws Exception;
}
