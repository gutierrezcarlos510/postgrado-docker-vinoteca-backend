package com.casavieja.business.services;

import com.casavieja.business.dto.form.PedidoForm;
import com.casavieja.business.entities.PedidoEntity;
import com.casavieja.business.model.PedidoM;
import com.casavieja.pagination.DataTableResults;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface PedidoS {
    @Transactional(readOnly = true)
    DataTableResults<PedidoM> listByDistribuidor(HttpServletRequest request, Boolean status, String tipo, Long distribuidorId);

    @Transactional
    PedidoEntity save(PedidoForm value);

    PedidoM findById(Long pedidoId);

    void delete(Long pedidoId);

    void review(Long pedidoId, String estado);

    void assign(Long pedidoId, Long distribuidorId);
}
