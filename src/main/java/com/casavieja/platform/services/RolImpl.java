/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import com.casavieja.platform.dao.*;
import com.casavieja.platform.data.DataSecurityMatcher;
import com.casavieja.platform.entities.*;
import com.casavieja.platform.models.RolAccesoM;
import com.casavieja.utils.MyConstants;
import com.casavieja.utils.UtilDataTableS;
import com.casavieja.utils.UtilValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
public class RolImpl implements RolS {

	private final RolDao rolDao;
	private final RolMenuDao rolMenuDao;
	private final RolTaskDao rolTaskDao;
	private final TaskDao taskDao;
	private final UtilDataTableS utilDataTableS;
	private final MenuDao menuDao;
	private final SubmenuDao submenuDao;
	private final RolAccesoDao rolAccesoDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<Rol>> listActive() {
		List<Rol> lista = rolDao.findByStatusTrue();
		return ResponseEntity.ok(lista);
	}

	@Override
	@Transactional(readOnly = true)
	public DataTableResults list(HttpServletRequest request) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("id,description,icon,name,status,authority");
		sql.setFrom("roles");
		sql.setWhere("status=true");
		DataTableResults<Rol> result=utilDataTableS.list(request, Rol.class, sql);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<Rol>> findBySystemUser(Long id) {
		return ResponseEntity.ok(rolDao.findBySystemUser(id));
	}

	@Override
	@Transactional
	public ResponseEntity<Rol> save(Rol value) {
		value.setStatus(MyConstants.ACTIVE);
		Rol saveEntity = rolDao.save(value);
		return ResponseEntity.ok(saveEntity);
	}
	@Override
	@Transactional
	public ResponseEntity delete(Rol value) {
		boolean isDeleted = rolDao.deleteLogic(value.getId()) > 0;
		return new ResponseEntity(isDeleted?HttpStatus.OK:HttpStatus.NOT_FOUND);
	}
	@Transactional
	public ResponseEntity saveAssign(Integer rolId, List<Integer> menuList) {
		rolMenuDao.deleteAllByRol(rolId);
		if(UtilValidation.exist(menuList)) {
			for (Integer menuId : menuList) {
				rolMenuDao.saveAssign(rolId, menuId);
			}
		}
		return ResponseEntity.ok()
				.header("msg", "Asignacion de menus exitoso")
				.body(true);
	}
	@Transactional
	public ResponseEntity saveAssignTask(Rol value, List<Integer> vecTask) {
		rolTaskDao.deleteAllByRol(value.getId());;
		if(UtilValidation.exist(vecTask)) {
			for (Integer taskId : vecTask) {
				rolTaskDao.insert(value.getId(), taskId);
			}
		}
		return ResponseEntity.ok()
				.header("msg", "Asignacion de tareas exitoso")
				.body(true);
	}
	@Transactional(readOnly = true)
	public List<DataSecurityMatcher> listActiveSecurity() {
		List<RolTask> listRolTask = (List<RolTask>) rolTaskDao.findAll();
		List<DataSecurityMatcher> matcherList = new ArrayList<DataSecurityMatcher>();
		if(UtilValidation.exist(listRolTask)) {
			for (RolTask rolTask : listRolTask) {
				Rol rol = rolDao.findById(rolTask.getRolId()).orElse(null);
				Task task = taskDao.findById(rolTask.getTaskId()).orElse(null);
				DataSecurityMatcher data = new DataSecurityMatcher();
				data.setNameRole(rol.getAuthority().substring(5));
				data.setNameTask(task.getUrl());
				matcherList.add(data);
			}
		}
		return matcherList;
	}
	@Override
	public ResponseEntity<List<Menu>> listarMenuPorRol(Integer rolId) {
		List<Menu> menuList = menuDao.findByRol(rolId);
		List<RolAcceso> rolAccesoList = rolAccesoDao.findByRolId(rolId);
		if(menuList != null && !menuList.isEmpty()) {
			for (Menu menu : menuList) {
				List<Submenu> submenus = submenuDao.findByMenu(menu.getId());
				List<Submenu> aux = new ArrayList<>();
				for (Submenu it : submenus) {
					aux.add(new Submenu(it.getId(), it.getName(), it.getDescription(), it.getUrl(), it.getIcon(), it.getStatus(), it.getTieneAcceso()));
				}
				menu.setSubmenuList(aux);
				if(rolAccesoList != null && !rolAccesoList.isEmpty()) {
					for (Submenu submenu : menu.getSubmenuList()) {
						submenu.setTieneAcceso(false);
						for (RolAcceso it : rolAccesoList) {
							if(it.getMenuId() == menu.getId() && it.getSubmenuId() == submenu.getId()) {
								submenu.setTieneAcceso(true);
								break;
							}
						}
					}
				}
			}
		}
		return ResponseEntity.ok(menuList);
	}
	@Override
	public ResponseEntity saveRolAcceso(@RequestBody RolAccesoM rolAccesoM) {
		RolAcceso rolAcceso = new RolAcceso();
		rolAcceso.setRolId(rolAccesoM.getRolId());
		rolAcceso.setMenuId(rolAccesoM.getMenuId());
		rolAcceso.setSubmenuId(rolAccesoM.getSubmenuId());
		if(rolAccesoM.isEsActivar()) {
			rolAccesoDao.save(rolAcceso);
			return ResponseEntity.ok()
					.header("msg","Asignacion exitosa")
					.body(true);
		} else {
			rolAccesoDao.delete(rolAcceso);
			return ResponseEntity.ok()
					.header("msg","Eliminacion de Asignacion exitosa")
					.body(true);
		}
	}
}
