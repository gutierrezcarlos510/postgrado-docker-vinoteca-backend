package com.casavieja.business.services;

import com.casavieja.business.entities.BarrioEntity;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BarrioS {
    @Transactional(readOnly = true)
    ResponseEntity<List<BarrioEntity>> findByZona(Short zonaId);

    @Transactional(readOnly = true)
    DataTableResults list(HttpServletRequest request);

    @Transactional
    BarrioEntity save(BarrioEntity value);

    @Transactional
    BarrioEntity update(Short barrioId, BarrioEntity value);

    @Transactional
    BarrioEntity delete(Short barrioId);
}
