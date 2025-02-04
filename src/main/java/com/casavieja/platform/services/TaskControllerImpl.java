/**
 *
 */
package com.casavieja.platform.services;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import com.casavieja.platform.dao.ModuleSystemDao;
import com.casavieja.platform.dao.TaskControllerDao;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.TaskController;
import com.casavieja.platform.models.TaskControllerM;
import com.casavieja.utils.UtilDataTableS;
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
public class TaskControllerImpl implements TaskControllerS {

    private final TaskControllerDao taskControllerDao;
    private final ModuleSystemDao moduleSystemDao;
    private final UtilDataTableS utilDataTableS;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<DataResponse> listActive() throws Exception {
        List<TaskController> lista = taskControllerDao.findByStatusTrue();
        return new DataResponse(lista, "").getResult(HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskController> findByModuleSystem(Integer value) throws Exception {
        return taskControllerDao.findByModuleSystemId(value);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResults list(HttpServletRequest request) throws Exception {
        SqlBuilder sql = new SqlBuilder();
        sql.setSelect("task_controller_id, description, module_system_id, name, status");
        sql.setFrom("tasks_controllers");
        sql.setWhere("status=true");
        DataTableResults<TaskControllerM> result = utilDataTableS.list(request, TaskControllerM.class, sql);
        for (TaskControllerM item : result.getListOfDataObjects()) {
            item.setModuleSystem(moduleSystemDao.findById(item.getModuleSystemId()).orElse(null));
        }
        return result;
    }

    @Override
    @Transactional
    public ResponseEntity<DataResponse> save(TaskController value) throws Exception {
        taskControllerDao.save(value);
        return new DataResponse<>("Registro exitoso")
                .getResult(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<DataResponse> delete(TaskController value) throws Exception {
        taskControllerDao.deleteLogic(value.getTaskControllerId());
        return new DataResponse<>("Eliminacion exitosa")
                .getResult(HttpStatus.OK);
    }

}
