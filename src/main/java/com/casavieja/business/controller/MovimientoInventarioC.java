/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.dao.MovimientoInventarioDao;
import com.casavieja.business.dto.form.MovimientoInventarioForm;
import com.casavieja.business.entities.MovimientoInventarioEntity;
import com.casavieja.business.enums.MovimientoInventarioE;
import com.casavieja.business.model.MovimientoInventarioM;
import com.casavieja.business.services.MovimientoInventarioS;
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

/**
 * @author CARLOS
 *
 */
@RestController
@RequiredArgsConstructor
public class MovimientoInventarioC {

    private final MovimientoInventarioS movimientoInventarioS;
    private final MovimientoInventarioDao movimientoInventarioDao;
    private final JWTService jwtService;
    private final BranchOfficeDao branchOfficeDao;
    private final CompanyDao companyDao;
    private final SystemUserDao systemUserDao;
    private final DataSource dataSource;

    @RequestMapping("movimientoInventario/listPage")
    public ResponseEntity<DataTableResults> listPage(HttpServletRequest request, Boolean status) {
        return ResponseEntity.ok(movimientoInventarioS.list(request, status));
    }

    @GetMapping("movimientoInventario/findById/{movimientoInventarioId}")
    public ResponseEntity<MovimientoInventarioM> findbyId(@PathVariable Long movimientoInventarioId) {
        return ResponseEntity.ok(movimientoInventarioS.findById(movimientoInventarioId));
    }

    @PostMapping("movimientoInventario/saveIngreso")
    public ResponseEntity<MovimientoInventarioEntity> saveIngreso(HttpServletRequest req, @RequestBody MovimientoInventarioForm value) {
        Long userId = jwtService.getUserIdSession(req);
        Integer sucursalActiva = jwtService.getSucursalActiva(req);
        value.setUsuarioId(userId);
        value.setCreatedBy(userId);
        value.setSucursalOrigen(sucursalActiva);
        value.setTipo(MovimientoInventarioE.ENTRADA.value);
        MovimientoInventarioEntity obj = movimientoInventarioS.saveIngreso(value);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("movimientoInventario/delete/{id}")
    public ResponseEntity<MovimientoInventarioEntity> delete(HttpServletRequest req,@PathVariable Long id) {
        Long userId = jwtService.getUserIdSession(req);
        MovimientoInventarioEntity obj = movimientoInventarioS.delete(id, userId);
        return ResponseEntity.ok(obj);
    }

    @PostMapping("movimientoInventario/saveEgreso")
    public ResponseEntity<MovimientoInventarioEntity> saveEgreso(HttpServletRequest req, @RequestBody MovimientoInventarioForm value) {
        Long userId = jwtService.getUserIdSession(req);
        Integer sucursalActiva = jwtService.getSucursalActiva(req);
        value.setUsuarioId(userId);
        value.setCreatedBy(userId);
        value.setSucursalOrigen(sucursalActiva);
        value.setTipo(MovimientoInventarioE.SALIDA.value);
        MovimientoInventarioEntity obj = movimientoInventarioS.saveEgreso(value);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("movimimento/reporteMovimientoIngreso")
    public ResponseEntity<Resource> reporteIngreso(HttpServletRequest req, @RequestParam Long ingresoId) {
        try {
            Long userId = jwtService.getUserIdSession(req);
            Integer sucursalId = jwtService.getSucursalActiva(req);
            SystemUser user = systemUserDao.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            BranchOffice sucursal = branchOfficeDao.findById(sucursalId).orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            Company company = companyDao.findById(sucursal.getCompanyId()).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            MovimientoInventarioM movimientoInventarioEntity =movimientoInventarioS.findById(ingresoId);

            String nameReport = "Movimiento_inventario_ingreso";
            DataReport dataReport = new DataReport(nameReport, ReportE.PDF, nameReport + ".jasper");
            dataReport.addParametro("xsucursal", sucursal.getName());
            dataReport.addParametro("direccion", sucursal.getAddress());
            dataReport.addParametro("telefono", company.getPhone());
            dataReport.addParametro("email", company.getEmail());
            dataReport.addParametro("xfechaModificacion", movimientoInventarioEntity.getFecha().toString());
            dataReport.addParametro("movimientoId", movimientoInventarioEntity.getId());
            dataReport.addParametro("xtipo", movimientoInventarioEntity.getTipo().toString());
            dataReport.addParametro("motivo",movimientoInventarioEntity.getMotivo());
            dataReport.addParametro("sucursalId", sucursalId);
            dataReport.addParametro("xusuario", user.getAlias());
            UtilReportes utilReport = new UtilReportes();
            return utilReport.generate(dataReport, dataSource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("movimimento/reporteMovimientoIngresoEgreso")
    public ResponseEntity<Resource> reporteIngresoEgreso(HttpServletRequest req, @RequestParam String fechaInicio, @RequestParam String fechaFin) {
        try {
            Long userId = jwtService.getUserIdSession(req);
            Integer sucursalId = jwtService.getSucursalActiva(req);
            SystemUser user = systemUserDao.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            BranchOffice sucursal = branchOfficeDao.findById(sucursalId).orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            Company company = companyDao.findById(sucursal.getCompanyId()).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

            String nameReport = "Movimietno_ingreso_egreso";
            DataReport dataReport = new DataReport(nameReport, ReportE.PDF, nameReport + ".jasper");
            dataReport.addParametro("sucursal", sucursal.getName());
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
