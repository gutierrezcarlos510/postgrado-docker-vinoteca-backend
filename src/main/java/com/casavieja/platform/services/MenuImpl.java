/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.platform.dao.MenuDao;
import com.casavieja.platform.dao.MenuSubmenuDao;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Menu;
import com.casavieja.platform.models.MenuM;
import com.casavieja.utils.UtilDataTableS;
import com.casavieja.utils.UtilValidation;
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
public class MenuImpl implements MenuS{

	private final MenuDao menuDao;
	private final MenuSubmenuDao menuSubmenuDao;
	private final UtilDataTableS utilDataTableS;

	@Transactional
	public ResponseEntity<DataResponse> save(Menu value) throws Exception {
		Menu menu = menuDao.save(value);
		return new DataResponse<>(menu, "Registro exitoso")
				.getResult(HttpStatus.OK);
	}
	@Transactional
	public ResponseEntity<DataResponse> delete(Menu value) throws Exception {
		menuDao.deleteLogic(value.getId());
		return new DataResponse("Eliminacion exitosa")
				.getResult(HttpStatus.OK);
	}
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) throws Exception {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("id,description,icon,name,status,url");
		sql.setFrom("menus");
		sql.setWhere("status=true");
		DataTableResults<MenuM> result = utilDataTableS.list(request, MenuM.class, sql);
		return result;
	}
	@Transactional
	public ResponseEntity<DataResponse> saveAssign(Integer menuId,List<Integer> submenuList) throws Exception {
		menuSubmenuDao.deleteAllByMenu(menuId);
		if(UtilValidation.exist(submenuList)) {
			for (Integer submenuId : submenuList) {
				menuSubmenuDao.saveAssign(menuId, submenuId);
			}
		}
		return new DataResponse("Asignacion de submenus exitosa")
				.getResult(HttpStatus.OK);
	}
	@Transactional(readOnly = true)
	public ResponseEntity<DataResponse>  findByRol(Integer value) throws Exception {
		List<Menu> lista = menuDao.findByRol(value);
		return new DataResponse(lista, "").getResult(HttpStatus.OK);
	}
	@Transactional(readOnly = true)
	public ResponseEntity<DataResponse> listActive() throws Exception {
		List<Menu> lista = menuDao.findByStatusTrue();
		return new DataResponse(lista, "").getResult(HttpStatus.OK);
	}
}
