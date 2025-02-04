package com.casavieja.business.dao;

import com.casavieja.business.entities.SalidaEntity;
import com.casavieja.business.model.SalidaM;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface SalidaDao extends CrudRepository<SalidaEntity, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.save_salida( ?1 , cast( ?2 as int[]),  cast( ?3 as int[]), cast(  ?4 as int[]))")
    void adicionarDetallesSalida(long salidaId, String productos, String unidades, String cajas);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.save_aumentar_detalle_salida( ?1 , ?2 , ?3 , cast( ?4 as int[]),  cast( ?5 as int[]), cast(  ?6 as int[]))")
    void aumentarDetallesSalida(long salidaId, long userId, String obs, String productos, String unidades, String cajas);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.eliminar_salida( ?1 , ?2 , ?3)")
    void eliminarSalida(Long salidaId, Long userId, String obs);

    @Transactional
    @Query(nativeQuery = true,value ="select s.*,concat(p1.name,' ',p1.first_lastname, ' ',p1.second_lastname) as xusuario,concat(p2.name,' ',p2.first_lastname, ' ',p2.second_lastname) as xdistribuidor from business.salida s inner join persons p1 on p1.id = s.created_by inner join persons p2 on p2.id = s.distribuidor_id where s.id = ?1")
    SalidaM obtener(Long salidaId);

    boolean existsSalidaEntityByDistribuidorIdAndEstadoSalidaAndStatusTrue(Long distribuidorId, String estadoSalida);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.finalizar_salida( ?1 , ?2 , ?3 , ?4 )")
    void finalizarSalida(long salidaId, long userId, String obs, BigDecimal totalEfectivoEntregado);
}
