package com.casavieja.business.dao;

import com.casavieja.business.entities.AlmacenEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlmacenDao extends CrudRepository<AlmacenEntity, Long> {

    List<AlmacenEntity> findByProductoId(Integer productoId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.procesamiento_resumen()")
    void procesarHistoricoDiario();
}
