/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.entities.AlmacenEntity;
import com.casavieja.business.model.AlmacenDistribuidorM;
import com.casavieja.business.model.AlmacenM;
import com.casavieja.business.model.ResumenAlmacenDistribuidorM;
import com.casavieja.business.services.AlmacenS;
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
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AlmacenC {

    private final AlmacenS almacenS;
    private final JWTService jwtService;
    private final BranchOfficeDao branchOfficeDao;
    private final CompanyDao companyDao;
    private final SystemUserDao systemUserDao;
    private final DataSource dataSource;

    @PostMapping("almacen/listPage")
    public ResponseEntity<DataTableResults<AlmacenM>> listPage(HttpServletRequest request, String statusFilterStock) {
        int sucursalId = jwtService.getSucursalActiva(request);
        return ResponseEntity.ok(almacenS.list(request,sucursalId, statusFilterStock));

    }
    @GetMapping("almacen/findByProductoId/{productoId}")
    public ResponseEntity<List<AlmacenEntity>> findByProductoId(@PathVariable Integer productoId) {
        return almacenS.findByProductoId(productoId);
    }
    @GetMapping("almacen/findByProductoIdTotal/{productoId}")
    public ResponseEntity<AlmacenM> findByProductoIdTotal(@PathVariable Integer productoId) {
        return almacenS.findByProductoIdTotal(productoId);
    }
    @GetMapping("almacen/listAlmacenByDistribuidor/{distribuidorId}")
    public ResponseEntity<List<AlmacenDistribuidorM>> listAlmacenByDistribuidor(@PathVariable Long distribuidorId) {
        return almacenS.almacenByDistribuidor(distribuidorId);
    }
    @GetMapping("almacen/listAlmacenDistribuidorBySalida/{salidaId}")
    public ResponseEntity<List<ResumenAlmacenDistribuidorM>> listAlmacenDistribuidorBySalida(@PathVariable Long salidaId) {
        return almacenS.listAlmacenDistribuidorBySalida(salidaId);
    }
    @GetMapping("almacen/reporteHistorico")
    public ResponseEntity<Resource> reporteHistorico(HttpServletRequest req, @RequestParam String fecha) {
        try {
            Long userId = jwtService.getUserIdSession(req);
            Integer sucursalId = jwtService.getSucursalActiva(req);
            SystemUser user =  systemUserDao.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            BranchOffice sucursal =  branchOfficeDao.findById(sucursalId).orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            Company company =  companyDao.findById(sucursal.getCompanyId()).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            String nameReport = "Inventario_por_fecha";
            DataReport dataReport = new DataReport(nameReport, ReportE.PDF, nameReport + ".jasper");
            dataReport.addParametro("xsucursal", sucursal.getName());
            dataReport.addParametro("direccion", sucursal.getAddress());
            dataReport.addParametro("telefono", company.getPhone());
            dataReport.addParametro("email", company.getEmail());
            dataReport.addParametro("xusuario", user.getAlias());
            dataReport.addParametro("xfechaReporte", fecha);
            dataReport.addParametro("sucursalId", sucursalId);
            UtilReportes utilReport = new UtilReportes();

            return utilReport.generate(dataReport, dataSource);
        } catch (Exception e) {
            log.error("reporte3", e);
            return ResponseEntity.notFound().build();
        }
    }

}
