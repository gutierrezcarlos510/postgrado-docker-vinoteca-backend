package com.casavieja.business.dao;

import com.casavieja.business.entities.VentaEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface VentaDao extends CrudRepository<VentaEntity, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.save_detalles_venta( ?1 , cast( ?2 as int[]), cast( ?3 as int[]), cast( ?4 as numeric[]), cast( ?5 as numeric[]), cast( ?6 as varchar[]))")
    void adicionarDetallesVenta(long ventaId, String productos, String cantidades, String precios, String descuentos, String tipos);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.delete_venta( ?1 , ?2 )")
    void eliminarVenta(Long ventaId, Long salidaId);

    boolean existsVentaEntityBySalidaIdAndStatusTrue(Long salidaId);
}
