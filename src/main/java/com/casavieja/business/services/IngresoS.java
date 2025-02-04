package com.casavieja.business.services;

import com.casavieja.business.dto.form.IngresoForm;
import com.casavieja.business.entities.IngresoEntity;
import com.casavieja.business.model.IngresoM;
import com.casavieja.pagination.DataTableResults;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface IngresoS {
    @Transactional(readOnly = true)
    DataTableResults list(HttpServletRequest request, Boolean status);

    @Transactional
    IngresoEntity save(IngresoForm value);

    IngresoM findById(Long ingresoId);

    @Transactional
    IngresoEntity delete(Long ingresoId, Long userId);
}
