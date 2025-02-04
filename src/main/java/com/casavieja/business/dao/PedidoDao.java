package com.casavieja.business.dao;

import com.casavieja.business.entities.PedidoEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PedidoDao extends CrudRepository<PedidoEntity, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.save_detalles_pedido( ?1 , cast( ?2 as int[]), cast( ?3 as int[]), cast( ?4 as numeric[]), cast( ?5 as varchar[]))")
    void adicionarDetallesPedido(long pedidoId, String productos, String cantidades, String precios, String tipos);
    @Transactional
    @Modifying
    @Query("update PedidoEntity p set p.status = false where p.id= ?1")
    int deleteLogic(Long id);
    @Transactional
    @Modifying
    @Query("update PedidoEntity p set p.estado = ?1 where p.id= ?2")
    int revisarEstadoPedido(String estado, Long id);
    @Transactional
    @Modifying
    @Query("update PedidoEntity p set p.distribuidorId =?1, p.estado = ?2 where p.id= ?3")
    int asignarDistribuidorPedido(Long idDistribuidor, String estadoPedido, Long idPedido);
}
