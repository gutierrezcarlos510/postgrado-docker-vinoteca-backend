/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.IngresoDao;
import com.casavieja.business.dto.form.IngresoForm;
import com.casavieja.business.entities.IngresoEntity;
import com.casavieja.business.model.IngresoDetalleM;
import com.casavieja.business.model.IngresoM;
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
import java.util.Arrays;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
public class IngresoImpl implements IngresoS {

	private final IngresoDao ingresoDao;
	private final UtilDataTableS utilDataTableS;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request, Boolean status) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("i.*, concat(p1.name,' ',p1.first_lastname, ' ',p1.second_lastname) xusuario_recepcion,concat(p2.name,' ',p2.first_lastname, ' ',p2.second_lastname) xusuario_entrega");
		sql.setFrom("business.ingreso i");
		sql.addJoin("public.persons p1 on p1.id = i.usuario_recepcion_id");
		sql.addJoin("public.persons p2 on p2.id = i.usuario_entrega_id");
		sql.setWhere("i.status = :xstatus");
		sql.addParameter("xstatus", status);
		return utilDataTableS.list(request, IngresoM.class, sql);
	}
	
	@Override
	@Transactional
	public IngresoEntity save(IngresoForm value) {
		IngresoEntity ingresoEntity = new IngresoEntity();
		long numeroIngreso = ingresoDao.obtenerNumeroIngreso(value.getBranchOfficeId());
		ingresoEntity.setNumero(numeroIngreso);
		ingresoEntity.setFecha(new Date(new java.util.Date().getTime()));
		ingresoEntity.setUsuarioRecepcionId(value.getUsuarioRecepcionId());
		ingresoEntity.setUsuarioEntregaId(value.getUsuarioEntregaId());
		ingresoEntity.setObservacion(value.getObservacion());
		ingresoEntity.setBranchOfficeId(value.getBranchOfficeId());
		ingresoEntity.setCreatedAt(Timestamp.from(Instant.now()));
		ingresoEntity.setCreatedBy(value.getCreatedBy());
		ingresoEntity.setStatus(true);
		IngresoEntity ingresoEntityDB = ingresoDao.save(ingresoEntity);
		String p1 = Arrays.toString(value.getProductos()).replace("[","{").replace("]","}");//"{1,2,5,8}";
		String p2 = Arrays.toString(value.getLotes()).replace("[","{").replace("]","}");//"{991,222,555,888}";
		String p3 = Arrays.toString(value.getElaboraciones()).replace("[","{").replace("]","}");//"{30/10/24,30/10/24,30/10/24,30/10/24}";
		String p4 = Arrays.toString(value.getVencimientos()).replace("[","{").replace("]","}");//"{30/10/26,30/10/26,30/10/26,30/10/26}";
		String p5 = Arrays.toString(value.getUnidades()).replace("[","{").replace("]","}");//"{1,2,3,4}";
		String p6 = Arrays.toString(value.getCajas()).replace("[","{").replace("]","}");//"{1,2,3,4}";
		ingresoDao.adicionarDetallesIngreso(ingresoEntityDB.getId(), ingresoEntity.getBranchOfficeId(), p1,p2,p3,p4,p5,p6);
		return ingresoEntityDB;
	}
	
	@Override
	public IngresoM findById(Long ingresoId) {
		Query query = entityManager.createNativeQuery("select i.*,concat(p1.name,' ',p1.first_lastname, ' ',p1.second_lastname) as xusuario_recepcion,concat(p2.name,' ',p2.first_lastname, ' ',p2.second_lastname) as xusuario_entrega from business.ingreso i inner join persons p1 on p1.id = i.usuario_recepcion_id inner join persons p2 on p2.id = i.usuario_entrega_id where i.id = ?", IngresoM.class);
		query.setParameter(1, ingresoId);
		List<IngresoM> lista = query.getResultList();
		if(!lista.isEmpty()) {
			IngresoM ingresoM = lista.get(0);
			Query queryDetalle = entityManager.createNativeQuery("select i.*,p.nombre_comercial as xproducto from business.ingreso_detalle i inner join business.producto p on i.producto_id = p.id where i.ingreso_id = ?", IngresoDetalleM.class);
			queryDetalle.setParameter(1, ingresoId);
			List<IngresoDetalleM> detalles = queryDetalle.getResultList();
			ingresoM.setDetalles(detalles);
			return ingresoM;
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional
	public IngresoEntity delete(Long ingresoId, Long userId) {
		try {
			IngresoEntity ingresoEntity = ingresoDao.findById(ingresoId).orElseThrow(()-> new RuntimeException("No existe Id"));
			ingresoDao.eliminarIngreso(ingresoId, userId);
			return  ingresoEntity;
		} catch (Exception e) {
			throw new RuntimeException("No se puede eliminar, ya que el inventario a revertir es mayor a lo que se tiene en inventario");
		}
	}
}
