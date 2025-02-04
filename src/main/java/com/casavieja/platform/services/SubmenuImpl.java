package com.casavieja.platform.services;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import com.casavieja.platform.dao.SubmenuDao;
import com.casavieja.platform.entities.Submenu;
import com.casavieja.platform.models.SubmenuM;
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
public class SubmenuImpl implements SubmenuS {

	private final SubmenuDao submenuDao;
	private final UtilDataTableS utilDataTableS;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<Submenu>> listActive() {
		return ResponseEntity.ok().body(submenuDao.findByStatusTrue());
	}
	@Override
	@Transactional
	public ResponseEntity<Submenu> save(Submenu value) {
		Submenu entitySave = submenuDao.save(value);
		return ResponseEntity.ok()
				.header("msg", "Registro Exitoso")
				.body(entitySave);
	}
	@Override
	@Transactional
	public ResponseEntity delete(Integer id) {
		boolean isDeleted = submenuDao.deleteLogic(id) > 0;
		return new ResponseEntity(isDeleted?HttpStatus.OK:HttpStatus.NOT_FOUND);
	}
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("id,name, description,icon,url,status");
		sql.setFrom("submenus");
		sql.setWhere("status = true");
		DataTableResults<SubmenuM> result = utilDataTableS.list(request, SubmenuM.class, sql);
		return result;
	}
	@Transactional(readOnly = true)
	public ResponseEntity<List<Submenu>> findByMenu(Integer value) {
		List<Submenu> submenuList = submenuDao.findByMenu(value);
		return ResponseEntity.ok().body(submenuList);
	}
}
