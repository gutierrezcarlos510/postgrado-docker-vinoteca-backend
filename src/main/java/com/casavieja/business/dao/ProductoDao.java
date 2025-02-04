package com.casavieja.business.dao;

import com.casavieja.business.entities.ProductoEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductoDao extends CrudRepository<ProductoEntity, Integer> {
    @Modifying
    @Query("update ProductoEntity p set p.status = false where p.id= ?1")
    int deleteLogic(Integer id);

    List<ProductoEntity> findByStatusTrue();

    List<ProductoEntity> findByCategoriaIdAndStatusTrue(Short tipoProductoId);
}
