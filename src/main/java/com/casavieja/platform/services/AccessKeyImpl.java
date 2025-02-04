/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.platform.dao.AccessKeyDao;
import com.casavieja.platform.dao.SystemUserDao;
import com.casavieja.platform.data.DataAccessKey;
import com.casavieja.platform.data.DataPasswordForgot;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.AccessKey;
import com.casavieja.platform.entities.SystemUser;
import com.casavieja.platform.enums.TypeAccessEnum;
import com.casavieja.utils.MyConstants;
import com.casavieja.utils.UtilValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
@Transactional
public class AccessKeyImpl implements AccessKeyS {

	private final AccessKeyDao accessKeyDao;
	private final BCryptPasswordEncoder passwordEncoder;
	private final SystemUserDao systemUserDao;
	private final UtilResponseS utilResponseS;

	@Transactional
	public ResponseEntity<DataResponse> changePassword(HttpServletRequest request, DataAccessKey value) {
		List<AccessKey> listAccessUser = accessKeyDao.findBySystemUserIdAndStatusTrueAndIsVerifiedCodeTrue(value.getSystemUserId());
		if(UtilValidation.exist(listAccessUser)) {
			for (AccessKey accessKey : listAccessUser) {
				if(passwordEncoder.matches(value.getPasswordNow(), accessKey.getValueAccess())) {
					AccessKey accessKeyEntity = new AccessKey();
					accessKeyEntity.setSystemUserId(value.getSystemUserId());
					accessKeyEntity.setValueAccess(passwordEncoder.encode(value.getPasswordNew()));
					try {
						accessKeyDao.deleteLogicBySystemUserAndTypeAccess(value.getSystemUserId(), TypeAccessEnum.USER_PASS.value);
						accessKeyEntity.setIsVerifiedCode(MyConstants.ACTIVE);
						accessKeyEntity.setCodeVerification(getCodeVerification());
						accessKeyEntity.setStatus(MyConstants.ACTIVE);
						accessKeyEntity.setTypeAccess(TypeAccessEnum.USER_PASS.value);
						accessKeyDao.save(accessKeyEntity);
						return new DataResponse<>("Se realizo con exito el cambio de contraseña")
								.getResult(HttpStatus.OK);
					} catch (IllegalArgumentException e) {
						return utilResponseS.getExceptionService(e);
					}
				}
			}
			return new DataResponse<>("No se encontró datos de acceso")
					.getResult(HttpStatus.OK);
		} else {
			return new DataResponse<>("No se encontró datos de acceso para esta contraseña. Contraseña actual incorrecta")
					.getResult(HttpStatus.BAD_REQUEST);
		}
	}
	@Transactional
	public ResponseEntity<DataResponse> generatePass(Long systemUserId) {
		if(systemUserId == null) {
			return new DataResponse<>("ID del usuario nulo")
					.getResult(HttpStatus.OK);
		}
		accessKeyDao.deleteLogicBySystemUserAndTypeAccess(systemUserId, TypeAccessEnum.USER_PASS.value);
		AccessKey entityAccess = new AccessKey();
		entityAccess.setSystemUserId(systemUserId);
		entityAccess.setTypeAccess(TypeAccessEnum.USER_PASS.value);
		entityAccess.setValueAccess(passwordEncoder.encode(MyConstants.PASS_DEFAULT));
		entityAccess.setStatus(MyConstants.ACTIVE);
		entityAccess.setIsVerifiedCode(false);
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String dateRegister = format.format( new Date());
		entityAccess.setCodeVerification(passwordEncoder.encode(dateRegister));
		accessKeyDao.save(entityAccess);
		SystemUser user = systemUserDao.findById(systemUserId).orElse(null);
		if(user == null) {
			return new DataResponse<>("No se encontro el usuario").getResult(HttpStatus.BAD_REQUEST);
		}
		return new DataResponse<>("Restablecimiento de contraseña.").getResult(HttpStatus.OK);
	}
	public AccessKey findByCodeRegister(String codeRegister) {
		return accessKeyDao.findByCodeRegister(codeRegister);
	}
	@Transactional
	public ResponseEntity<DataResponse> changePasswordForgot(DataPasswordForgot data) {
		if (data == null) {
			return new DataResponse<>("Datos de acceso nulo").getResult(HttpStatus.BAD_REQUEST);
		}
		AccessKey accessKeyEntity = accessKeyDao.findByCodeRegister(data.getCodeHash());
		if(accessKeyEntity != null) {
			if(data.getPasswordUser().equals(data.getPasswordUserRepeat())) {
				accessKeyEntity.setValueAccess(passwordEncoder.encode(data.getPasswordUser()));
				accessKeyEntity.setIsVerifiedCode(true);
				try {
					accessKeyDao.save(accessKeyEntity);
					return new DataResponse<>("Cambio de clave correctamente, inicie sesión nuevamente.")
							.getResult(HttpStatus.OK);
				} catch (IllegalArgumentException e) {
					return utilResponseS.getExceptionService(e);
				}
			}else {
				return new DataResponse<>("Las claves no coinciden, vuelva a intentarlo.")
						.getResult(HttpStatus.BAD_REQUEST);
			}
		}else {
			return new DataResponse<>("No se logro recuperar los datos de acceso con el codigo de verificacion.")
					.getResult(HttpStatus.BAD_REQUEST);
		}
	}
	@Transactional
	public ResponseEntity<DataResponse> activeAccessKeyRegister(String codeVerification) {
		if(codeVerification == null) {
			return new DataResponse<>("Datos de acceso nulo.")
					.getResult(HttpStatus.BAD_REQUEST);
		}
		AccessKey accessKeyEntity = accessKeyDao.findByCodeRegister(codeVerification);
		if(accessKeyEntity !=null) {
			accessKeyEntity.setIsVerifiedCode(true);
			try {
				accessKeyDao.save(accessKeyEntity);
				return new DataResponse<>("Gracias por confirmar tu suscripcion, te damos la bienvenida y esperamos que sea de agrado nuestro sistema de contabilidad. Cualquier duda o consulta no dudes en escribirnos.")
						.getResult(HttpStatus.OK);
			} catch (IllegalArgumentException e) {
				return utilResponseS.getExceptionService(e);
			}
		}else {
			return new DataResponse<>("No se logro recuperar los datos de acceso con el codigo de verificacion. Esto suele pasar caundo ya se realizo la activacion o su codigo ha sido expirado.")
					.getResult(HttpStatus.BAD_REQUEST);
		}
	}
	@Transactional
	public ResponseEntity<DataResponse> recoveryPasswordByEmail(String mailUser) {
		if(mailUser == null || (mailUser != null && mailUser.isEmpty())) {
			return new DataResponse<>("Email invalido").getResult(HttpStatus.BAD_REQUEST);
		}
		SystemUser systemUser = systemUserDao.findByEmailAndStatusTrue(mailUser);
		if(systemUser == null) {
			return new DataResponse<>("Este correo electronico no esta registrado en el sistema, registrese o consulte con soporte tecnico.")
					.getResult(HttpStatus.BAD_REQUEST);
		}
		if(!systemUser.getStatus()) {
			return new DataResponse<>("El usuario esta dado de baja. Consultar con soporte tecnico el motivo de baja que se dio, ya que en este estado no se puede recuperar su clave.")
					.getResult(HttpStatus.BAD_REQUEST);
		}

		List<AccessKey> accessKeyEntityList = accessKeyDao.findBySystemUserIdAndStatusTrue(systemUser.getId());
		AccessKey accessKeyEntity = UtilValidation.exist(accessKeyEntityList) ? accessKeyEntityList.get(0):null;
		if(accessKeyEntity == null) {
			return new DataResponse<>("No se encontro datos de acceso validos para el usuario. Consultar con soporte tecnico.")
					.getResult(HttpStatus.BAD_REQUEST);
		}
		accessKeyEntity.setStatus(false);
		accessKeyDao.save(accessKeyEntity);
		AccessKey newAccessKey = new AccessKey();
		newAccessKey.setSystemUserId(systemUser.getId());
		newAccessKey.setIsVerifiedCode(false);
		newAccessKey.setStatus(true);
		newAccessKey.setTypeAccess(TypeAccessEnum.USER_PASS.value);
		newAccessKey.setValueAccess(passwordEncoder.encode(MyConstants.PASS_DEFAULT));
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String codeVerification = format.format(new Date());
		newAccessKey.setCodeVerification(passwordEncoder.encode(codeVerification));
		AccessKey accessKey = accessKeyDao.save(newAccessKey);
		return new DataResponse<>("Se envio un mensaje a su correo electronico, para proceder al cambio de clave.")
				.getResult(HttpStatus.OK);
	}
	private String getCodeVerification() {
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String dateRegister = format.format( new Date());
		return passwordEncoder.encode(dateRegister);
	}
}
