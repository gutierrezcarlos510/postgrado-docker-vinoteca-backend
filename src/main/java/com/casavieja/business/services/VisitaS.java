package com.casavieja.business.services;

import com.casavieja.business.entities.VisitaEntity;
import com.casavieja.business.model.VisitaM;
import com.casavieja.pagination.DataTableResults;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface VisitaS {
    @Transactional(readOnly = true)
    DataTableResults listBySalida(HttpServletRequest request, boolean status, long salidaId);

    VisitaM findById(Long visitaId);

    VisitaEntity save(VisitaEntity value);

    VisitaEntity delete(long visitaId);
}
