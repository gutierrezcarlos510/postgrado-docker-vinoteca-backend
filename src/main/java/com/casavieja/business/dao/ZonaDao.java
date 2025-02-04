package com.casavieja.business.dao;

import com.casavieja.business.entities.ZonaEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZonaDao extends CrudRepository<ZonaEntity, Short> {
    @Modifying
    @Query("update ZonaEntity z set z.status = false where z.id= ?1")
    int deleteLogic(Short id);

    List<ZonaEntity> findByCiudadIdAndStatusTrue(Short ciudadId);
}
