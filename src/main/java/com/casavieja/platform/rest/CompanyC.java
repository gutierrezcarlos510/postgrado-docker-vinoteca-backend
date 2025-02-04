/**
 *
 */
package com.casavieja.platform.rest;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Company;
import com.casavieja.platform.services.CompanyS;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@Tag(name = "Empresa", description = "Gestiona las empresas del sistema")
@RestController
@RequestMapping("company/*")
public class CompanyC {
    private final CompanyS companyS;

    @Autowired
    public CompanyC(CompanyS companyS) {
        this.companyS = companyS;
    }

    @RequestMapping("listPage")
    public ResponseEntity<DataTableResults> listPage(HttpServletRequest request) throws Exception{
        return ResponseEntity.ok(companyS.list(request));
    }
    @GetMapping("list")
    public ResponseEntity<List<Company>> listPage() throws Exception {
        return companyS.listActive();
    }

    @PostMapping("save")
    public ResponseEntity<DataResponse> save(@Valid @RequestBody Company value) throws Exception {
        return companyS.save(value);
    }

    @PostMapping("delete")
    public ResponseEntity<DataResponse> delete(@RequestBody Company value) throws Exception {
        return companyS.delete(value);
    }

    @GetMapping("findBySystemUser/{value}")
    public ResponseEntity<DataResponse> findBySystemUser(@PathVariable Long value) throws Exception {
        return companyS.findBySystemUser(value);
    }
}
