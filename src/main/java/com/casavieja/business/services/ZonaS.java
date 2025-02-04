package com.casavieja.business.services;

import com.casavieja.business.entities.ZonaEntity;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ZonaS {
    @Transactional(readOnly = true)
    ResponseEntity<List<ZonaEntity>> findByCiudad(Short ciudadId);

    @Transactional(readOnly = true)
    DataTableResults list(HttpServletRequest request);

    @Transactional
    ZonaEntity save(ZonaEntity value);

    @Transactional
    ZonaEntity update(Short zonaId, ZonaEntity value);

    @Transactional
    ZonaEntity delete(Short zonaId);
}
