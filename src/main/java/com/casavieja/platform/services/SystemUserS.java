/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.auth.DataToken;
import com.casavieja.platform.data.DataAvatar;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.data.FileInput;
import com.casavieja.platform.models.PasswordChangeRequestM;
import com.casavieja.platform.entities.Person;
import com.casavieja.platform.entities.SystemUser;
import com.casavieja.platform.models.form.UserForm;
import com.casavieja.platform.models.form.UserMain;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author CARLOS
 * Controla los usuatios del sistema
 */
public interface SystemUserS {

	@Transactional(readOnly = true)
    SystemUser findByEmail(String value) throws DataAccessException;

	@Transactional(readOnly = true)
	SystemUser findByUsername(String value) throws DataAccessException;

    Boolean encontrarusuario(String value);

    /**
	 * Actualiza los datos de un usuario del sistema
	 * @param value
	 */
	ResponseEntity<DataResponse> update(SystemUser value) throws Exception;
	/**
	 * Actualiza el avatar de un usuario
	 * @param value
	 * @param user
	 */
	ResponseEntity<DataResponse> changeAvatar(FileInput value, SystemUser user) throws Exception;
	/**
	 * Actualiza el avatar de un usuario por un avatar interno que provee el sistema
	 * @param value
	 */
	ResponseEntity<DataResponse> changeAvatarInternal(DataAvatar value) throws Exception;
	/**
	 * Elimina el avatar de un usuario
	 * @param user
	 */
	ResponseEntity<DataResponse> deleteAvatar(SystemUser user) throws Exception;

	abstract ResponseEntity obtenerPorUsuarioWizard(Long systemUserId) throws Exception;

    DataToken obtenerParaToken(String username) throws Exception;

    /**
	 * Elimina un usuario del sistema
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> delete(SystemUser value) throws Exception;
	/**
	 * Guarda el usuario del sistema
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> save(SystemUser value) throws Exception;
	ResponseEntity<DataResponse> saveWizard(UserForm value) throws Exception;
	/**
	 * Lista los usuarios de un sistema mediante Datatable
	 * @param request
	 * @return
	 */
	DataTableResults list(HttpServletRequest request) throws Exception;
	/**
	 * Guarda un avatar de un usuario del sistema
	 * @param value
	 * @return
	 */
	ResponseEntity<DataResponse> saveAvatar(SystemUser value) throws Exception;

	ResponseEntity<UserMain> obtenerUsuarioParaPrincipal(Long systemUserId) throws Exception;

	List<Person> listarUsuariosSistema();

    @Transactional
	ResponseEntity<DataResponse> changePassword(PasswordChangeRequestM passwordChangeRequest) throws Exception;

	@Transactional
	ResponseEntity<DataResponse> resetPassword(Long systemUserId);
}
