/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.TaskController;

/**
 * @author CARLOS
 *
 */
@Repository
public interface TaskControllerDao extends CrudRepository<TaskController, Integer> {
	@Modifying
	@Query("update TaskController t set t.status=false where t.taskControllerId= ?1")
	void deleteLogic(Integer id);
	List<TaskController> findByStatusTrue();
	List<TaskController> findByModuleSystemId(Integer id);
}
