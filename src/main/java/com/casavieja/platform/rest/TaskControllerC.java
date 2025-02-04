/**
 *
 */
package com.casavieja.platform.rest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.TaskController;
import com.casavieja.platform.services.TaskControllerS;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@Tag(name = "Tarea Controlador", description = "Gestiona la relacion de tareas y controladores del sistema")
@RestController
@RequestMapping("taskController/*")
public class TaskControllerC {
    private final TaskControllerS taskControllerS;

    @Autowired
    public TaskControllerC(TaskControllerS taskControllerS) {
        this.taskControllerS = taskControllerS;
    }

    @RequestMapping("listPage")
    public ResponseEntity<DataResponse> listPage(HttpServletRequest request) throws Exception{
        return new DataResponse(taskControllerS.list(request), "").getResult(HttpStatus.OK);
    }
    @GetMapping("list")
    public ResponseEntity<DataResponse> list() throws Exception {
        return taskControllerS.listActive();
    }

    @PostMapping("save")
    public ResponseEntity<DataResponse> save(@RequestBody TaskController value) throws Exception {
        return taskControllerS.save(value);
    }

    @PostMapping("delete")
    public ResponseEntity<DataResponse> delete(@RequestBody TaskController value) throws Exception {
        return taskControllerS.delete(value);
    }

    @GetMapping("findByModuleSystem/{value}")
    public List<TaskController> findByCompany(@PathVariable Integer value) throws Exception {
        return taskControllerS.findByModuleSystem(value);
    }
}
