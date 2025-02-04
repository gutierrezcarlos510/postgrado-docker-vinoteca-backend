package com.casavieja.platform.services;

import com.casavieja.platform.dao.PersonDao;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Person;
import com.casavieja.platform.models.PersonM;
import com.casavieja.utils.MyConstants;
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

@RequiredArgsConstructor
@Service
public class PersonImpl implements PersonS {

	private final PersonDao personDao;
	private final UtilDataTableS utilDataTableS;

	private static final String SIN_CARNET = "0";

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<DataResponse> listActive() throws Exception {
		List<Person> lista = personDao.findByStatusTrue();
		return new DataResponse(lista, "").getResult(HttpStatus.OK);
	}
	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) throws Exception {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("id,ci,first_lastname,gender,name,second_lastname,status");
		sql.setFrom("persons");
		sql.setWhere("status=true");
		DataTableResults<PersonM> result=utilDataTableS.list(request, PersonM.class, sql);
		return result;
	}
	@Override
	@Transactional
	public ResponseEntity<DataResponse> save(Person value) throws Exception {
		value.setStatus(MyConstants.ACTIVE);
		personDao.save(value);
		return new DataResponse<>("Registro exitoso")
				.getResult(HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<DataResponse> delete(Person value) throws Exception {
		personDao.deleteLogic(value.getId());
		return new DataResponse<>("Eliminacion exitosa")
				.getResult(HttpStatus.OK);
	}
	@Override
	@Transactional
	public ResponseEntity<DataResponse> update(Person value) throws Exception {
		Person entity = personDao.findById(value.getId()).orElse(null);
		if(entity == null) {
			return new DataResponse<>("El ID de la persona no se encuentra en el sistema")
					.getResult(HttpStatus.BAD_REQUEST);
		}
		if (value.getCi() == null || (value.getCi() != null && value.getCi().isEmpty())) {
			entity.setCi(SIN_CARNET);
		} else {
			entity.setCi(value.getCi());
		}
		entity.setName(value.getName());
		entity.setFirstLastname(value.getFirstLastname());
		entity.setSecondLastname(value.getSecondLastname());
		entity.setGender(value.getGender());
		personDao.save(entity);
		return new DataResponse<>("Actualizacion exitosa")
				.getResult(HttpStatus.OK);
	}
	@Override
	public ResponseEntity<DataResponse> obtener(long personId) throws Exception {
		Person entity = personDao.findById(personId).orElse(null);
		return new DataResponse(entity, "Consulta exitosa").getResult(HttpStatus.OK);
	}
}
