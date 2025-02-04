/**
 * 
 */
package com.casavieja.platform.rest;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Management;
import com.casavieja.platform.services.ManagementS;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author CARLOS
 *
 */
@Slf4j
@Tag(name = "Gestiones", description = "Administras las diferentes gestions de una sucursal")
@RestController
@RequestMapping("management/*")
public class ManagementC {
	private final ManagementS managementS;
	@Autowired
	public ManagementC(ManagementS managementS){
		this.managementS = managementS;
	}

	@RequestMapping("listPage")
	public ResponseEntity<DataTableResults> listPage(HttpServletRequest request, @RequestParam(name = "branchOfficeId") Integer branchOfficeId) throws Exception{
		return ResponseEntity.ok(managementS.list(request, branchOfficeId));
	}
	@GetMapping("list")
	public ResponseEntity<DataResponse> list() throws Exception{
		return managementS.listActive();
	}
	@GetMapping("listarPorSucursal/{id}")
	public ResponseEntity<DataResponse> listarPorSucursal(@PathVariable(name = "id") Integer sucursalId) throws Exception{
		return managementS.findByBranchOffice(sucursalId);
	}
	@PostMapping("save")
	public ResponseEntity<DataResponse> save(@Valid @RequestBody Management value) throws Exception {
		log.info("-->"+value.getStartDate());
		log.info("-->"+value.getEndDate());
		return managementS.save(value);
	}
	@PostMapping("delete")
	public ResponseEntity<DataResponse> delete(@RequestBody Management value) throws Exception {
		return managementS.delete(value);
	}
}
