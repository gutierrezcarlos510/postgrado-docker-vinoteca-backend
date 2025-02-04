/**
 *
 */
package com.casavieja.platform.rest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Task;
import com.casavieja.platform.services.TaskS;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@Tag(name = "Tareas", description = "Gestiona las tareas a las que puede acceder un usuario dentro del sistema y que estan siendo controladas por Spring Security.")
@RestController
@RequestMapping("task/*")
public class TaskC {
    private final TaskS taskS;

    @Autowired
    public TaskC(TaskS taskS) {
        this.taskS = taskS;
    }

    @RequestMapping("listPage")
    public ResponseEntity<DataResponse> listPage(HttpServletRequest request) throws Exception{
        return new DataResponse(taskS.list(request), "").getResult(HttpStatus.OK);
    }
    @GetMapping("list")
    public ResponseEntity<DataResponse> list() throws Exception {
        return taskS.listActive();
    }

    @PostMapping("save")
    public ResponseEntity<DataResponse> save(@Valid @RequestBody Task value) throws Exception {
        return taskS.save(value);
    }

    @PostMapping("delete")
    public ResponseEntity<DataResponse> delete(@RequestBody Task value) throws Exception {
        return taskS.delete(value);
    }

    @GetMapping("findByRol/{value}")
    public List<Task> findByRol(@PathVariable Integer value) throws Exception {
        return taskS.findByRol(value);
    }
}
