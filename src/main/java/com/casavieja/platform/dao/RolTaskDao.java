/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.RolTask;
import com.casavieja.platform.entities.RolTaskPK;

/**
 * @author CARLOS
 *
 */
@Repository
public interface RolTaskDao extends CrudRepository<RolTask, RolTaskPK> {
	public List<RolTask> findByRolId(Integer rolId);
	@Modifying
	@Query("delete from RolTask rt where rt.rolId = ?1")
	void deleteAllByRol(Integer id);
	@Modifying
	@Query(value="insert into rol_task(rol_id,task_id) values(?1,?2)",nativeQuery = true)
	void insert(Integer rolId,Integer taskId);
}
