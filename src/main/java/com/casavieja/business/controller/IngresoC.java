/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.dto.form.IngresoForm;
import com.casavieja.business.entities.IngresoEntity;
import com.casavieja.business.model.IngresoM;
import com.casavieja.business.services.IngresoS;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.auth.JWTService;
import com.casavieja.platform.dao.BranchOfficeDao;
import com.casavieja.platform.dao.CompanyDao;
import com.casavieja.platform.dao.SystemUserDao;
import com.casavieja.platform.entities.BranchOffice;
import com.casavieja.platform.entities.Company;
import com.casavieja.platform.entities.SystemUser;
import com.casavieja.utils.DataReport;
import com.casavieja.utils.UtilReportes;
import com.casavieja.utils.enums.ReportE;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Optional;

/**
 * @author CARLOS
 *
 */
@RestController
@RequiredArgsConstructor
public class IngresoC {

    private final IngresoS ingresoS;
    private final JWTService jwtService;
    private final BranchOfficeDao branchOfficeDao;
    private final CompanyDao companyDao;
    private final SystemUserDao systemUserDao;
    private final DataSource dataSource;

    @RequestMapping("ingreso/listPage")
    public ResponseEntity<DataTableResults> listPage(HttpServletRequest request,Boolean status) {
        return ResponseEntity.ok(ingresoS.list(request, status));

    }

    @GetMapping("ingreso/findById/{ingresoId}")
    public ResponseEntity<IngresoM> findbyId(@PathVariable Long ingresoId) {
        return ResponseEntity.ok(ingresoS.findById(ingresoId));
    }

    @PostMapping("ingreso/save")
    public ResponseEntity<IngresoEntity> save(HttpServletRequest req, @RequestBody IngresoForm value) {
        Long userId = jwtService.getUserIdSession(req);
        Integer sucursalActiva = jwtService.getSucursalActiva(req);
        value.setCreatedBy(userId);
        value.setBranchOfficeId(sucursalActiva);
        IngresoEntity obj = ingresoS.save(value);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("ingreso/delete/{id}")
    public ResponseEntity<IngresoEntity> delete(HttpServletRequest req,@PathVariable Long id) {
        Long userId = jwtService.getUserIdSession(req);
        IngresoEntity obj = ingresoS.delete(id, userId);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("ingreso/reporteIngreso")
    public ResponseEntity<Resource> reporteIngreso(HttpServletRequest req, @RequestParam Long ingresoId) {
        try {
            Long userId = jwtService.getUserIdSession(req);
            Integer sucursalId = jwtService.getSucursalActiva(req);
            SystemUser user = systemUserDao.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            BranchOffice sucursal = branchOfficeDao.findById(sucursalId).orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            Company company = companyDao.findById(sucursal.getCompanyId()).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            IngresoM ingresoM = ingresoS.findById(ingresoId);
            String nameReport = "Ingreso_Individual";
            DataReport dataReport = new DataReport(nameReport, ReportE.PDF, nameReport + ".jasper");
            dataReport.addParametro("xsucursal", sucursal.getName());
            dataReport.addParametro("direccion", sucursal.getAddress());
            dataReport.addParametro("telefono", company.getPhone());
            dataReport.addParametro("email", company.getEmail());
            dataReport.addParametro("xusuarioEntrega", ingresoM.getXusuarioEntrega());
            dataReport.addParametro("xUsuarioRecibido",ingresoM.getXusuarioRecepcion());
            dataReport.addParametro("xusuario", user.getAlias());
            dataReport.addParametro("sucursalId", sucursalId);
            dataReport.addParametro("ingresoId", ingresoId);
            dataReport.addParametro("usuarioEntregaId", ingresoM.getUsuarioEntregaId());
            dataReport.addParametro("usuarioRecibidoId",ingresoM.getUsuarioRecepcionId());

            UtilReportes utilReport = new UtilReportes();
            return utilReport.generate(dataReport, dataSource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("ingreso/reporteIngresoEntreFechas")
    public ResponseEntity<Resource> reporteIngresoEgreso(HttpServletRequest req, @RequestParam String fechaInicio, @RequestParam String fechaFin) {
        try {
            Long userId = jwtService.getUserIdSession(req);
            Integer sucursalId = jwtService.getSucursalActiva(req);
            SystemUser user = systemUserDao.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            BranchOffice sucursal = branchOfficeDao.findById(sucursalId).orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            Company company = companyDao.findById(sucursal.getCompanyId()).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

            String nameReport = "Ingreso_entre_fechas";
            DataReport dataReport = new DataReport(nameReport, ReportE.PDF, nameReport + ".jasper");
            dataReport.addParametro("xsucursal", sucursal.getName());
            dataReport.addParametro("direccion", sucursal.getAddress());
            dataReport.addParametro("telefono", company.getPhone());
            dataReport.addParametro("email", company.getEmail());
            dataReport.addParametro("fini",fechaInicio);
            dataReport.addParametro("ffin",fechaFin);
            dataReport.addParametro("sucursalId", sucursalId);
            dataReport.addParametro("xusuario", user.getAlias());
            UtilReportes utilReport = new UtilReportes();
            return utilReport.generate(dataReport, dataSource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

}
