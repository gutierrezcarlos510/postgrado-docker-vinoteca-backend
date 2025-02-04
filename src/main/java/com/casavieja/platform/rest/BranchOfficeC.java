/**
 *
 */
package com.casavieja.platform.rest;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.BranchOffice;
import com.casavieja.platform.services.BranchOfficeS;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author CARLOS
 *
 */
@Tag(name = "Sucursal", description = "Gestiona las sucursales del sistema")
@RestController
@RequestMapping("branchOffice/*")
public class
BranchOfficeC {
    private final BranchOfficeS branchOfficeS;

    @Autowired
    public BranchOfficeC(BranchOfficeS branchOfficeS) {
        this.branchOfficeS = branchOfficeS;
    }

    @RequestMapping("listPage")
    public ResponseEntity<DataTableResults> listPage(HttpServletRequest request, @RequestParam(name = "companyId") Integer companyId) throws Exception{
        return ResponseEntity.ok(branchOfficeS.list(request,companyId));

    }
    @GetMapping("list")
    public ResponseEntity<DataResponse> listPage() throws Exception {
        return branchOfficeS.listActive();
    }

    @PostMapping("save")
    public ResponseEntity<DataResponse> save(@Valid @RequestBody BranchOffice value) throws Exception {
        return branchOfficeS.save(value);
    }

    @PostMapping("delete")
    public ResponseEntity<DataResponse> delete(@RequestBody BranchOffice value) throws Exception {
        return branchOfficeS.delete(value);
    }

    @GetMapping("findByCompany/{value}")
    public ResponseEntity<DataResponse> findByCompany(@PathVariable Integer value) throws Exception {
        return branchOfficeS.findByCompany(value);
    }
}
