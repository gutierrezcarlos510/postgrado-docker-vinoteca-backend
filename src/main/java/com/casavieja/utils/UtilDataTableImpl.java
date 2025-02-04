/**
 * 
 */
package com.casavieja.utils;


import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.google.gson.GsonBuilder;
import com.casavieja.pagination.AppUtil;
import com.casavieja.pagination.DataTableRequest;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.PaginationCriteria;
import com.casavieja.pagination.SqlBuilder;

/**
 * @author CARLOS
 * Administra las funciones de datatable
 */
@Service
public class UtilDataTableImpl implements UtilDataTableS {
	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public String toJson(DataTableResults<?> value) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();
		return gsonBuilder.create().toJson(value);
	}
	@Override
	public <T> DataTableResults<T> list(HttpServletRequest request, Class<T> clazz, SqlBuilder sql){
		DataTableRequest<T> dataTableInRQ = new DataTableRequest<T>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		//System.out.println(sql.generateCount());
		Query queryCount = entityManager.createNativeQuery(sql.generateCount());
		long tam = ((BigInteger) queryCount.getSingleResult()).longValue();
		Query queryCountFilter = entityManager.createNativeQuery(AppUtil.buildCountQuery(sql.generate(""),pagination));
		long tamFiltered = ((BigInteger) queryCountFilter.getSingleResult()).longValue();
//		DataTableRequest<T> dataTableInRQ = new DataTableRequest<T>(request);
//		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		String paginatedQuery = AppUtil.buildPaginatedQuery(sql.generate(tam+" AS total_records"), pagination);
		//System.out.println(paginatedQuery);
		Query query = entityManager.createNativeQuery(paginatedQuery, clazz);
		
		@SuppressWarnings("unchecked")
		List<T> dataList = query.getResultList();
		
		DataTableResults<T> dataTableResult = new DataTableResults<T>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(dataList);
		if (UtilValidation.exist(dataList)) {
			dataTableResult.setRecordsTotal(""+tam);
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(""+tam);
			} else {
				dataTableResult.setRecordsFiltered(String.valueOf(tamFiltered));
			}
		}else {
			dataTableResult.setRecordsFiltered("0");
			dataTableResult.setRecordsTotal("0");
		}
		return dataTableResult;
	}
	
}
