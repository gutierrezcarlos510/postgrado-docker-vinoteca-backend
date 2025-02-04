package com.casavieja.platform.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.SystemUser;

import java.util.List;

@Repository
public interface SystemUserDao extends CrudRepository<SystemUser, Long>{
	
	SystemUser findByEmailAndStatusTrue(String email);
	SystemUser findByUsernameAndStatusTrue(String username);
	boolean existsByUsername(String username);
	List<SystemUser> findByEmailOrUsernameOrCelularAndStatusTrue(String email, String username, String celular);
	SystemUser findByEmail(String email);
	SystemUser findByUsername(String username);
	SystemUser findByCelular(String celullar);
	@Modifying
	@Query("update SystemUser s set s.status=false where s.id = ?1")
	void deleteLogic(Long id);
//	@Query("select SystemUser from SystemUser s where s.status = true and s.typeSystemUser not in ('CLIENT','ROOT')")
//	List<SystemUser> listarUsuariosSistema();
}
