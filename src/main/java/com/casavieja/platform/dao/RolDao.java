/**
 *
 */
package com.casavieja.platform.dao;

import com.casavieja.platform.entities.Rol;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author CARLOS
 *
 */
@Repository
public interface RolDao extends CrudRepository<Rol, Integer> {
    @Modifying
    @Query("update Rol r set r.status=false where r.id = ?1")
    int deleteLogic(Integer id);

    @Query("select r from Rol r join UserRol ur on ur.rolId=r.id and ur.systemUserId = ?1 where r.status=true")
    List<Rol> findBySystemUser(Long id);

    List<Rol> findByStatusTrue();
    @Modifying
    @Query(value="insert into user_rol(system_user_id, rol_id) values(?,?)", nativeQuery = true)
    void assignUserRol(Long userId,Integer rolId);
    @Modifying
    @Query(value = "delete from user_rol where system_user_id = ?1", nativeQuery = true)
    void eliminarAsignacionRolPorUsuario(Long systemUserId);
}
