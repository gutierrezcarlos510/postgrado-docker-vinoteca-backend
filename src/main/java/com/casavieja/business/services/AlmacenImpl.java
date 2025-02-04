/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.AlmacenDao;
import com.casavieja.business.entities.AlmacenEntity;
import com.casavieja.business.model.AlmacenDistribuidorM;
import com.casavieja.business.model.AlmacenM;
import com.casavieja.business.model.ResumenAlmacenDistribuidorM;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import com.casavieja.utils.UtilDataTableS;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
public class AlmacenImpl implements AlmacenS {

	private final AlmacenDao almacenDao;
	private final UtilDataTableS utilDataTableS;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public DataTableResults<AlmacenM> list(HttpServletRequest request, Integer sucursalId, String statusFilterStock) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("p.id as producto_id, p.nombre_comercial as xproducto, p.unidad_por_caja, p.stock_medio, p.stock_alto, coalesce(sum(a.cantidad), 0) as cantidad");
		sql.setFrom("business.producto p");
		sql.addLeftJoin("business.almacen a on p.id = a.producto_id");
		sql.setWhere("p.status = true");
		sql.setGroupBy("p.id, p.nombre_comercial, p.unidad_por_caja, p.stock_medio, p.stock_alto");
		if (statusFilterStock.equals("a")) {
			sql.setHaving("coalesce(sum(a.cantidad), 0) >= p.stock_alto");
		} else if (statusFilterStock.equals("m")) {
			sql.setHaving("coalesce(sum(a.cantidad), 0) >= p.stock_medio and coalesce(sum(a.cantidad), 0) < p.stock_alto");
		} else if (statusFilterStock.equals("b")) {
			sql.setHaving("coalesce(sum(a.cantidad), 0) < p.stock_medio");
		}
		return utilDataTableS.list(request, AlmacenM.class, sql);
	}

	@Override
	public ResponseEntity<AlmacenM> findByProductoIdTotal(Integer productoId) {
		String sql = new StringBuilder("select p.id as producto_id,p.nombre_comercial as xproducto,p.unidad_por_caja,")
				.append("p.stock_medio,p.stock_alto,coalesce(sum(a.cantidad),0) as cantidad ")
				.append("from business.producto p ")
				.append("left join business.almacen a on p.id = a.producto_id ")
				.append("where p.id = ? group by p.id,p.nombre_comercial,p.unidad_por_caja,p.stock_medio,p.stock_alto").toString();
		Query query = entityManager.createNativeQuery(sql, AlmacenM.class);
		query.setParameter(1, productoId);
		List<AlmacenM> lista = query.getResultList();
		if(!lista.isEmpty()) {
			return ResponseEntity.ok(lista.get(0));
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<AlmacenEntity>> findByProductoId(Integer productoId) {
		List<AlmacenEntity> lista = almacenDao.findByProductoId(productoId);
		return ResponseEntity.ok(lista);
	}
	@Override
	public ResponseEntity<List<AlmacenDistribuidorM>> almacenByDistribuidor(Long distribuidorId) {
		String sql = new StringBuilder("select a.producto_id,p.nombre_comercial xproducto,p.foto,p.pv_unit,p.pv_caja,p.unidad_por_caja, sum(ad.cantidad) as cantidad ")
				.append("from business.almacen_distribuidor ad inner join business.almacen a on a.id = ad.almacen_id ")
				.append("inner join business.producto p on p.id = a.producto_id ")
				.append("where ad.distribuidor_id = ? and ad.cantidad > 0 ")
				.append("group by a.producto_id,p.nombre_comercial,p.foto,p.pv_unit,p.pv_caja,p.unidad_por_caja;").toString();
		Query query = entityManager.createNativeQuery(sql, AlmacenDistribuidorM.class);
		query.setParameter(1, distribuidorId);
		List<AlmacenDistribuidorM> lista = query.getResultList();
		return ResponseEntity.ok(lista);
	}
	@Override
	public ResponseEntity<List<ResumenAlmacenDistribuidorM>> listAlmacenDistribuidorBySalida(Long salidaId) {
		String sql = new StringBuilder("select sed.salida_id,sed.producto_id,p.nombre_comercial as xproducto,p.unidad_por_caja,sum(sed.cantidad_total) cantidad_entregada, ")
				.append("(select coalesce(sum(vd.cantidad_unitaria_total),0) from business.venta_detalle vd inner join business.venta v on v.status = true and v.id = vd.venta_id and v.salida_id = sed.salida_id where vd.producto_id = sed.producto_id) cantidad_vendida, ")
				.append("(select coalesce(sum(ad.cantidad),0) from business.almacen_distribuidor ad inner join business.almacen a on a.id = ad.almacen_id and a.producto_id = sed.producto_id where ad.salida_id = sed.salida_id ) cantidad_actual, ")
				.append("(select coalesce(sum(vd2.subtotal),0) from business.venta_detalle vd2 inner join business.venta v2 on v2.id = vd2.venta_id and v2.salida_id = sed.salida_id and v2.status = true where vd2.producto_id = sed.producto_id) as subtotal,")
				.append("(select coalesce(sum(vd2.descuento),0) from business.venta_detalle vd2 inner join business.venta v2 on v2.id = vd2.venta_id and v2.salida_id = sed.salida_id and v2.status = true where vd2.producto_id = sed.producto_id) as descuento,")
				.append("(select coalesce(sum(vd2.total),0) from business.venta_detalle vd2 inner join business.venta v2 on v2.id = vd2.venta_id and v2.salida_id = sed.salida_id and v2.status = true where vd2.producto_id = sed.producto_id) as total ")
				.append("from business.salida_entrega_detalle sed ")
				.append("inner join business.producto p on p.id = sed.producto_id ")
				.append("where sed.salida_id = ? and sed.status = true ")
				.append("group by sed.salida_id,sed.producto_id,p.nombre_comercial,p.unidad_por_caja").toString();
		Query query = entityManager.createNativeQuery(sql, ResumenAlmacenDistribuidorM.class);
		query.setParameter(1, salidaId);
		List<ResumenAlmacenDistribuidorM> lista = query.getResultList();
		return ResponseEntity.ok(lista);
	}

}
