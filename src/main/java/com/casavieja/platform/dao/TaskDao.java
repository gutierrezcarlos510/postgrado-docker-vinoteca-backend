/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.Task;

/**
 * @author CARLOS
 *
 */
@Repository
public interface TaskDao extends CrudRepository<Task, Integer> {
	@Modifying
	@Query("update Task t set t.status=false where t.taskId=?1")
	void deleteLogic(Integer id);
	List<Task> findByStatusTrue();
	List<Task> findByTaskControllerIdAndStatusTrue(Integer id);
	@Query(value = "select * from tasks t join rol_task rt on t.task_id = rt.task_id and rt.rol_id = ?1 where t.status = true", nativeQuery = true)
	List<Task> findByRol(Integer rolId);
}
