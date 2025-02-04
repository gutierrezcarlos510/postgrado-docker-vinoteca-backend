package com.casavieja.business.services;

import com.casavieja.business.entities.AlmacenEntity;
import com.casavieja.business.model.AlmacenDistribuidorM;
import com.casavieja.business.model.AlmacenM;
import com.casavieja.business.model.ResumenAlmacenDistribuidorM;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AlmacenS {
    @Transactional(readOnly = true)
    DataTableResults<AlmacenM> list(HttpServletRequest request, Integer sucursalId, String statusFilterStock);

    ResponseEntity<AlmacenM> findByProductoIdTotal(Integer productoId);

    @Transactional(readOnly = true)
    ResponseEntity<List<AlmacenEntity>> findByProductoId(Integer productoId);

    ResponseEntity<List<AlmacenDistribuidorM>> almacenByDistribuidor(Long distribuidorId);

    ResponseEntity<List<ResumenAlmacenDistribuidorM>> listAlmacenDistribuidorBySalida(Long salidaId);
}
