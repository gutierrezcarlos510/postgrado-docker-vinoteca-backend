/**
 * 
 */
package com.casavieja.platform.services;

import javax.servlet.http.HttpServletRequest;

import com.casavieja.platform.data.DataAccessKey;
import com.casavieja.platform.data.DataPasswordForgot;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.AccessKey;
import org.springframework.http.ResponseEntity;

/**
 * @author CARLOS
 * Controla las claves de acceso de los usuarios 
 */
public interface AccessKeyS {
	/**
	 * Cambiar claves de un usuario
	 * @param request
	 * @param value
	 */
	ResponseEntity<DataResponse> changePassword(HttpServletRequest request, DataAccessKey value);
	/**
	 * Genera un password por defecto para el nuevo usuario
	 * @param systemUserId
	 * @return
	 */
	ResponseEntity<DataResponse> generatePass(Long systemUserId);
	/**
	 * Encuentra la clave de acceso en base a un codigo de registro
	 * @param codeRegister
	 * @return
	 */
	AccessKey findByCodeRegister(String codeRegister);
	/**
	 * Cambia el password cuando se realiza la peticion de olvido de clave pro el usuario
	 * @param data
	 * @return
	 */
	ResponseEntity<DataResponse> changePasswordForgot(DataPasswordForgot data);
	/**
	 * Activa una clave del usuario mediante la verificacion del codigo de registro enviado
	 * @param codeVerification
	 * @return
	 */
	ResponseEntity<DataResponse> activeAccessKeyRegister(String codeVerification);
	/**
	 * Envia un correo al usuario con la recuperacion de la clave
	 * @param mailUser
	 * @return
	 */
	ResponseEntity<DataResponse> recoveryPasswordByEmail(String mailUser);
}
