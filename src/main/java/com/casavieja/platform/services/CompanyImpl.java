/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import com.casavieja.platform.dao.CompanyDao;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Company;
import com.casavieja.platform.models.CompanyM;
import com.casavieja.utils.UtilDataTableS;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class CompanyImpl implements CompanyS {
	private final CompanyDao companyDao;
	private final UtilDataTableS utilDataTableS;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<Company>> listActive()  throws Exception{
		List<Company> lista = companyDao.findByStatusTrue();
		return ResponseEntity.ok(lista);
	}

	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) throws Exception {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("c.id, c.description, c.email, c.fax, c.name, c.nit, c.phone, c.place, c.status, c.type_company_id, c.web_page");
		sql.setFrom("companies c");
		sql.setWhere("c.status=true");
		DataTableResults<CompanyM> result= utilDataTableS.list(request, CompanyM.class, sql);
		return result;
	}

	@Override
	@Transactional
	public ResponseEntity<DataResponse> save(Company value) throws Exception {
		Company entitySave = companyDao.save(value);
		if(entitySave != null) {
			return new DataResponse<>(entitySave, "Se registro con exito")
					.getResult(HttpStatus.OK);
		} else {
			return new DataResponse().getResult(HttpStatus.BAD_REQUEST);
		}
	}
	@Override
	@Transactional
	public ResponseEntity<DataResponse> delete(Company value) throws Exception {
		boolean isDelete = companyDao.deleteLogic(value.getId()) > 0;
		if(isDelete) {
			return new DataResponse<>("Se elimino la empresa").getResult(HttpStatus.OK);
		} else {
			return new DataResponse<>("No se logro eliminar").getResult(HttpStatus.BAD_REQUEST);
		}
	}
	@Transactional(readOnly = true)
	public ResponseEntity<DataResponse> findBySystemUser(Long systemUserId) throws Exception {
		List<Company> lista = companyDao.findBySystemUser(systemUserId);
		return new DataResponse<>(lista, "").getResult(HttpStatus.OK);
	}
}
