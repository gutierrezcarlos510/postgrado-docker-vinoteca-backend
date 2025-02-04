/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.platform.dao.NotificationDao;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Notification;
import com.casavieja.platform.models.NotificationM;
import com.casavieja.platform.enums.StateNotificationEnum;
import com.casavieja.utils.MyConstants;
import com.casavieja.utils.UtilDataTableS;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
public class NotificationImpl implements NotificationS {

	private final NotificationDao notificationDao;
	private final UtilDataTableS utilDataTableS;
	private final UtilResponseS utilResponseS;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<DataResponse> listActive() {
		List<Notification> lista = notificationDao.findByStatusTrue();
		return new DataResponse(lista, "").getResult(HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("id,title,description,url,param_type_notification,status_notification,date_register,status,system_user_id");
		sql.setFrom("notifications");
		sql.setWhere("status=true");
		DataTableResults<NotificationM> result=utilDataTableS.list(request, NotificationM.class, sql);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Notification> findBySystemUser(Long id) {
		return notificationDao.findBySystemUserIdAndStatusTrue(id);
	}

	@Override
	@Transactional
	public ResponseEntity<DataResponse> save(Notification value) {
		try {
			value.setStatus(MyConstants.ACTIVE);
			value.setDateRegister(new Date());
			if(value.getStatusNotification()==null) {
				value.setStatusNotification(StateNotificationEnum.PENDIENTE.value);
			}
			notificationDao.save(value);
			return new DataResponse<>("Registro exitoso")
					.getResult(HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return utilResponseS.getExceptionService(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<DataResponse> delete(Notification value) {
		try {
			notificationDao.deleteLogic(value.getId());
			return new DataResponse<>("Eliminacion exitosa")
					.getResult(HttpStatus.OK);
		} catch (Exception e) {
			return utilResponseS.getExceptionService(e);
		}
	}
}
