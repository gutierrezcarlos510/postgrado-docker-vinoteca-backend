/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.platform.dao.BranchOfficeDao;
import com.casavieja.platform.dao.CompanyDao;
import com.casavieja.platform.dao.ManagementDao;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Management;
import com.casavieja.platform.models.ManagementM;
import com.casavieja.utils.MyConstants;
import com.casavieja.utils.UtilDataTableS;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
@Service
public class ManagementImpl implements ManagementS {

	private final ManagementDao managementDao;
	private final BranchOfficeDao branchOfficeDao;
	private final CompanyDao companyDao;
	private final UtilDataTableS utilDataTableS;
	private final UtilResponseS utilResponseS;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<DataResponse> listActive() throws Exception{
		List<Management> lista = managementDao.findByStatusTrue();
		return new DataResponse(lista,"").getResult(HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<DataResponse> findByBranchOffice(Integer value) throws Exception {
		List<Management> lista = managementDao.findByBranchOfficeIdAndStatusTrue(value);
		return new DataResponse(lista, "").getResult(HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request, Integer branchOfficeId) throws Exception {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("id, branch_office_id, end_date, start_date, status, year_number");
		sql.setFrom("managements");
		sql.setWhere("status=true and branch_office_id = :branch");
		sql.addParameter("branch", branchOfficeId);
		DataTableResults<ManagementM> result=utilDataTableS.list(request, ManagementM.class, sql);
		return result;
	}

	@Override
	@Transactional
	public ResponseEntity<DataResponse> save(Management value) throws Exception {
		value.setStatus(MyConstants.ACTIVE);
		log.info("-->"+value.getStartDate());
		log.info("-->"+value.getEndDate());
		managementDao.save(value);
		return new DataResponse<>("Registro exitoso")
				.getResult(HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<DataResponse> delete(Management value) throws Exception {
		if(value == null || (value != null && value.getId() == null)) {
			return new DataResponse<>("Datos de la gestion invalido")
					.getResult(HttpStatus.BAD_REQUEST);
		}
		managementDao.deleteLogic(value.getId());
		return new DataResponse<>("Se realizo con exito la eliminacion")
				.getResult(HttpStatus.OK);
	}

}
