package com.casavieja.business.dao;

import com.casavieja.business.entities.CategoriaProductoEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoriaProductoDao extends CrudRepository<CategoriaProductoEntity, Short> {
    @Modifying
    @Query("update CategoriaProductoEntity c set c.status = false where c.id= ?1")
    int deleteLogic(Short id);

    List<CategoriaProductoEntity> findByStatusTrue();

    List<CategoriaProductoEntity> findByTipoProductoIdAndStatusTrue(Short tipoProductoId);
}
