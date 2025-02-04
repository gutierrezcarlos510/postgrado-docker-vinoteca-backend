package com.casavieja.business.dao;

import com.casavieja.business.entities.CaracteristicaEntity;
import com.casavieja.business.entities.CategoriaProductoEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CaracteristicaDao extends CrudRepository<CaracteristicaEntity, Short> {
    @Modifying
    @Query("update CaracteristicaEntity c set c.status = false where c.id= ?1")
    int deleteLogic(Short id);

    List<CaracteristicaEntity> findByTipoAndStatusTrue(String tipoId);
}
