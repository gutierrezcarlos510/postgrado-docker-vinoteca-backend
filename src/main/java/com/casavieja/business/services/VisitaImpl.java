/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.VisitaDao;
import com.casavieja.business.entities.VisitaEntity;
import com.casavieja.business.model.VisitaM;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
public class VisitaImpl implements VisitaS {

	private final VisitaDao visitaDao;
	private final UtilDataTableS utilDataTableS;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public DataTableResults listBySalida(HttpServletRequest request, boolean status, long salidaId) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("v.*, concat(pe.name, ' ', pe.first_lastname, ' ', pe.second_lastname) xcliente");
		sql.setFrom("business.visita v");
		sql.addJoin("public.persons pe on pe.id = v.cliente_id");
		sql.setWhere("v.status = :xstatus and v.salida_id = :xsalida_id");
		sql.addParameter("xstatus", status);
		sql.addParameter("xsalida_id", salidaId);
		return utilDataTableS.list(request, VisitaM.class, sql);
	}

	@Override
	public VisitaM findById(Long visitaId) {
		String sql = new StringBuilder().append("select v.*, concat(pe.name, ' ', pe.first_lastname, ' ', pe.second_lastname) xcliente from business.visita v ")
				.append("inner join public.persons pe on pe.id = v.cliente_id ")
				.append("where v.id = ?;").toString();
		Query query = entityManager.createNativeQuery(sql, VisitaM.class);
		query.setParameter(1, visitaId);
		List<VisitaM> lista = query.getResultList();
		if(!lista.isEmpty()) {
			return lista.get(0);
		} else {
			return null;
		}
	}

	@Override
	public VisitaEntity save(VisitaEntity value) {
		value.setFecha(new Timestamp(new Date().getTime()));
		value.setStatus(true);
		return visitaDao.save(value);
	}

	@Transactional
	@Override
	public VisitaEntity delete(long visitaId) {
		VisitaEntity visitaDB = visitaDao.findById(visitaId).orElseThrow(()-> new RuntimeException("No existe ID"));
		boolean isDelete = visitaDao.deleteLogic(visitaDB.getId()) > 0;
		if(isDelete) {
			return visitaDB;
		} else {
			throw new RuntimeException("Existe un error al eliminar");
		}
	}

}
