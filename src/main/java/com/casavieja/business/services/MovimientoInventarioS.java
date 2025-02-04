package com.casavieja.business.services;

import com.casavieja.business.dto.form.MovimientoInventarioForm;
import com.casavieja.business.entities.MovimientoInventarioEntity;
import com.casavieja.business.model.MovimientoInventarioM;
import com.casavieja.pagination.DataTableResults;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface MovimientoInventarioS {
    @Transactional(readOnly = true)
    DataTableResults list(HttpServletRequest request, Boolean status);

    @Transactional
    MovimientoInventarioEntity saveIngreso(MovimientoInventarioForm value);

    @Transactional
    MovimientoInventarioEntity saveEgreso(MovimientoInventarioForm value);

    MovimientoInventarioM findById(Long movimientoIngresoId);

    @Transactional
    MovimientoInventarioEntity delete(Long movimientoId, Long userId);

}
