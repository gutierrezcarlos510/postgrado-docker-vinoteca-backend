/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.dto.form.SalidaEntregaForm;
import com.casavieja.business.dto.form.SalidaForm;
import com.casavieja.business.entities.SalidaEntity;
import com.casavieja.business.enums.SalidaE;
import com.casavieja.business.model.SalidaM;
import com.casavieja.business.services.SalidaS;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.auth.JWTService;
import com.casavieja.platform.dao.BranchOfficeDao;
import com.casavieja.platform.dao.CompanyDao;
import com.casavieja.platform.dao.PersonDao;
import com.casavieja.platform.dao.SystemUserDao;
import com.casavieja.platform.entities.BranchOffice;
import com.casavieja.platform.entities.Company;
import com.casavieja.platform.entities.Person;
import com.casavieja.platform.entities.SystemUser;
import com.casavieja.platform.models.PersonM;
import com.casavieja.platform.services.PersonS;
import com.casavieja.utils.DataReport;
import com.casavieja.utils.UtilReportes;
import com.casavieja.utils.enums.ReportE;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.hibernate.Hibernate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.stream.Collectors;

/**
 * @author CARLOS
 *
 */
@RestController

@RequiredArgsConstructor
public class SalidaC {

    private final SalidaS salidaS;
    private final JWTService jwtService;
    private final SystemUserDao systemUserDao;
    private final BranchOfficeDao branchOfficeDao;
    private final CompanyDao companyDao;
    private final DataSource dataSource;
    private final PersonDao personS;
    private final ResourceLoader resourceLoader;

    @RequestMapping("salida/listPage")
    public ResponseEntity<DataTableResults<SalidaM>> listPage(HttpServletRequest request, String estadoSalida) {
        return ResponseEntity.ok(salidaS.list(request, estadoSalida));
    }

    @GetMapping("salida/findById/{salidaId}")
    public ResponseEntity<SalidaForm> findbyId(@PathVariable Long salidaId) {
        return ResponseEntity.ok(salidaS.findById(salidaId));
    }

    @PostMapping("salida/save")
    public ResponseEntity<SalidaEntity> save(HttpServletRequest req, @RequestBody SalidaForm value) {
        Long userId = jwtService.getUserIdSession(req);
        Integer sucursalActiva = jwtService.getSucursalActiva(req);
        value.setCreatedBy(userId);
        value.setBranchOfficeId(sucursalActiva);
        value.setEstadoSalida(SalidaE.ACTIVO);
        SalidaEntity obj = salidaS.save(value);
        if(obj != null) {
            return ResponseEntity.ok(obj);
        } else {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @PostMapping("salida/aumentar")
    public ResponseEntity<Boolean> aumentar(HttpServletRequest req, @RequestBody SalidaForm value) {
        Long userId = jwtService.getUserIdSession(req);
        value.setCreatedBy(userId);
        boolean exitSave = salidaS.aumentarEntrega(value);
        if(exitSave) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }

    }

    @DeleteMapping("salida/delete/{id}")
    public ResponseEntity<SalidaEntity> delete(HttpServletRequest req, @PathVariable Long id) {
        Long userId = jwtService.getUserIdSession(req);
        SalidaEntity obj = salidaS.delete(id, userId);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("salida/existeSalidaPorDistribuidorEstadoActivo/{distribuidorId}")
    public ResponseEntity<Boolean> existeSalidaPorDistribuidorEstadoActivo(HttpServletRequest req,@PathVariable Long distribuidorId) {
        if(distribuidorId == 0) {
            distribuidorId = jwtService.getUserIdSession(req);
        }
        return ResponseEntity.ok(salidaS.validarExistenciaSalidaPorDistribuidorEstadoActivo(distribuidorId));
    }
    @GetMapping("salida/findSalidaActivaByDistribuidor/{distribuidorId}")
    public ResponseEntity<SalidaForm> findSalidaActivaByDistribuidor(HttpServletRequest req,@PathVariable Long distribuidorId) {
        if(distribuidorId == 0) {
            distribuidorId = jwtService.getUserIdSession(req);
        }
        return ResponseEntity.ok(salidaS.findSalidaActivaByDistribuidor(distribuidorId));
    }
    // consumir findSalidaFormWithResumenDetalleParaFinalizar
    @GetMapping("salida/findSalidaFormWithResumenDetalleParaFinalizar/{salidaId}")
    public ResponseEntity<SalidaForm> findSalidaFormWithResumen(@PathVariable Long salidaId) {
        return ResponseEntity.ok(salidaS.findSalidaFormWithResumenDetalleParaFinalizar(salidaId));
    }
    @PostMapping("salida/finalizarSalida")
    public ResponseEntity<Boolean> finalizarSalida(HttpServletRequest req, @RequestBody SalidaForm value) {
        Long userId = jwtService.getUserIdSession(req);
        value.setCreatedBy(userId);
        boolean exitSave = salidaS.finalizarSalida(value);
        if(exitSave) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }
    @GetMapping("salida/reporteSalidaEntreFechas")
    public ResponseEntity<Resource> reporteIngresoEgreso(HttpServletRequest req, @RequestParam String fechaInicio, @RequestParam String fechaFin) {
        try {
            Long userId = jwtService.getUserIdSession(req);
            Integer sucursalId = jwtService.getSucursalActiva(req);
            SystemUser user = systemUserDao.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            BranchOffice sucursal = branchOfficeDao.findById(sucursalId).orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            Company company = companyDao.findById(sucursal.getCompanyId()).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

            String nameReport = "Salidas_entre_fechas";
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

    @GetMapping("salida/reporteSalida")
    public ResponseEntity<Resource> reporteSalida(HttpServletRequest req, @RequestParam Long salidaId, @RequestParam Integer entregaId) {
        try {
            Long userId = jwtService.getUserIdSession(req);
            Integer sucursalId = jwtService.getSucursalActiva(req);

            Person user = personS.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            BranchOffice sucursal = branchOfficeDao.findById(sucursalId)
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

            SalidaForm salidaM = salidaS.findById(salidaId);

            SalidaEntregaForm entrega = salidaM.getEntregaList().stream()
                    .filter(e -> e.getId().equals(entregaId.shortValue())) // Convertir entregaId (Integer) a Short
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Entrega con ID " + entregaId + " no encontrada"));
            SystemUser distribuidor = systemUserDao.findById(salidaM.getDistribuidorId())
                    .orElseThrow(() -> new RuntimeException("Distribuidor no encontrado"));
            Person personM = personS.findById(distribuidor.getId()).orElseThrow(() -> new RuntimeException("Persona del distribuidor no encontrada"));
            String nombreDistribuidor = personM.getFullname();
            String ciDistribuidor = personM.getCi();
            String celularDistribuidor = personM.getNumeroCelular();

            String nameReport = "reporte_entrega";
            DataReport dataReport = new DataReport(nameReport, ReportE.PDF, nameReport + ".jasper");
            dataReport.addParametro("xsucursal", sucursal.getName());
            dataReport.addParametro("direccion", sucursal.getAddress());
            dataReport.addParametro("sucursalId", sucursalId);
            dataReport.addParametro("xusuario", user.getFullname());
            dataReport.addParametro("xfecha", salidaM.getFecha());
            dataReport.addParametro("registrado", salidaM.getXusuario());
            dataReport.addParametro("xdistribuidor", nombreDistribuidor);
            dataReport.addParametro("ci", ciDistribuidor);
            dataReport.addParametro("celular", celularDistribuidor);
            dataReport.addParametro("observaciones", salidaM.getObs());
            dataReport.addParametro("distribuidorId", salidaM.getDistribuidorId());
            dataReport.addParametro("salidaId", salidaId);
            dataReport.addParametro("entregaId", entregaId);
            dataReport.addParametro("descuentoGeneral", salidaM.getTotalDescuento());
            dataReport.addParametro("totalGeneral", salidaM.getTotalGeneral());
            dataReport.addParametro("estadoSalida", salidaM.getEstadoSalida().toString());
            dataReport.addParametro("xfechaHora", entrega.getCreatedAt());

            UtilReportes utilReport = new UtilReportes();
            return utilReport.generate(dataReport, dataSource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }



}
