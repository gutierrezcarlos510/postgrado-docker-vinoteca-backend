/**
 *
 */
package com.casavieja.platform.rest;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.data.MenuAssignForm;
import com.casavieja.platform.entities.Menu;
import com.casavieja.platform.services.MenuS;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author CARLOS
 *
 */
@Tag(name = "Menu", description = "Gestiona los menus del sistema")
@RestController
@RequestMapping("menu/*")
public class MenuC {
    private final MenuS menuS;

    @Autowired
    public MenuC(MenuS menuS) {
        this.menuS = menuS;
    }

    @GetMapping("list")
    public ResponseEntity<DataResponse> listPage() throws Exception {
        return menuS.listActive();
    }

    @PostMapping("save")
    public ResponseEntity<DataResponse> save(@Valid @RequestBody Menu value) throws Exception {
        return menuS.save(value);
    }

    @PostMapping("delete")
    public ResponseEntity<DataResponse> delete(@RequestBody Menu value) throws Exception {
        return menuS.delete(value);
    }

    @PostMapping("saveAssign")
    public ResponseEntity<DataResponse> saveAssign(@RequestBody MenuAssignForm value) throws Exception {
        return menuS.saveAssign(value.getMenuId(), value.getSubmenuList());
    }

    @GetMapping("findByRol/{rolId}")
    public ResponseEntity<DataResponse> findByRol(@PathVariable(name = "rolId") Integer value) throws Exception {
        return menuS.findByRol(value);
    }
    @RequestMapping("listPage")
    public ResponseEntity<DataTableResults<Menu>> listPage(HttpServletRequest request) throws Exception{
        return ResponseEntity.ok(menuS.list(request));
    }
}
