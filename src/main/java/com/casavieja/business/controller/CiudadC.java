/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.entities.CiudadEntity;
import com.casavieja.business.services.CiudadS;
import com.casavieja.pagination.DataTableResults;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@RestController
@RequiredArgsConstructor
public class CiudadC {

    private final CiudadS ciudadS;

    @RequestMapping("ciudad/listPage")
    public ResponseEntity<DataTableResults> listPage(HttpServletRequest request) {
        return ResponseEntity.ok(ciudadS.list(request));

    }
    @GetMapping("ciudad/list")
    public ResponseEntity<List<CiudadEntity>> list() {
        return ciudadS.listActive();
    }

    @PostMapping("ciudad/save")
    public ResponseEntity<CiudadEntity> save(@Valid @RequestBody CiudadEntity value) {
        CiudadEntity obj = ciudadS.save(value);
        return ResponseEntity.ok(obj);
    }

    @PatchMapping("ciudad/update/{id}")
    public ResponseEntity<CiudadEntity> update(@PathVariable Short id,@Valid @RequestBody CiudadEntity value) {
        CiudadEntity obj = ciudadS.update(id, value);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("ciudad/delete/{id}")
    public ResponseEntity<CiudadEntity> delete(@PathVariable Short id) {
        CiudadEntity obj = ciudadS.delete(id);
        return ResponseEntity.ok(obj);
    }
}
