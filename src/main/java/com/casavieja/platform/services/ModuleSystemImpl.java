/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.platform.dao.ModuleSystemDao;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.ModuleSystem;
import com.casavieja.platform.models.ModuleSystemM;
import com.casavieja.utils.UtilDataTableS;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
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
public class ModuleSystemImpl implements ModuleSystemS {

	private final ModuleSystemDao moduleSystemDao;
	private final UtilDataTableS utilDataTableS;

	@Override
	@Transactional
	public ResponseEntity<DataResponse> save(ModuleSystem value) throws Exception {
		moduleSystemDao.save(value);
		if(value.getCodeModule()==null || (value.getCodeModule()!= null && value.getCodeModule().isEmpty()))
			generateCodeModule(value);
		return new DataResponse<>("Registro exitoso")
				.getResult(HttpStatus.OK);
	}
	private void generateCodeModule(ModuleSystem value) {
		String code = value.getId().toString();
		for (int i = code.length(); i <= 3; i++) {
			code = "0"+code;
		}
		value.setCodeModule(value.getName().substring(0,3)+"-"+code);
		moduleSystemDao.save(value);
	}
	
	@Override
	@Transactional
	public ResponseEntity<DataResponse> delete(ModuleSystem value) throws Exception{
		moduleSystemDao.deleteLogic(value.getId());
		return new DataResponse<>("Eliminacion exitosa")
				.getResult(HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) throws Exception {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("id,code_module,description,name,status");
		sql.setFrom("modules_systems");
		sql.setWhere("status=true");
		DataTableResults<ModuleSystemM> result = utilDataTableS.list(request, ModuleSystemM.class, sql);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<DataResponse> listActive() throws Exception {
		List<ModuleSystem> lista = moduleSystemDao.findByStatusTrue();
		return new DataResponse(lista, "").getResult(HttpStatus.OK);
	}

}
