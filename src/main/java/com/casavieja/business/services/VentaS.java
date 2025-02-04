package com.casavieja.business.services;

import com.casavieja.business.entities.VentaEntity;
import com.casavieja.business.model.VentaM;
import com.casavieja.pagination.DataTableResults;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface VentaS {
    @Transactional(readOnly = true)
    DataTableResults listByDistribuidor(HttpServletRequest request, Boolean status, Long distribuidorId);

    @Transactional(readOnly = true)
    DataTableResults listBySalida(HttpServletRequest request, Boolean status, Long salidaId);

    @Transactional
    VentaEntity save(VentaM value);

    VentaM findById(Long ventaId);

    @Transactional
    void eliminarVenta(Long ventaId);
}
