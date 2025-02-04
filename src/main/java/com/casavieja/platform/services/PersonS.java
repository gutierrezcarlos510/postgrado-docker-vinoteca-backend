/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Person;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author CARLOS
 * Controla la tabla de personas
 */
public interface PersonS {
	/**
	 * Lista las personas activas
	 * @return
	 */
	ResponseEntity<DataResponse> listActive() throws Exception;
	/**
	 * Lista las personas mediante Datatables
	 * @param request
	 * @return
	 */
	DataTableResults list(HttpServletRequest request) throws Exception;
	/**
	 * Guarda una persona
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> save(Person value) throws Exception;
	/**
	 * Elimina una persona mediante su ID
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> delete(Person value) throws Exception;
	/**
	 * Actualiza la persona mediante su ID
	 * @param value
	 */
	ResponseEntity<DataResponse> update(Person value) throws Exception;

	ResponseEntity<DataResponse> obtener(long personId) throws Exception;
}
