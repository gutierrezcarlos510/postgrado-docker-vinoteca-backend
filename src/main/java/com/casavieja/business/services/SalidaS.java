package com.casavieja.business.services;

import com.casavieja.business.dto.form.SalidaForm;
import com.casavieja.business.entities.SalidaEntity;
import com.casavieja.business.model.SalidaM;
import com.casavieja.pagination.DataTableResults;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface SalidaS {
    @Transactional(readOnly = true)
    DataTableResults<SalidaM> list(HttpServletRequest request, String estadoSalida);

    @Transactional
    SalidaEntity save(SalidaForm value);

    @Transactional
    boolean aumentarEntrega(SalidaForm value);

    SalidaForm findById(Long salidaId);

    @Transactional
    SalidaEntity delete(Long salidaId, Long userId);

    Boolean validarExistenciaSalidaPorDistribuidorEstadoActivo(Long distribuidorId);

    SalidaForm findSalidaActivaByDistribuidor(Long distribuidorId);

    SalidaForm findSalidaFormWithResumenDetalleParaFinalizar(Long salidaId);

    // consumir funcion SalidaDao.finalizarSalida
    boolean finalizarSalida(SalidaForm value);
}
