package com.casavieja.business.services;

import com.casavieja.business.entities.CaracteristicaEntity;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CaracteristicaS {
    @Transactional(readOnly = true)
    ResponseEntity<List<CaracteristicaEntity>> findByTipo(String tipoId);

    @Transactional(readOnly = true)
    DataTableResults list(HttpServletRequest request);

    @Transactional
    CaracteristicaEntity save(CaracteristicaEntity value);

    @Transactional
    CaracteristicaEntity update(Short caracteristicaId, CaracteristicaEntity value);

    @Transactional
    CaracteristicaEntity delete(Short caracteristicaId);
}
