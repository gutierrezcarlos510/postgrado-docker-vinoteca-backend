/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.CiudadDao;
import com.casavieja.business.entities.CiudadEntity;
import com.casavieja.business.model.CiudadM;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import com.casavieja.utils.UtilDataTableS;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
public class CiudadImpl implements CiudadS {

	private final CiudadDao ciudadDao;
	private final UtilDataTableS utilDataTableS;
	
	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("id, nombre, status");
		sql.setFrom("business.ciudad");
		sql.setWhere("status = true");
		return utilDataTableS.list(request, CiudadM.class, sql);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<CiudadEntity>> listActive() {
		List<CiudadEntity> lista = ciudadDao.findAllByStatusTrue();
		return ResponseEntity.ok(lista);
	}
	
	@Override
	@Transactional
	public CiudadEntity save(CiudadEntity value) {
		return ciudadDao.save(value);
	}


	
	@Override
	@Transactional
	public CiudadEntity update(Short ciudadId, CiudadEntity value) {
		CiudadEntity ciudadDB = ciudadDao.findById(ciudadId).orElseThrow(()-> new RuntimeException("No existe ID"));
		ciudadDB.setNombre(value.getNombre());
		return ciudadDao.save(ciudadDB);
	}

	
	@Override
	@Transactional
	public CiudadEntity delete(Short ciudadId) {
		CiudadEntity ciudadDB = ciudadDao.findById(ciudadId).orElseThrow(()-> new RuntimeException("No existe ID"));
		boolean isDelete = ciudadDao.deleteLogic(ciudadDB.getId()) > 0;
		if(isDelete) {
			return ciudadDB;
		} else {
			throw new RuntimeException("Existe un error al eliminar");
		}
	}

}
