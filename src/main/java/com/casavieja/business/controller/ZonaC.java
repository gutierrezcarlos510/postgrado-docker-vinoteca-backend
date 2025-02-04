/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.entities.ZonaEntity;
import com.casavieja.business.services.ZonaS;
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
public class ZonaC {

    private final ZonaS zonaS;

    @RequestMapping("zona/listPage")
    public ResponseEntity<DataTableResults> listPage(HttpServletRequest request) {
        return ResponseEntity.ok(zonaS.list(request));

    }

    @PostMapping("zona/save")
    public ResponseEntity<ZonaEntity> save(@Valid @RequestBody ZonaEntity value) {
        ZonaEntity obj = zonaS.save(value);
        return ResponseEntity.ok(obj);
    }

    @PatchMapping("zona/update/{id}")
    public ResponseEntity<ZonaEntity> update(@PathVariable Short id,@Valid @RequestBody ZonaEntity value) {
        ZonaEntity obj = zonaS.update(id, value);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("zona/delete/{id}")
    public ResponseEntity<ZonaEntity> delete(@PathVariable Short id) {
        ZonaEntity obj = zonaS.delete(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("zona/findByCiudad/{ciudadId}")
    public ResponseEntity<List<ZonaEntity>> findByCiudad(@PathVariable Short ciudadId) {
        return zonaS.findByCiudad(ciudadId);
    }
}
