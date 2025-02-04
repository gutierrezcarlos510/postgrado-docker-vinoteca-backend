/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import com.casavieja.platform.dao.ModuleSystemDao;
import com.casavieja.platform.dao.TaskControllerDao;
import com.casavieja.platform.dao.TaskDao;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Task;
import com.casavieja.platform.entities.TaskController;
import com.casavieja.platform.models.TaskM;
import com.casavieja.utils.UtilDataTableS;
import com.casavieja.utils.UtilValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
public class TaskImpl implements TaskS {

	private final TaskDao taskDao;
	private final TaskControllerDao taskControllerDao;
	private final ModuleSystemDao moduleSystemDao;
	private final UtilDataTableS utilDataTableS;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<DataResponse> listActive() throws Exception {
		List<Task> taskList = taskDao.findByStatusTrue();
		if(UtilValidation.exist(taskList)) {
			taskList.forEach(item -> item.setTaskController(taskControllerDao.findById(item.getTaskControllerId()).orElse(null)));
		}
		return new DataResponse(taskList, "").getResult(HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByTaskController(Integer value) throws Exception {
		return taskDao.findByTaskControllerIdAndStatusTrue(value);
	}

	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) throws Exception {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("id, description, name, status, task_controller_id, url");
		sql.setFrom("tasks");
		sql.setWhere("status=true");
		DataTableResults<TaskM> result=utilDataTableS.list(request, TaskM.class, sql);
		for (TaskM task : result.getListOfDataObjects()) {
			TaskController item = taskControllerDao.findById(task.getTaskControllerId()).orElse(null);
			item.setModuleSystem(moduleSystemDao.findById(item.getModuleSystemId()).orElse(null));
			task.setTaskController(item);
		}
		return result;
	}

	@Override
	@Transactional
	public ResponseEntity<DataResponse> save(Task value) throws Exception {
		taskDao.save(value);
		return new DataResponse<>("Registro exitoso")
				.getResult(HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<DataResponse> delete(Task value) throws Exception {
		taskDao.deleteLogic(value.getTaskId());
		return new DataResponse<>("Eliminacion exitosa")
				.getResult(HttpStatus.OK);
	}

	@Override
	public List<Task> findByRol(Integer rolId) throws Exception {
		return taskDao.findByRol(rolId);
	}

}
