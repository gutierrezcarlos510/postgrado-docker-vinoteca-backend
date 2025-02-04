/**
 *
 */
package com.casavieja.platform.rest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.ModuleSystem;
import com.casavieja.platform.services.ModuleSystemS;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author CARLOS
 *
 */
@Tag(name = "Modulos del Sistema", description = "Gestiona los diferentes modulos que tiene el sistema")
@RestController
@RequestMapping("moduleSystem/*")
public class ModuleSystemC {
    private final ModuleSystemS moduleSystemS;

    @Autowired
    public ModuleSystemC(ModuleSystemS moduleSystemS) {
        this.moduleSystemS = moduleSystemS;
    }

    @RequestMapping("listPage")
    public ResponseEntity<DataResponse> listPage(HttpServletRequest request) throws Exception{
        return new DataResponse(moduleSystemS.list(request), "").getResult(HttpStatus.OK);
    }
    @GetMapping("list")
    public ResponseEntity<DataResponse> listPage() throws Exception {
        return moduleSystemS.listActive();
    }

    @PostMapping("save")
    public ResponseEntity<DataResponse> save(@Valid @RequestBody ModuleSystem value) throws Exception {
        return moduleSystemS.save(value);
    }

    @PostMapping("delete")
    public ResponseEntity<DataResponse> delete(@RequestBody ModuleSystem value) throws Exception {
        return moduleSystemS.delete(value);
    }
}
