package com.casavieja.business.services;

import com.casavieja.business.entities.CiudadEntity;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CiudadS {
    @Transactional(readOnly = true)
    DataTableResults list(HttpServletRequest request);

    @Transactional(readOnly = true)
    ResponseEntity<List<CiudadEntity>> listActive();

    @Transactional
    CiudadEntity save(CiudadEntity value);

    @Transactional
    CiudadEntity update(Short ciudadId, CiudadEntity value);

    @Transactional
    CiudadEntity delete(Short ciudadId);
}
