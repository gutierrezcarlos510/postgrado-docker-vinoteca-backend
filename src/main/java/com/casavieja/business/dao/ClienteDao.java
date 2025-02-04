package com.casavieja.business.dao;

import com.casavieja.business.entities.ClienteEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDao extends CrudRepository<ClienteEntity, Long> {
    @Modifying
    @Query("update ClienteEntity c set c.status = false where c.id= ?1")
    int deleteLogic(Long id);

}
