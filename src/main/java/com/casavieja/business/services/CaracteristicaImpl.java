/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.CaracteristicaDao;
import com.casavieja.business.entities.CaracteristicaEntity;
import com.casavieja.business.model.CaracteristicaM;
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
public class CaracteristicaImpl implements CaracteristicaS {

	private final CaracteristicaDao caracteristicaDao;
	private final UtilDataTableS utilDataTableS;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<CaracteristicaEntity>> findByTipo(String tipoId) {
		List<CaracteristicaEntity> lista = caracteristicaDao.findByTipoAndStatusTrue(tipoId);
		return ResponseEntity.ok(lista);
	}

	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("id, nombre, tipo, status");
		sql.setFrom("business.caracteristica");
		sql.setWhere("status = true");
		return utilDataTableS.list(request, CaracteristicaM.class, sql);
	}

	@Override
	@Transactional
	public CaracteristicaEntity save(CaracteristicaEntity value) {
		return caracteristicaDao.save(value);
	}


	@Override
	@Transactional
	public CaracteristicaEntity update(Short caracteristicaId, CaracteristicaEntity value) {
		CaracteristicaEntity caracteristicaDB = caracteristicaDao.findById(caracteristicaId).orElseThrow(()-> new RuntimeException("No existe ID"));
		caracteristicaDB.setNombre(value.getNombre());
		caracteristicaDB.setTipo(value.getTipo());
		return caracteristicaDao.save(caracteristicaDB);
	}

	@Override
	@Transactional
	public CaracteristicaEntity delete(Short caracteristicaId) {
		CaracteristicaEntity caracteristicaDB = caracteristicaDao.findById(caracteristicaId).orElseThrow(()-> new RuntimeException("No existe ID"));
		boolean isDelete = caracteristicaDao.deleteLogic(caracteristicaDB.getId()) > 0;
		if(isDelete) {
			return caracteristicaDB;
		} else {
			throw new RuntimeException("Existe un error al eliminar");
		}
	}

}
