/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.MovimientoInventarioDao;
import com.casavieja.business.dto.form.MovimientoInventarioForm;
import com.casavieja.business.entities.MovimientoInventarioEntity;
import com.casavieja.business.enums.MovimientoInventarioE;
import com.casavieja.business.model.MovimientoInventarioDetalleM;
import com.casavieja.business.model.MovimientoInventarioM;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import com.casavieja.utils.UtilDataTableS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
public class MovimientoInventarioImpl implements MovimientoInventarioS {

	private final MovimientoInventarioDao movimientoInventarioDao;
	private final UtilDataTableS utilDataTableS;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request, Boolean status) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("mi.*, concat(p1.name,' ',p1.first_lastname, ' ',p1.second_lastname) as xusuario,b.name xsucursal_destino,b1.name xsucursal_origen");
		sql.setFrom("business.movimiento_inventario mi");
		sql.addJoin("public.persons p1 on p1.id = mi.usuario_id");
		sql.addJoin("public.branch_offices b1 on mi.sucursal_origen = b1.id");
		sql.addLeftJoin("public.branch_offices b on mi.sucursal_destino = b.id");
		sql.setWhere("mi.status = :pstatus");
		sql.addParameter("pstatus", status);
		return utilDataTableS.list(request, MovimientoInventarioM.class, sql);
	}

	@Override
	@Transactional
	public MovimientoInventarioEntity saveIngreso(MovimientoInventarioForm value) {
		MovimientoInventarioEntity movimientoDB = saveMovimientoEntity(value);
		String p1 = value.getDetalles().stream().map(it -> it.getProductoId()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		String p2 = value.getDetalles().stream().map(it -> it.getLote().isEmpty() ? "-" : it.getLote()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{991,222,555,888}";
		String p3 = value.getDetalles().stream().map(it -> it.getFechaElaboracion().isEmpty() ? "-" : it.getFechaElaboracion()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{30/10/24,30/10/24,30/10/24,30/10/24}";
		String p4 = value.getDetalles().stream().map(it -> it.getFechaVencimiento().isEmpty() ? "-" : it.getFechaVencimiento()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{30/10/26,30/10/26,30/10/26,30/10/26}";
		String p5 = value.getDetalles().stream().map(it -> it.getCantidadUnidad() != null ? it.getCantidadUnidad() : 0).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,3,4}";
		String p6 = value.getDetalles().stream().map(it -> it.getCantidadCaja() != null ? it.getCantidadCaja() : 0).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,3,4}";
		movimientoInventarioDao.adicionarDetallesMovimientoIngreso(movimientoDB.getId(), movimientoDB.getSucursalOrigen(), p1,p2,p3,p4,p5,p6);
		return movimientoDB;
	}

	@Override
	@Transactional
	public MovimientoInventarioEntity saveEgreso(MovimientoInventarioForm value) {
		MovimientoInventarioEntity movimientoDB = saveMovimientoEntity(value);
		String p1 = value.getDetalles().stream().map(it -> it.getProductoId()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		String p2 = value.getDetalles().stream().map(it -> it.getCantidadUnidad() != null ? it.getCantidadUnidad() : 0).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,3,4}";
		String p3 = value.getDetalles().stream().map(it -> it.getCantidadCaja() != null ? it.getCantidadCaja() : 0).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,3,4}";
		System.out.println(movimientoDB.getId());
		System.out.println(movimientoDB.getSucursalOrigen());
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p3);
		movimientoInventarioDao.adicionarDetallesMovimientoEgreso(movimientoDB.getId(), movimientoDB.getSucursalOrigen(), p1,p2,p3);
		return movimientoDB;
	}

	private MovimientoInventarioEntity saveMovimientoEntity(MovimientoInventarioForm value) {
		MovimientoInventarioEntity movimiento = new MovimientoInventarioEntity();
		movimiento.setFecha(new Date(new java.util.Date().getTime()));
		movimiento.setUsuarioId(value.getUsuarioId());
		movimiento.setMotivo(value.getMotivo());
		movimiento.setSucursalOrigen(value.getSucursalOrigen());
		movimiento.setCreatedAt(Timestamp.from(Instant.now()));
		movimiento.setCreatedBy(value.getCreatedBy());
		movimiento.setTipo(value.getTipo());
		movimiento.setStatus(true);
		return movimientoInventarioDao.save(movimiento);
	}
	
	@Override
	public MovimientoInventarioM findById(Long movimientoIngresoId) {
		Query query = entityManager.createNativeQuery("select mi.*,concat(p1.name,' ',p1.first_lastname, ' ',p1.second_lastname) as xusuario,b1.name xsucursal_origen,b.name xsucursal_destino from business.movimiento_inventario mi inner join persons p1 on p1.id = mi.usuario_id inner join branch_offices b1 on mi.sucursal_origen = b1.id left join branch_offices b on mi.sucursal_destino = b.id where mi.id = ?", MovimientoInventarioM.class);
		query.setParameter(1, movimientoIngresoId);
		List<MovimientoInventarioM> lista = query.getResultList();
		if(!lista.isEmpty()) {
			MovimientoInventarioM movimientoM = lista.get(0);
			Query queryDetalle = entityManager.createNativeQuery("select mid.*,p.nombre_comercial as xproducto from business.movimiento_inventario_detalle mid inner join business.producto p on mid.producto_id = p.id where mid.movimiento_inventario_id = ?", MovimientoInventarioDetalleM.class);
			queryDetalle.setParameter(1, movimientoIngresoId);
			List<MovimientoInventarioDetalleM> detalles = queryDetalle.getResultList();
			movimientoM.setDetalles(detalles);
			return movimientoM;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public MovimientoInventarioEntity delete(Long movimientoId, Long userId) {
		try {
			MovimientoInventarioEntity movimientoEntity = movimientoInventarioDao.findById(movimientoId).orElseThrow(()-> new RuntimeException("No existe Id"));
			if(MovimientoInventarioE.ENTRADA.esIgual(movimientoEntity.getTipo())) {
				movimientoInventarioDao.eliminarDetallesMovimientoIngreso(movimientoId, userId);
			} else if(MovimientoInventarioE.SALIDA.esIgual(movimientoEntity.getTipo())) {
				movimientoInventarioDao.eliminarDetallesMovimientoEgreso(movimientoId, userId);
			}
			return  movimientoEntity;
		} catch (Exception e) {
			throw new RuntimeException("No se puede eliminar, ya que el inventario a revertir es mayor a lo que se tiene en inventario");
		}
	}
}
