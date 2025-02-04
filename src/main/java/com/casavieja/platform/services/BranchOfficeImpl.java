/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import com.casavieja.platform.dao.BranchOfficeDao;
import com.casavieja.platform.dao.CompanyDao;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.BranchOffice;
import com.casavieja.platform.models.BranchOfficeM;
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
public class BranchOfficeImpl implements BranchOfficeS {

	private final BranchOfficeDao branchOfficeDao;
	private final CompanyDao companyDao;
	private final UtilDataTableS utilDataTableS;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<DataResponse> listActive() throws Exception {
		List<BranchOffice> lista = branchOfficeDao.findByStatusTrue();
		return new DataResponse<>(lista, "").getResult(HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<DataResponse> findByCompany(Integer value) throws Exception {
		List<BranchOffice> lista = branchOfficeDao.findByCompanyIdAndStatusTrue(value);
		return new DataResponse<>(lista, "").getResult(HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request, Integer companyId) throws Exception {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("id, address, company_id, description, name, status");
		sql.setFrom("branch_offices");
		sql.setWhere("status=true and company_id = :xcompanyId");
		sql.addParameter("xcompanyId", companyId);
		DataTableResults<BranchOfficeM> result = utilDataTableS.list(request, BranchOfficeM.class, sql);
		if(result != null && !result.getListOfDataObjects().isEmpty())
		for (BranchOfficeM item : result.getListOfDataObjects()) {
			item.setCompany(companyDao.findById(item.getCompanyId()).orElse(null));
		}
		return result;
	}

	@Override
	@Transactional
	public ResponseEntity<DataResponse> save(BranchOffice value) throws Exception {
		BranchOffice entitySave = branchOfficeDao.save(value);
		if(entitySave != null) {
			return new DataResponse<>(entitySave, "Se registro con exito")
					.getResult(HttpStatus.OK);
		} else {
			return new DataResponse().getResult(HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<DataResponse> delete(BranchOffice value) throws Exception {
		boolean isDelete = branchOfficeDao.deleteLogic(value.getId()) > 0;
		if(isDelete) {
			return new DataResponse<>("Se elimino exitosamente").getResult(HttpStatus.OK);
		} else {
			return new DataResponse<>("No se logro eliminar").getResult(HttpStatus.BAD_REQUEST);
		}
	}

}
