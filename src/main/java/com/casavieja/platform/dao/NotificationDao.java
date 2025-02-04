package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.casavieja.platform.entities.Notification;

public interface NotificationDao extends CrudRepository<Notification, Long> {
	@Modifying
	@Query("update Notification n set n.status=false where n.id = ?1")
	int deleteLogic(Long id);
	List<Notification> findByStatusTrue();
	List<Notification> findBySystemUserIdAndStatusTrue(Long id);
}
