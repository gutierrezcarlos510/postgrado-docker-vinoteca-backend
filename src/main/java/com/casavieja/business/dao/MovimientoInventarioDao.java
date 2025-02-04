package com.casavieja.business.dao;

import com.casavieja.business.entities.MovimientoInventarioEntity;
import com.casavieja.business.model.MovimientoInventarioM;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MovimientoInventarioDao extends CrudRepository<MovimientoInventarioEntity, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.save_movimiento_ingreso( ?1 , ?2 , cast( ?3 as int[]), cast( ?4 as varchar[]), cast( ?5 as varchar[]), cast( ?6 as varchar[]), cast( ?7 as int[]), cast(  ?8 as int[]))")
    void adicionarDetallesMovimientoIngreso(long movimientoIngresoId, int sucursalId, String productos, String lotes,String elaboraciones,
                                 String vencimientos, String unidades, String cajas);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.delete_movimiento_ingreso( ?1 , ?2 )")
    void eliminarDetallesMovimientoIngreso(Long ingresoId, Long userId);

    @Transactional
    @Query(nativeQuery = true,value ="select mi.*,concat(p1.name,' ',p1.first_lastname, ' ',p1.second_lastname) as xusuario,b.name xsucursal_destino from business.movimiento_inventario mi inner join persons p1 on p1.id = mi.usuario_id left join branch_offices b on mi.sucursal_destino = b.id where mi.id = ?1")
    MovimientoInventarioM obtener(Long movimientoIngresoId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.save_movimiento_egreso( ?1 , ?2 , cast( ?3 as int[]), cast( ?4 as int[]), cast( ?5 as int[]))")
    void adicionarDetallesMovimientoEgreso(long movimientoEgresoId, int sucursalId, String productos, String unidades, String cajas);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.delete_movimiento_egreso( ?1 , ?2 )")
    void eliminarDetallesMovimientoEgreso(Long egresoId, Long userId);
}
