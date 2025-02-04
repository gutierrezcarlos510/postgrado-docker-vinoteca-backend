package com.casavieja.business.dao;

import com.casavieja.business.entities.VisitaEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface VisitaDao extends CrudRepository<VisitaEntity, Long> {
    @Transactional
    @Modifying
    @Query("update VisitaEntity v set v.status = false where v.id= ?1")
    int deleteLogic(Long id);
}
