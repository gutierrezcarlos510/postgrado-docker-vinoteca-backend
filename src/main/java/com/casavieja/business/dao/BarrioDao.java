package com.casavieja.business.dao;

import com.casavieja.business.entities.BarrioEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BarrioDao extends CrudRepository<BarrioEntity, Short> {
    @Modifying
    @Query("update BarrioEntity b set b.status = false where b.id= ?1")
    int deleteLogic(Short id);

    List<BarrioEntity> findByZonaIdAndStatusTrue(Short zonaId);
}
