/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.ClienteDao;
import com.casavieja.business.dto.form.ClienteForm;
import com.casavieja.business.entities.ClienteEntity;
import com.casavieja.business.mappers.ClientFormMapper;
import com.casavieja.business.model.ClienteM;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import com.casavieja.platform.dao.PersonDao;
import com.casavieja.platform.entities.Person;
import com.casavieja.utils.UtilDataTableS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
public class ClienteImpl implements ClienteS {

	private final ClienteDao clienteDao;
	private final PersonDao personDao;
	private final UtilDataTableS utilDataTableS;

	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("c.*,concat(p.name, ' ', p.first_lastname) as nombre_completo, p.numero_celular");
		sql.setFrom("business.cliente c");
		sql.addJoin("public.persons p on p.id = c.id");
		sql.setWhere("c.status = true");
		return utilDataTableS.list(request, ClienteM.class, sql);
	}

	@Override
	public ClienteForm findById(Long clienteId) {
		ClienteEntity clienteEntity = clienteDao.findById(clienteId).orElseThrow(()-> new RuntimeException("No existe Id"));
		Person person = personDao.findById(clienteId).orElseThrow(()-> new RuntimeException("No existe Id"));
		ClienteForm cli = new ClienteForm();
		cli.setId(clienteEntity.getId());
		cli.setAlias(clienteEntity.getAlias());
		cli.setDireccion(clienteEntity.getDireccion());
		cli.setEmail(clienteEntity.getEmail());
		cli.setNombreNegocio(clienteEntity.getNombreNegocio());
		cli.setDescripcionNegocio(clienteEntity.getDescripcionNegocio());
		cli.setTipoCliente(clienteEntity.getTipoCliente());
		cli.setBarrioId(clienteEntity.getBarrioId());
		cli.setName(person.getName());
		cli.setFirstLastname(person.getFirstLastname());
		cli.setNumeroCelular(person.getNumeroCelular());
		cli.setGender(person.getGender());
		return cli;
	}

	@Override
	@Transactional
	public ClienteForm update(Long id, ClienteForm value) throws Exception {
		ClientFormMapper mapper = new ClientFormMapper();
		Person entity = personDao.findById(id).orElse(null);
		if(entity == null) {
			throw new RuntimeException("El ID de la persona no se encuentra en el sistema");
		}
		entity.setName(value.getName());
		entity.setFirstLastname(value.getFirstLastname());
		entity.setGender(value.getGender());
		entity.setNumeroCelular(value.getNumeroCelular());
		Person personUpdated = personDao.save(entity);
		ClienteEntity cliente = clienteDao.findById(id).orElseThrow(()-> new RuntimeException("El ID de cliente no se encuentra"));
		cliente.setAlias(value.getAlias());
		cliente.setDireccion(value.getDireccion());
		cliente.setEmail(value.getEmail());
		cliente.setNombreNegocio(value.getNombreNegocio());
		cliente.setDescripcionNegocio(value.getDescripcionNegocio());
		cliente.setTipoCliente(value.getTipoCliente());
		cliente.setBarrioId(value.getBarrioId());
		ClienteEntity clienteUpdated = clienteDao.save(cliente);
		return mapper.fromEntity(personUpdated, clienteUpdated);
	}

	
	@Override
	@Transactional
	public ClienteEntity delete(Long clienteId) {
		ClienteEntity clienteDB = clienteDao.findById(clienteId).orElseThrow(()-> new RuntimeException("No existe ID"));
		boolean isDelete = clienteDao.deleteLogic(clienteDB.getId()) > 0;
		if(isDelete) {
			return clienteDB;
		} else {
			throw new RuntimeException("Existe un error al eliminar");
		}
	}

}
