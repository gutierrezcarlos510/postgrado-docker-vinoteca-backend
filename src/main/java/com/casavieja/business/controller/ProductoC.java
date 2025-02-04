/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.dto.form.ProductoForm;
import com.casavieja.business.entities.ProductoEntity;
import com.casavieja.business.model.ProductoM;
import com.casavieja.business.services.ProductoS;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.utils.MyConstants;
import com.casavieja.utils.UploadFileS;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author CARLOS
 *
 */
@RestController
@RequiredArgsConstructor
public class ProductoC {

    private final ProductoS productoS;
    private final UploadFileS uploadFileS;

    @RequestMapping("producto/listPage")
    public ResponseEntity<DataTableResults<ProductoM>> listPage(HttpServletRequest request) {
        return ResponseEntity.ok(productoS.list(request));

    }
    @GetMapping("producto/list")
    public ResponseEntity<List<ProductoEntity>> list() {
        return productoS.listActive();
    }
    @GetMapping("producto/findById/{productoId}")
    public ResponseEntity<ProductoEntity> findbyId(@PathVariable Integer productoId) {
        return ResponseEntity.ok(productoS.findById(productoId));
    }

    @PostMapping("producto/save")
    public ResponseEntity<ProductoEntity> save(@RequestBody ProductoEntity value) {
        value.setFoto("producto.png");
        value.setPvCajaDescuento(BigDecimal.ZERO);
        value.setPvUnitDescuento(BigDecimal.ZERO);
        value.setPvCajaPromo(BigDecimal.ZERO);
        value.setPvUnitPromo(BigDecimal.ZERO);
        value.setPresentacionUnitId((short)1);
        value.setPresentacionCajaId((short)2);
        ProductoEntity obj = productoS.save(value);
        return ResponseEntity.ok(obj);
    }

    @PostMapping(value = "producto/saveFile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductoEntity> saveFile(ProductoForm value) {
        value.setPvCajaDescuento(BigDecimal.ZERO);
        value.setPvUnitDescuento(BigDecimal.ZERO);
        value.setPvCajaPromo(BigDecimal.ZERO);
        value.setPvUnitPromo(BigDecimal.ZERO);
        value.setPresentacionUnitId((short)1);
        value.setPresentacionCajaId((short)2);
        return ResponseEntity.ok(productoS.save(value));
    }

    @PatchMapping("producto/update/{id}")
    public ResponseEntity<ProductoEntity> update(@PathVariable Integer id,@Valid @RequestBody ProductoEntity value) {
        ProductoEntity obj = productoS.update(id, value);
        return ResponseEntity.ok(obj);
    }
    @PostMapping(value = "producto/updateFile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductoEntity> updateFile(ProductoForm value) {
        value.setPvCajaDescuento(BigDecimal.ZERO);
        value.setPvUnitDescuento(BigDecimal.ZERO);
        value.setPvCajaPromo(BigDecimal.ZERO);
        value.setPvUnitPromo(BigDecimal.ZERO);
        value.setPresentacionUnitId((short)1);
        value.setPresentacionCajaId((short)2);
        return ResponseEntity.ok(productoS.update(value));
    }

    @DeleteMapping("producto/delete/{id}")
    public ResponseEntity<ProductoEntity> delete(@PathVariable Integer id) {
        ProductoEntity obj = productoS.delete(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("producto/findByCategoria/{value}")
    public ResponseEntity<List<ProductoEntity>> findByCategoria(@PathVariable Short value) {
        return productoS.findByCategoria(value);
    }
}
