/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.BarrioDao;
import com.casavieja.business.entities.BarrioEntity;
import com.casavieja.business.model.BarrioM;
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
public class BarrioImpl implements BarrioS {

	private final BarrioDao barrioDao;
	private final UtilDataTableS utilDataTableS;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<BarrioEntity>> findByZona(Short zonaId) {
		List<BarrioEntity> lista = barrioDao.findByZonaIdAndStatusTrue(zonaId);
		return ResponseEntity.ok(lista);
	}
	
	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("*");
		sql.setFrom("business.barrio");
		sql.setWhere("status = true");
		return utilDataTableS.list(request, BarrioM.class, sql);
	}

	@Override
	@Transactional
	public BarrioEntity save(BarrioEntity value) {
		return barrioDao.save(value);
	}

	@Override
	@Transactional
	public BarrioEntity update(Short barrioId, BarrioEntity value) {
		BarrioEntity barrioDB = barrioDao.findById(barrioId).orElseThrow(()-> new RuntimeException("No existe ID"));
		barrioDB.setNombre(value.getNombre());
		barrioDB.setZonaId(value.getZonaId());
		return barrioDao.save(barrioDB);
	}

	@Override
	@Transactional
	public BarrioEntity delete(Short barrioId) {
		BarrioEntity barrioDB = barrioDao.findById(barrioId).orElseThrow(()-> new RuntimeException("No existe ID"));
		boolean isDelete = barrioDao.deleteLogic(barrioDB.getId()) > 0;
		if(isDelete) {
			return barrioDB;
		} else {
			throw new RuntimeException("Existe un error al eliminar");
		}
	}
}
