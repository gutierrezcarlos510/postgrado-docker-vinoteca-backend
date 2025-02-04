/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.entities.CaracteristicaEntity;
import com.casavieja.business.services.CaracteristicaS;
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
public class CaracteristicaC {

    private final CaracteristicaS caracteristicaS;

    @RequestMapping("caracteristica/listPage")
    public ResponseEntity<DataTableResults> listPage(HttpServletRequest request) {
        return ResponseEntity.ok(caracteristicaS.list(request));

    }

    @PostMapping("caracteristica/save")
    public ResponseEntity<CaracteristicaEntity> save(@Valid @RequestBody CaracteristicaEntity value) {
        CaracteristicaEntity obj = caracteristicaS.save(value);
        return ResponseEntity.ok(obj);
    }

    @PatchMapping("caracteristica/update/{id}")
    public ResponseEntity<CaracteristicaEntity> update(@PathVariable Short id,@Valid @RequestBody CaracteristicaEntity value) {
        CaracteristicaEntity obj = caracteristicaS.update(id, value);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("caracteristica/delete/{id}")
    public ResponseEntity<CaracteristicaEntity> delete(@PathVariable Short id) {
        CaracteristicaEntity obj = caracteristicaS.delete(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("caracteristica/findByTipo/{tipo}")
    public ResponseEntity<List<CaracteristicaEntity>> findByTipo(@PathVariable String tipo) {
        return caracteristicaS.findByTipo(tipo);
    }
}
