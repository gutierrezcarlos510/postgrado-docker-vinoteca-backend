package com.casavieja.platform.rest;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.entities.Submenu;
import com.casavieja.platform.services.SubmenuS;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author cgutierre
 */
@Tag(name = "Submenu", description = "Gestiona los submenus o accesos a las diferentes vistas")
@RequiredArgsConstructor
@RestController
@RequestMapping("submenu/*")
public class SubmenuC {
    private final SubmenuS submenuS;

    @GetMapping("list")
    public ResponseEntity<List<Submenu>> list() {
        return submenuS.listActive();
    }

    @RequestMapping("save")
    public ResponseEntity<Submenu> save(@Valid @RequestBody Submenu value) {
        return submenuS.save(value);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) throws Exception {
        return submenuS.delete(id);
    }

    @GetMapping("findByMenu/{menuId}")
    public ResponseEntity<List<Submenu>> findByMenu(@PathVariable(name = "menuId") Integer value) {
        return submenuS.findByMenu(value);
    }
    @RequestMapping("listPage")
    public ResponseEntity<DataTableResults<Submenu>> listPage(HttpServletRequest request) {
        return ResponseEntity.ok(submenuS.list(request));
    }
}
