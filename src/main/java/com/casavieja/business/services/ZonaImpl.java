/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.ZonaDao;
import com.casavieja.business.entities.ZonaEntity;
import com.casavieja.business.model.ZonaM;
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
public class ZonaImpl implements ZonaS {

	private final ZonaDao zonaDao;
	private final UtilDataTableS utilDataTableS;

	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<ZonaEntity>> findByCiudad(Short ciudadId) {
		List<ZonaEntity> lista = zonaDao.findByCiudadIdAndStatusTrue(ciudadId);
		return ResponseEntity.ok(lista);
	}

	
	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("*");
		sql.setFrom("business.zona");
		sql.setWhere("status = true");
		return utilDataTableS.list(request, ZonaM.class, sql);
	}

	
	@Override
	@Transactional
	public ZonaEntity save(ZonaEntity value) {
		return zonaDao.save(value);
	}


	
	@Override
	@Transactional
	public ZonaEntity update(Short zonaId, ZonaEntity value) {
		ZonaEntity zonaDB = zonaDao.findById(zonaId).orElseThrow(()-> new RuntimeException("No existe ID"));
		zonaDB.setNombre(value.getNombre());
		zonaDB.setCiudadId(value.getCiudadId());
		return zonaDao.save(zonaDB);
	}

	
	@Override
	@Transactional
	public ZonaEntity delete(Short zonaId) {
		ZonaEntity zonaDB = zonaDao.findById(zonaId).orElseThrow(()-> new RuntimeException("No existe ID"));
		boolean isDelete = zonaDao.deleteLogic(zonaDB.getId()) > 0;
		if(isDelete) {
			return zonaDB;
		} else {
			throw new RuntimeException("Existe un error al eliminar");
		}
	}

}
