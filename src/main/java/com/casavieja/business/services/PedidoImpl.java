/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.PedidoDao;
import com.casavieja.business.dto.form.PedidoForm;
import com.casavieja.business.entities.PedidoEntity;
import com.casavieja.business.enums.PedidoE;
import com.casavieja.business.model.PedidoDetalleM;
import com.casavieja.business.model.PedidoM;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
public class PedidoImpl implements PedidoS {

	private final PedidoDao pedidoDao;
	private final UtilDataTableS utilDataTableS;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public DataTableResults<PedidoM> listByDistribuidor(HttpServletRequest request, Boolean status, String estado, Long distribuidorId) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("p.*, concat(pe.name, ' ', pe.first_lastname, ' ', pe.second_lastname) xdistribuidor, concat(pe2.name, ' ', pe2.first_lastname, ' ', pe2.second_lastname) xcliente");
		sql.setFrom("business.pedido p");
		sql.addJoin("public.persons pe2 on pe2.id = p.cliente_id");
		sql.addLeftJoin("public.persons pe on pe.id = p.distribuidor_id");
		sql.setWhere("(p.distribuidor_id = :xdistribuidor or '-1' = :xdistribuidor) and p.status = :xstatus and (p.estado = :xestado or '-1' = :xestado)");
		sql.addParameter("xestado", estado);
		sql.addParameter("xstatus", status);
		sql.addParameter("xdistribuidor", distribuidorId);
		return utilDataTableS.list(request, PedidoM.class, sql);
	}

	@Override
	@Transactional
	public PedidoEntity save(PedidoForm value) {
		PedidoEntity pedidoEntity = new PedidoEntity();
		pedidoEntity.setClienteId(value.getClienteId());
		pedidoEntity.setDistribuidorId(value.getDistribuidorId());
		pedidoEntity.setUsuarioId(value.getUsuarioId());
		pedidoEntity.setTipo(value.getTipo().value);
		pedidoEntity.setFecha(new Timestamp(System.currentTimeMillis()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if(value.getFechaEntrega() != null && !value.getFechaEntrega().isEmpty()) {
				Date parseDate = sdf.parse(value.getFechaEntrega());
				pedidoEntity.setFechaEntrega(new Timestamp(parseDate.getTime()));
			} else {
				pedidoEntity.setFechaEntrega(null);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		pedidoEntity.setObservacion(value.getObservacion());
		pedidoEntity.setEstado(value.getEstado());
		pedidoEntity.setStatus(true);
		pedidoEntity.setSubtotal(BigDecimal.ZERO);
		pedidoEntity.setImpuesto(BigDecimal.ZERO);
		pedidoEntity.setDescuento(value.getDescuento());
		pedidoEntity.setTotal(BigDecimal.ZERO);
		PedidoEntity pedidoEntityDB = pedidoDao.save(pedidoEntity);
		String p1 = value.getDetalles().stream().map(it -> it.getProductoId()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		String p2 = value.getDetalles().stream().map(it -> it.getCantidad()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		String p3 = value.getDetalles().stream().map(it -> it.getPrecio()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		String p4 = value.getDetalles().stream().map(it -> it.getTipoCantidad()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		pedidoDao.adicionarDetallesPedido(pedidoEntityDB.getId(), p1,p2,p3,p4);
		return pedidoEntityDB;
	}

	@Override
	public PedidoM findById(Long pedidoId) {
		String sql = new StringBuilder()
				.append("select p.*, concat(pe.name, ' ', pe.first_lastname, ' ', pe.second_lastname) xdistribuidor, ")
				.append("concat(pe2.name, ' ', pe2.first_lastname, ' ', pe2.second_lastname) xcliente from business.pedido p ")
				.append("left join public.persons pe on pe.id = p.distribuidor_id ")
				.append("inner join public.persons pe2 on pe2.id = p.cliente_id ")
				.append("where p.id = ?;").toString();
		Query query = entityManager.createNativeQuery(sql, PedidoM.class);
		query.setParameter(1, pedidoId);
		List<PedidoM> lista = query.getResultList();
		if(!lista.isEmpty()) {
			PedidoM pedidoM = lista.get(0);
			sql = new StringBuilder().append("select pd.*,p.nombre_comercial as xproducto from business.pedido_detalle pd ")
					.append("inner join business.producto p on pd.producto_id = p.id where pd.pedido_id =?;").toString();
			Query queryDetalle = entityManager.createNativeQuery(sql, PedidoDetalleM.class);
			queryDetalle.setParameter(1, pedidoId);
			List<PedidoDetalleM> detalles = queryDetalle.getResultList();
			pedidoM.setDetalles(detalles);
			return pedidoM;
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public void delete(Long pedidoId) {
		boolean isDelete = pedidoDao.deleteLogic(pedidoId) > 0;
		if(!isDelete) {
			throw new RuntimeException("Existe un error al eliminar");
		}
	}

	@Override
	@Transactional
	public void review(Long pedidoId, String estado) {
		try {
			boolean isReview = pedidoDao.revisarEstadoPedido(estado, pedidoId) > 0;
			if(!isReview) {
				throw new RuntimeException("Existe un error al revisar pedido");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Transactional
	@Override
	public void assign(Long pedidoId, Long distribuidorId) {
		boolean isReview = pedidoDao.asignarDistribuidorPedido(distribuidorId, PedidoE.EN_PROCESO.value, pedidoId) > 0;
		if(!isReview) {
			throw new RuntimeException("Existe un error al revisar pedido");
		}
	}
}
