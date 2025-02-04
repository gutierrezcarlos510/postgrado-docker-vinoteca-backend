/**
 * 
 */
package com.casavieja.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.casavieja.platform.entities.Person;

/**
 * @author CARLOS
 *
 */
@Repository
public interface PersonDao extends CrudRepository<Person, Long> {
	@Modifying
	@Query("update Person p set p.status=false where p.id = ?1")
	void deleteLogic(Long id);
	List<Person> findByStatusTrue();
	Person findByCodigoCelularAndNumeroCelular(String code, String celular);
	@Query("select p from Person p inner join SystemUser s on s.id = p.id and s.status = true and s.typeSystemUser not in ('ROOT','CLIENT')")
	List<Person> listarUsuariosSistema();
}
