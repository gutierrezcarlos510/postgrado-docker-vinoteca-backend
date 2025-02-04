/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.entities.VisitaEntity;
import com.casavieja.business.model.VisitaM;
import com.casavieja.business.services.VisitaS;
import com.casavieja.pagination.DataTableResults;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author CARLOS
 *
 */
@RestController
@RequiredArgsConstructor
public class VisitaC {

    private final VisitaS visitaS;

    @RequestMapping("visita/listPageBySalida")
    public ResponseEntity<DataTableResults> listPageBySalida(HttpServletRequest request, boolean status, long salidaId) {
        return ResponseEntity.ok(visitaS.listBySalida(request, status, salidaId));

    }

    @PostMapping("visita/save")
    public ResponseEntity<VisitaEntity> save(@Valid @RequestBody VisitaEntity value) {
        VisitaEntity obj = visitaS.save(value);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("visita/delete/{id}")
    public ResponseEntity<VisitaEntity> delete(@PathVariable long id) {
        VisitaEntity obj = visitaS.delete(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("visita/find/{id}")
    public ResponseEntity<VisitaM> find(@PathVariable Long id) {
        return ResponseEntity.ok(visitaS.findById(id));
    }
}
