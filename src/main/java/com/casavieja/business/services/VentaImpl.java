/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.VentaDao;
import com.casavieja.business.entities.VentaEntity;
import com.casavieja.business.model.VentaDetalleM;
import com.casavieja.business.model.VentaM;
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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
public class VentaImpl implements VentaS {

	private final VentaDao ventaDao;
	private final UtilDataTableS utilDataTableS;
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	@Transactional(readOnly = true)
	public DataTableResults listByDistribuidor(HttpServletRequest request, Boolean status, Long distribuidorId) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("v.*, concat(pe.name, ' ', pe.first_lastname, ' ', pe.second_lastname) xcliente");
		sql.setFrom("business.venta v");
		sql.addJoin("public.persons pe on pe.id = v.cliente_id");
		sql.addJoin("public.persons pe2 on pe2.id = v.usuario_id");
		sql.setWhere("v.usuario_id = :xdistribuidor and v.status = :xstatus");
		sql.addParameter("xstatus", status);
		sql.addParameter("xdistribuidor", distribuidorId);
		return utilDataTableS.list(request, VentaM.class, sql);
	}

	@Override
	@Transactional(readOnly = true)
	public DataTableResults listBySalida(HttpServletRequest request, Boolean status, Long salidaId) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("v.*, concat(pe.name, ' ', pe.first_lastname, ' ', pe.second_lastname) xcliente");
		sql.setFrom("business.venta v");
		sql.addJoin("public.persons pe on pe.id = v.cliente_id");
		sql.addJoin("public.persons pe2 on pe2.id = v.usuario_id");
		sql.setWhere("v.salida_id = :xsalida and v.status = :xstatus");
		sql.addParameter("xstatus", status);
		sql.addParameter("xsalida", salidaId);
		return utilDataTableS.list(request, VentaM.class, sql);
	}

	public void validate(VentaM value) {
		if(value.getDetalles() == null || value.getDetalles().isEmpty()) {
			throw new RuntimeException("Debe agregar por lo menos un detalle");
		}
		if(value.getClienteId() == null) {
			throw new RuntimeException("Debe seleccionar un cliente");
		}
		if(value.getSalidaId() == null) {
			throw new RuntimeException("Debe seleccionar una salida acttiva");
		}
		BigDecimal subtotal = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		for(VentaDetalleM detalle : value.getDetalles()) {
			if(detalle.getDescuento() == null) {
				detalle.setDescuento(new BigDecimal(0));
			}
			if(detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
				throw new RuntimeException("La cantidad debe ser mayor a 0");
			}
			if(detalle.getPrecio() == null || detalle.getPrecio().compareTo(new BigDecimal(0)) <= 0) {
				throw new RuntimeException("El precio debe ser mayor a 0");
			}
			subtotal = detalle.getPrecio().multiply(new BigDecimal(detalle.getCantidad()));
			total = total.add(subtotal.subtract(detalle.getDescuento()));
		}
		value.setSubtotal(total);
		value.setTotal(total.subtract(value.getDescuento()).add(value.getImpuesto()));
	}
	@Override
	@Transactional
	public VentaEntity save(VentaM value) {
		validate(value);
		VentaEntity ventaEntity = new VentaEntity();
		ventaEntity.setClienteId(value.getClienteId());
		ventaEntity.setUsuarioId(value.getUsuarioId());
		ventaEntity.setFormaPago((short)value.getFormaPago().value);
		ventaEntity.setTipoVenta(value.getTipoVenta().value);
		ventaEntity.setFecha(new Timestamp(System.currentTimeMillis()));
		ventaEntity.setImpuesto(value.getImpuesto());
		ventaEntity.setDescuento(value.getDescuento());
		ventaEntity.setSubtotal(value.getSubtotal());
		ventaEntity.setTotal(value.getTotal());
		ventaEntity.setSalidaId(value.getSalidaId());
		ventaEntity.setBranchOfficeId(value.getBranchOfficeId());
		ventaEntity.setStatus(true);
		VentaEntity ventaEntityDB = ventaDao.save(ventaEntity);
		String p1 = value.getDetalles().stream().map(it -> it.getProductoId()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		String p2 = value.getDetalles().stream().map(it -> it.getCantidad()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		String p3 = value.getDetalles().stream().map(it -> it.getPrecio()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		String p4 = value.getDetalles().stream().map(it -> it.getDescuento()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		String p5 = value.getDetalles().stream().map(it -> it.getTipoCantidad()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		ventaDao.adicionarDetallesVenta(ventaEntityDB.getId(), p1,p2,p3,p4, p5);
		return ventaEntityDB;
	}

	
	@Override
	public VentaM findById(Long ventaId) {
		String sql = new StringBuilder().append("select v.*,concat(p.name, ' ', p.first_lastname, ' ', p.second_lastname) xcliente from business.venta v ")
				.append("inner join public.persons p on p.id = v.cliente_id ")
				.append("where v.id = ?;").toString();
		Query query = entityManager.createNativeQuery(sql, VentaM.class);
		query.setParameter(1, ventaId);
		List<VentaM> lista = query.getResultList();
		if(!lista.isEmpty()) {
			VentaM ventaM = lista.get(0);
			sql = new StringBuilder().append("select vd.*,p.nombre_comercial as xproducto from business.venta_detalle vd ")
					.append("inner join business.producto p on vd.producto_id = p.id where vd.venta_id = ?;").toString();
			Query queryDetalle = entityManager.createNativeQuery(sql, VentaDetalleM.class);
			queryDetalle.setParameter(1, ventaId);
			List<VentaDetalleM> detalles = queryDetalle.getResultList();
			ventaM.setDetalles(detalles);
			return ventaM;
		} else {
			return null;
		}
	}
	@Transactional
	@Override
	public void eliminarVenta(Long ventaId) {
		VentaEntity ventaEntity = ventaDao.findById(ventaId).orElseThrow(() -> new RuntimeException("La venta no existe"));
		ventaDao.eliminarVenta(ventaId, ventaEntity.getSalidaId());
	}
}
