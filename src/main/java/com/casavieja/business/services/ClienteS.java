package com.casavieja.business.services;

import com.casavieja.business.dto.form.ClienteForm;
import com.casavieja.business.entities.ClienteEntity;
import com.casavieja.pagination.DataTableResults;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface ClienteS {
    @Transactional(readOnly = true)
    DataTableResults list(HttpServletRequest request);

    ClienteForm findById(Long clienteId);

    @Transactional
    ClienteForm update(Long id, ClienteForm value) throws Exception;

    @Transactional
    ClienteEntity delete(Long clienteId);
}
