/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.platform.data.DataRegister;
import com.casavieja.platform.data.DataResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author CARLOS
 * Controla el login de los usuarios
 */
public interface LoginS {
	/**
	 * Guarda los datos de un usuario cuando se registra por el sistema
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> saveRegisterUserSystem(DataRegister value);

    ResponseEntity<DataResponse> refreshtoken(HttpServletRequest request) throws Exception;
}
