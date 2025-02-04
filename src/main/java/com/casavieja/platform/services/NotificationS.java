/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Notification;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author CARLOS
 *
 */
public interface NotificationS {
	ResponseEntity<DataResponse> listActive();
	DataTableResults list(HttpServletRequest request);
	List<Notification> findBySystemUser(Long id);
	ResponseEntity<DataResponse> save(Notification value);
	ResponseEntity<DataResponse> delete(Notification value);
}
