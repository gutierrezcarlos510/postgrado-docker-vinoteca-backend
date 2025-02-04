package com.casavieja.business.services;

import com.casavieja.business.dto.form.ProductoForm;
import com.casavieja.business.entities.ProductoEntity;
import com.casavieja.business.model.ProductoM;
import com.casavieja.pagination.DataTableResults;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductoS {
    @Transactional(readOnly = true)
    ResponseEntity<List<ProductoEntity>> listActive();

    @Transactional(readOnly = true)
    ResponseEntity<List<ProductoEntity>> findByCategoria(Short categoriaId);

    @Transactional(readOnly = true)
    DataTableResults<ProductoM> list(HttpServletRequest request);

    @Transactional
    ProductoEntity save(ProductoEntity value);

    ProductoEntity save(ProductoForm value);

    ProductoEntity findById(Integer productoId);

    @Transactional
    ProductoEntity update(Integer productoId, ProductoEntity value);

    @Transactional
    ProductoEntity update(ProductoForm value);

    @Transactional
    ProductoEntity delete(Integer productoId);
}
