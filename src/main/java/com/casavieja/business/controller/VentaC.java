/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.entities.VentaEntity;
import com.casavieja.business.enums.FormaPagoE;
import com.casavieja.business.enums.PedidoE;
import com.casavieja.business.enums.TipoPedidoE;
import com.casavieja.business.enums.TipoVentaE;
import com.casavieja.business.model.IngresoM;
import com.casavieja.business.model.VentaDetalleM;
import com.casavieja.business.model.VentaM;
import com.casavieja.business.services.PedidoS;
import com.casavieja.business.services.VentaS;
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
import java.math.BigDecimal;

/**
 * @author CARLOS
 *
 */
@RestController
@RequiredArgsConstructor
public class VentaC {

    private final VentaS ventaS;
    private final PedidoS pedidoS;
    private final JWTService jwtService;
    private final BranchOfficeDao branchOfficeDao;
    private final CompanyDao companyDao;
    private final SystemUserDao systemUserDao;
    private final DataSource dataSource;

    @RequestMapping("venta/listPageByDistribuidor")
    public ResponseEntity<DataTableResults> listPageByDistribuidor(HttpServletRequest request, boolean status, Long distribuidorId) {
        return ResponseEntity.ok(ventaS.listByDistribuidor(request, status, distribuidorId));
    }

    @RequestMapping("venta/listPageBySalida")
    public ResponseEntity<DataTableResults> listPageBySalida(HttpServletRequest request, boolean status, Long salidaId) {
        return ResponseEntity.ok(ventaS.listBySalida(request, status, salidaId));
    }

    @GetMapping("venta/findById/{ventaId}")
    public ResponseEntity<VentaM> findbyId(@PathVariable Long ventaId) {
        return ResponseEntity.ok(ventaS.findById(ventaId));
    }

    @PostMapping("venta/saveByDistribuidor")
    public ResponseEntity<VentaEntity> saveByDistribuidor(HttpServletRequest req, @RequestBody VentaM value) {
        Long userId = jwtService.getUserIdSession(req);
        Integer sucursalId = jwtService.getSucursalActiva(req);
        value.setUsuarioId(userId);
        value.setBranchOfficeId(sucursalId);
        value.setTipoVenta(TipoVentaE.CONTADO);
        value.setFormaPago(FormaPagoE.EFECTIVO);
        value.setImpuesto(BigDecimal.ZERO);
        VentaEntity obj = ventaS.save(value);
        if(value.getPedidoId() != null) {
            pedidoS.review(value.getPedidoId(), PedidoE.ENTREGADO.value);
        }
        if(obj != null) {
            return ResponseEntity.ok(obj);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("venta/deleteByDistribuidor/{ventaId}")
    public ResponseEntity<Boolean> deleteVenta(HttpServletRequest req, @PathVariable Long ventaId) {
        ventaS.eliminarVenta(ventaId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("ventas/reporteVenta")
    public ResponseEntity<Resource> reporteIngreso(HttpServletRequest req, @RequestParam Long ventaId) {
        try {
            Long userId = jwtService.getUserIdSession(req);
            Integer sucursalId = jwtService.getSucursalActiva(req);
            SystemUser user = systemUserDao.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            BranchOffice sucursal = branchOfficeDao.findById(sucursalId).orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            Company company = companyDao.findById(sucursal.getCompanyId()).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            VentaM ventaM =ventaS.findById(ventaId);

            String nameReport = "Ventas_individual";
            DataReport dataReport = new DataReport(nameReport, ReportE.PDF, nameReport + ".jasper");
            dataReport.addParametro("xsucursal", sucursal.getName());
            dataReport.addParametro("direccion", sucursal.getAddress());
            dataReport.addParametro("telefono", company.getPhone());
            dataReport.addParametro("email", company.getEmail());
            dataReport.addParametro("xfecha", ventaM.getFecha());
            dataReport.addParametro("xcliente",ventaM.getXcliente());
            dataReport.addParametro("xusuario", user.getAlias());
            dataReport.addParametro("descuentoGeneral",ventaM.getDescuento());
            dataReport.addParametro("total",ventaM.getTotal());
            dataReport.addParametro("sucursalId", sucursalId);
            dataReport.addParametro("codigoVenta", ventaId);
            dataReport.addParametro("clienteId",ventaM.getClienteId());

            UtilReportes utilReport = new UtilReportes();
            return utilReport.generate(dataReport, dataSource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
