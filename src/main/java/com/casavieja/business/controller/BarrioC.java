/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.entities.BarrioEntity;
import com.casavieja.business.services.BarrioS;
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
public class BarrioC {

    private final BarrioS barrioS;

    @RequestMapping("barrio/listPage")
    public ResponseEntity<DataTableResults> listPage(HttpServletRequest request) {
        return ResponseEntity.ok(barrioS.list(request));

    }

    @PostMapping("barrio/save")
    public ResponseEntity<BarrioEntity> save(@Valid @RequestBody BarrioEntity value) {
        BarrioEntity obj = barrioS.save(value);
        return ResponseEntity.ok(obj);
    }

    @PatchMapping("barrio/update/{id}")
    public ResponseEntity<BarrioEntity> update(@PathVariable Short id,@Valid @RequestBody BarrioEntity value) {
        BarrioEntity obj = barrioS.update(id, value);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("barrio/delete/{id}")
    public ResponseEntity<BarrioEntity> delete(@PathVariable Short id) {
        BarrioEntity obj = barrioS.delete(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("barrio/findByZona/{zonaId}")
    public ResponseEntity<List<BarrioEntity>> findByZona(@PathVariable Short zonaId) {
        return barrioS.findByZona(zonaId);
    }
}
