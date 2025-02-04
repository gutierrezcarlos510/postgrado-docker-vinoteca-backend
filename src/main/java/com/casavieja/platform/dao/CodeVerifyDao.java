/**
 * 
 */
package com.casavieja.platform.dao;

import com.casavieja.platform.entities.CodeVerify;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@Repository
public interface CodeVerifyDao extends CrudRepository<CodeVerify, Integer> {
	@Query("select cv from CodeVerify cv where cv.celular = ?1 and cv.codigo = ?2 and ?3 between cv.fini and cv.ffin")
	List<CodeVerify> verificarCodigo(String celular, String codigo, Date fecha);
}
