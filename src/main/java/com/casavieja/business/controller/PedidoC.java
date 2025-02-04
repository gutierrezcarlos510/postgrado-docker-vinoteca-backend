/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.dto.form.PedidoForm;
import com.casavieja.business.entities.PedidoEntity;
import com.casavieja.business.enums.PedidoE;
import com.casavieja.business.enums.TipoPedidoE;
import com.casavieja.business.model.PedidoM;
import com.casavieja.business.services.PedidoS;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.auth.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author CARLOS
 *
 */
@RestController
@RequiredArgsConstructor
public class PedidoC {

    private final PedidoS pedidoS;
    private final JWTService jwtService;

    @RequestMapping("pedido/listPageByDistribuidor")
    public ResponseEntity<DataTableResults<PedidoM>> listPageByDistribuidor(HttpServletRequest request, boolean status, String tipo, Long distribuidorId) {
        return ResponseEntity.ok(pedidoS.listByDistribuidor(request, status, tipo, distribuidorId));
    }

    @GetMapping("pedido/findById/{pedidoId}")
    public ResponseEntity<PedidoM> findbyId(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pedidoS.findById(pedidoId));
    }

    @PostMapping("pedido/saveByUsuario")
    public ResponseEntity<PedidoEntity> saveByUsuario(HttpServletRequest req, @RequestBody PedidoForm value) {
        Long userId = jwtService.getUserIdSession(req);
        value.setUsuarioId(userId);
        value.setTipo(TipoPedidoE.POR_USUARIO);
        value.setEstado(PedidoE.PEDIDO_RECIBIDO.value);
        PedidoEntity obj = pedidoS.save(value);
        if(obj != null) {
            return ResponseEntity.ok(obj);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PostMapping("pedido/saveByDistribuidor")
    public ResponseEntity<PedidoEntity> saveByDistribuidor(HttpServletRequest req, @RequestBody PedidoForm value) {
        Long userId = jwtService.getUserIdSession(req);
        value.setUsuarioId(userId);
        value.setDistribuidorId(userId);
        value.setTipo(TipoPedidoE.POR_DISTRIBUIDOR);
        value.setEstado(PedidoE.EN_PROCESO.value);
        PedidoEntity obj = pedidoS.save(value);
        if(obj != null) {
            return ResponseEntity.ok(obj);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("pedido/saveByCliente")
    public ResponseEntity<PedidoEntity> saveByCliente(HttpServletRequest req, @RequestBody PedidoForm value) {
        Long userId = jwtService.getUserIdSession(req);
        value.setClienteId(userId);
        value.setTipo(TipoPedidoE.POR_USUARIO);
        value.setEstado(PedidoE.PEDIDO_RECIBIDO.value);
        PedidoEntity obj = pedidoS.save(value);
        if(obj != null) {
            return ResponseEntity.ok(obj);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("pedido/revisar")
    public ResponseEntity<Boolean> revisar(@RequestBody PedidoEntity value) {
        try {
            pedidoS.review(value.getId(), value.getEstado());
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PostMapping("pedido/asignar")
    public ResponseEntity<Boolean> asignar(@RequestBody PedidoForm value) {
        try {
            pedidoS.assign(value.getId(), value.getDistribuidorId());
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @DeleteMapping("pedido/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            pedidoS.delete(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
