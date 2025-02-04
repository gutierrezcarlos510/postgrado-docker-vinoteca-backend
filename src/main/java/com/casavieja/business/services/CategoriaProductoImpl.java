/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.CategoriaProductoDao;
import com.casavieja.business.entities.CategoriaProductoEntity;
import com.casavieja.business.model.CategoriaProductoM;
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
public class CategoriaProductoImpl implements CategoriaProductoS {

	private final CategoriaProductoDao categoriaProductoDao;
	private final UtilDataTableS utilDataTableS;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<CategoriaProductoEntity>> listActive() {
		List<CategoriaProductoEntity> lista = categoriaProductoDao.findByStatusTrue();
		return ResponseEntity.ok(lista);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<CategoriaProductoEntity>> findByTipoProducto(Short tipoProductoId) {
		List<CategoriaProductoEntity> lista = categoriaProductoDao.findByTipoProductoIdAndStatusTrue(tipoProductoId);
		return ResponseEntity.ok(lista);
	}

	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("id, nombre, descripcion, status, tipo_producto_id");
		sql.setFrom("business.categoria_producto");
		sql.setWhere("status = true");
		return utilDataTableS.list(request, CategoriaProductoM.class, sql);
	}

	@Override
	@Transactional
	public CategoriaProductoEntity save(CategoriaProductoEntity value) {
		return categoriaProductoDao.save(value);
	}

	@Override
	@Transactional
	public CategoriaProductoEntity update(Short categoriaId, CategoriaProductoEntity value) {
		CategoriaProductoEntity categoriaDB = categoriaProductoDao.findById(categoriaId).orElseThrow(()-> new RuntimeException("No existe ID"));
		categoriaDB.setNombre(value.getNombre());
		categoriaDB.setDescripcion(value.getDescripcion());
		categoriaDB.setTipoProductoId(value.getTipoProductoId());
		return categoriaProductoDao.save(categoriaDB);
	}

	@Override
	@Transactional
	public CategoriaProductoEntity delete(Short categoriaId) {
		CategoriaProductoEntity categoriaDB = categoriaProductoDao.findById(categoriaId).orElseThrow(()-> new RuntimeException("No existe ID"));
		boolean isDelete = categoriaProductoDao.deleteLogic(categoriaDB.getId()) > 0;
		if(isDelete) {
			return categoriaDB;
		} else {
			throw new RuntimeException("Existe un error al eliminar");
		}
	}

}
