package com.casavieja.business.dao;

import com.casavieja.business.entities.IngresoEntity;
import com.casavieja.business.model.IngresoM;
import com.casavieja.business.services.IngresoS;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IngresoDao extends CrudRepository<IngresoEntity, Long> {
    @Modifying
    @Query("update IngresoEntity i set i.status = false where i.id= ?1")
    int deleteLogic(Long id);

    @Query("select coalesce(max(i.numero),0)+1 from IngresoEntity i where i.branchOfficeId = ?1")
    long obtenerNumeroIngreso(int sucursalId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.save_detalles_ingreso( ?1 , ?2 , cast( ?3 as int[]), cast( ?4 as varchar[]), cast( ?5 as varchar[]), cast( ?6 as varchar[]), cast( ?7 as int[]), cast(  ?8 as int[]))")
    void adicionarDetallesIngreso(long ingresoId, int sucursalId, String productos, String lotes,String elaboraciones,
                                 String vencimientos, String unidades, String cajas);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call business.delete_ingreso( ?1 , ?2 )")
    void eliminarIngreso(Long ingresoId, Long userId);

    @Transactional
    @Query(nativeQuery = true,value ="select i.*,concat(p1.name,' ',p1.first_lastname, ' ',p1.second_lastname) as xusuario_recepcion,concat(p2.name,' ',p2.first_lastname, ' ',p2.second_lastname) as xusuario_entrega from business.ingreso i inner join persons p1 on p1.id = i.usuario_recepcion_id inner join persons p2 on p2.id = i.usuario_entrega_id where i.id = ?1")
    IngresoM obtener(Long ingresoId);
}
