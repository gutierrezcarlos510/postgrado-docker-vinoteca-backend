/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.SalidaDao;
import com.casavieja.business.dao.VentaDao;
import com.casavieja.business.dto.form.SalidaDetalleResumenForm;
import com.casavieja.business.dto.form.SalidaEntregaDetalleForm;
import com.casavieja.business.dto.form.SalidaEntregaForm;
import com.casavieja.business.dto.form.SalidaForm;
import com.casavieja.business.entities.SalidaEntity;
import com.casavieja.business.enums.SalidaE;
import com.casavieja.business.model.ResumenDetalleSalidaPorFinalizarM;
import com.casavieja.business.model.SalidaM;
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
public class SalidaImpl implements SalidaS {

	private final SalidaDao salidaDao;
	private final VentaDao ventaDao;
	private final UtilDataTableS utilDataTableS;
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request, String estadoSalida) {
		try {
			SqlBuilder sql = new SqlBuilder();
			sql.setSelect("s.*,concat(p1.name,' ',p1.first_lastname, ' ',p1.second_lastname) as xusuario,concat(p2.name,' ',p2.first_lastname, ' ',p2.second_lastname) as xdistribuidor");
			sql.setFrom("business.salida s");
			sql.addJoin("persons p1 on p1.id = s.created_by");
			sql.addJoin("persons p2 on p2.id = s.distribuidor_id");
			if(!estadoSalida.equals("t")) {
				sql.setWhere("s.status = true and s.estado_salida = :xestado");
				sql.addParameter("xestado", estadoSalida);
			} else {
				sql.setWhere("s.status = true");
			}
			return utilDataTableS.list(request, SalidaM.class, sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public SalidaEntity save(SalidaForm value) {
		boolean esValido =validarDetalleSalida(value);
		if(!esValido) {
			return null;
		}
		SalidaEntity salidaEntity = new SalidaEntity();
		salidaEntity.setFecha(new Date(new java.util.Date().getTime()));
		salidaEntity.setDistribuidorId(value.getDistribuidorId());
		salidaEntity.setObs(value.getObs());
		salidaEntity.setEstadoSalida(value.getEstadoSalida().getValue());
		salidaEntity.setBranchOfficeId(value.getBranchOfficeId());
		salidaEntity.setCreatedAt(Timestamp.from(Instant.now()));
		salidaEntity.setCreatedBy(value.getCreatedBy());
		salidaEntity.setStatus(true);

		SalidaEntity salidaEntityDB = salidaDao.save(salidaEntity);
		String p1 = value.getEntregaList().get(0).getDetalles().stream().map(it -> it.getProductoId()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		String p2 = value.getEntregaList().get(0).getDetalles().stream().map(it -> it.getCantidadUnitaria() != null ? it.getCantidadUnitaria() : 0).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,3,4}";
		String p3 = value.getEntregaList().get(0).getDetalles().stream().map(it -> it.getCantidadCaja() != null ? it.getCantidadCaja() : 0).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,3,4}";
		salidaDao.adicionarDetallesSalida(salidaEntityDB.getId(), p1,p2,p3);
		return salidaEntityDB;
	}

	@Override
	@Transactional
	public boolean aumentarEntrega(SalidaForm value) {
		boolean esValido =validarDetalleSalida(value);
		if(!esValido) {
			return false;
		}
		String p1 = value.getEntregaList().get(0).getDetalles().stream().map(it -> it.getProductoId()).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,5,8}";
		String p2 = value.getEntregaList().get(0).getDetalles().stream().map(it -> it.getCantidadUnitaria() != null ? it.getCantidadUnitaria() : 0).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,3,4}";
		String p3 = value.getEntregaList().get(0).getDetalles().stream().map(it -> it.getCantidadCaja() != null ? it.getCantidadCaja() : 0).collect(Collectors.toList()).toString().replace("[","{").replace("]","}");//"{1,2,3,4}";
		salidaDao.aumentarDetallesSalida(value.getId(), value.getCreatedBy(), value.getObs(), p1,p2,p3);
		return true;
	}

	
	@Override
	public SalidaForm findById(Long salidaId) {
		SalidaForm salidaForm = findSalidaFormBySalidaId(salidaId);
		if(salidaForm != null) {
			salidaForm.setEntregaList(listAllSalidaEntregaBySalidaId(salidaId));
			salidaForm.setResumenProductosList(listAllSalidaDetallesBySalidaId(salidaId));
			return salidaForm;
		}
		return salidaForm;
	}

	public SalidaForm findSalidaFormBySalidaId(Long salidaId) {
		String sqlString = new StringBuilder().append("select s.*,concat(p1.name,' ',p1.first_lastname, ' ',p1.second_lastname) as xusuario,")
				.append("concat(p2.name,' ',p2.first_lastname, ' ',p2.second_lastname) as xdistribuidor ")
				.append("from business.salida s inner join persons p1 on p1.id = s.created_by ")
				.append("inner join persons p2 on p2.id = s.distribuidor_id where s.id = ?").toString();
		Query query = entityManager.createNativeQuery(sqlString, SalidaForm.class);
		query.setParameter(1, salidaId);
		List<SalidaForm> lista = query.getResultList();
		if(!lista.isEmpty()) {
			return lista.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Returns a list of SalidaEntregaForm objects that belong to the given salidaId.
	 * Each SalidaEntregaForm object contains a list of SalidaEntregaDetalleForm objects.
	 *
	 * @param salidaId the id of the salida
	 * @return a list of SalidaEntregaForm objects
	 */
	private List<SalidaEntregaForm> listAllSalidaEntregaBySalidaId(Long salidaId) {
		Query queryEntrega = entityManager.createNativeQuery("select se.* from business.salida_entrega se where se.salida_id = ? and se.status = true", SalidaEntregaForm.class);
		queryEntrega.setParameter(1, salidaId);
		List<SalidaEntregaForm> entregas = queryEntrega.getResultList();
		Query queryDetalles = entityManager.createNativeQuery("select sed.*,p.nombre_comercial as xproducto,p.unidad_por_caja from business.salida_entrega_detalle sed inner join business.producto p on p.id = sed.producto_id where sed.salida_id = ?", SalidaEntregaDetalleForm.class);
		queryDetalles.setParameter(1, salidaId);
		List<SalidaEntregaDetalleForm> detalles = queryDetalles.getResultList();
		for (SalidaEntregaForm entrega : entregas) {
			entrega.setDetalles(detalles.stream().filter(it -> it.getSalidaEntregaId().shortValue() == entrega.getId().shortValue()).collect(Collectors.toList()));
		}
		return entregas;
	}
	private List<SalidaDetalleResumenForm> listAllSalidaDetallesBySalidaId(Long salidaId) {
		Query queryResumenProductos = entityManager.createNativeQuery("select sed.producto_id,p.nombre_comercial as xproducto,p.unidad_por_caja,sum(sed.cantidad_total) total from business.salida_entrega_detalle sed inner join business.producto p on p.id = sed.producto_id where sed.salida_id = ? group by sed.producto_id,p.nombre_comercial,p.unidad_por_caja", SalidaDetalleResumenForm.class);
		queryResumenProductos.setParameter(1, salidaId);
		List<SalidaDetalleResumenForm> resumenList = queryResumenProductos.getResultList();
		return resumenList;
	}
	
	@Override
	@Transactional
	public SalidaEntity delete(Long salidaId, Long userId) {
		// validar si no existe ventas asociadas a la salida
		boolean existeVentas = ventaDao.existsVentaEntityBySalidaIdAndStatusTrue(salidaId);
		if(existeVentas) {
			throw new RuntimeException("No se puede eliminar la salida porque existen ventas asociadas");
		} else {
			SalidaEntity salida = salidaDao.findById(salidaId).orElseThrow(()-> new RuntimeException("No existe Id"));
			salidaDao.eliminarSalida(salidaId, userId, "Eliminado por usuario id: " + userId);
			return  salida;
		}
	}
	@Override
	public Boolean validarExistenciaSalidaPorDistribuidorEstadoActivo(Long distribuidorId) {
		return salidaDao.existsSalidaEntityByDistribuidorIdAndEstadoSalidaAndStatusTrue(distribuidorId, SalidaE.ACTIVO.getValue());
	}
	public boolean validarDetalleSalida(SalidaForm value) {
		if(value.getEntregaList() != null && !value.getEntregaList().isEmpty()) {
			for (SalidaEntregaForm entrega : value.getEntregaList()) {
				if (!entrega.getDetalles().isEmpty()) {
					for (int i = 0; i < entrega.getDetalles().size(); i++) {
						if(entrega.getDetalles().get(i).getCantidadCaja() == null) {
							entrega.getDetalles().get(i).setCantidadCaja(0);
						}
						if(entrega.getDetalles().get(i).getCantidadUnitaria() == null) {
							entrega.getDetalles().get(i).setCantidadUnitaria(0);
						}
						if(entrega.getDetalles().get(i).getCantidadCaja() + entrega.getDetalles().get(i).getCantidadUnitaria() <= 0) {
							return false;
						}
					}
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
        return true;
	}
	@Override
	public SalidaForm findSalidaActivaByDistribuidor(Long distribuidorId) {
		StringBuilder sql = new StringBuilder("")
				.append("select s.*,concat(p.name,' ',p.first_lastname, ' ',p.second_lastname) as xdistribuidor ,concat(p2.name,' ',p2.first_lastname, ' ',p2.second_lastname) as xusuario from business.salida s ")
				.append("inner join public.persons p on p.id = s.distribuidor_id ")
				.append("inner join public.persons p2 on p2.id = s.created_by ")
				.append("where s.distribuidor_id = ? and s.estado_salida = 'a' and s.status = true;");
		Query query = entityManager.createNativeQuery(sql.toString(), SalidaForm.class);
		query.setParameter(1, distribuidorId);
		List<SalidaForm> lista = query.getResultList();
		if(!lista.isEmpty()) {
			return lista.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Returns a {@link SalidaForm} with resumen detalle para finalizar.
	 *
	 * @param salidaId
	 *            the id of the {@link SalidaEntity} to get the resumen detalle
	 *            para finalizar.
	 * @return a {@link SalidaForm} with resumen detalle para finalizar, or null
	 *         if the {@link SalidaEntity} does not exist.
	 */
	@Override
	public SalidaForm findSalidaFormWithResumenDetalleParaFinalizar(Long salidaId) {
		SalidaForm salidaForm = findSalidaFormBySalidaId(salidaId);
		if(salidaForm != null) {
			String sqlString = new StringBuilder()
					.append("SELECT sed.producto_id, p.nombre_comercial as xproducto,p.unidad_por_caja, SUM(ad.cantidad) AS devuelto, SUM(seda.cantidad) AS entregado ")
					.append("FROM business.salida_entrega_detalle_almacen seda ")
					.append("INNER JOIN business.almacen_distribuidor ad ON seda.salida_id = ad.salida_id AND seda.salida_entrega_detalle_id = ad.salida_entrega_detalle_id AND seda.salida_entrega_id = ad.salida_entrega_id AND seda.almacen_id = ad.almacen_id ")
					.append("INNER JOIN business.salida_entrega_detalle sed ON seda.salida_id = sed.salida_id AND seda.salida_entrega_detalle_id = sed.id AND seda.salida_entrega_id = sed.salida_entrega_id ")
					.append("INNER JOIN business.producto p ON p.id = sed.producto_id ")
					.append("WHERE seda.salida_id = :salidaId GROUP BY sed.producto_id, p.nombre_comercial, sed.producto_id,p.unidad_por_caja").toString();
			Query query = entityManager.createNativeQuery(sqlString, ResumenDetalleSalidaPorFinalizarM.class);
			query.setParameter("salidaId", salidaId);
			List<ResumenDetalleSalidaPorFinalizarM> resultados = query.getResultList();
			salidaForm.setResumenFinalizarList(resultados);
		}
		return salidaForm;
	}
	// consumir funcion SalidaDao.finalizarSalida
	@Override
	public boolean finalizarSalida(SalidaForm value) {
		salidaDao.finalizarSalida(value.getId(), value.getCreatedBy(), value.getObsAlFinalizar(), value.getTotalEfectivoEntregado());
		return true;
	}
}
