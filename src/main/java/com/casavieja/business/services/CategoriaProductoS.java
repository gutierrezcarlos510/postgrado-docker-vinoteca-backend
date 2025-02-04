package com.casavieja.business.services;

import com.casavieja.business.entities.CategoriaProductoEntity;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CategoriaProductoS {
    @Transactional(readOnly = true)
    ResponseEntity<List<CategoriaProductoEntity>> listActive();

    @Transactional(readOnly = true)
    ResponseEntity<List<CategoriaProductoEntity>> findByTipoProducto(Short tipoProductoId);

    @Transactional(readOnly = true)
    DataTableResults list(HttpServletRequest request);

    @Transactional
    CategoriaProductoEntity save(CategoriaProductoEntity value);

    @Transactional
    CategoriaProductoEntity delete(Short categoriaId);

    @Transactional
    CategoriaProductoEntity update(Short categoriaId, CategoriaProductoEntity value);
}
