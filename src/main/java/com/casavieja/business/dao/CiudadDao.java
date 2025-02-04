package com.casavieja.business.dao;

import com.casavieja.business.entities.CiudadEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CiudadDao extends CrudRepository<CiudadEntity, Short> {
    @Modifying
    @Query("update CiudadEntity c set c.status = false where c.id= ?1")
    int deleteLogic(Short id);

    List<CiudadEntity> findAllByStatusTrue();
}
