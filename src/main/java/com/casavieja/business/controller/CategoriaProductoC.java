/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.entities.CategoriaProductoEntity;
import com.casavieja.business.services.CategoriaProductoS;
import com.casavieja.pagination.DataTableResults;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@RestController
@RequiredArgsConstructor
public class CategoriaProductoC {

    private final CategoriaProductoS categoriaProductoS;

    @RequestMapping("categoriaProducto/listPage")
    public ResponseEntity<DataTableResults> listPage(HttpServletRequest request) {
        return ResponseEntity.ok(categoriaProductoS.list(request));

    }
    @GetMapping("categoriaProducto/list")
    public ResponseEntity<List<CategoriaProductoEntity>> list() {
        return categoriaProductoS.listActive();
    }

    @PostMapping("categoriaProducto/save")
    public ResponseEntity<CategoriaProductoEntity> save(@Valid @RequestBody CategoriaProductoEntity value) {
        CategoriaProductoEntity obj = categoriaProductoS.save(value);
        return ResponseEntity.ok(obj);
    }

    @PatchMapping("categoriaProducto/update/{id}")
    public ResponseEntity<CategoriaProductoEntity> update(@PathVariable Short id,@Valid @RequestBody CategoriaProductoEntity value) {
        CategoriaProductoEntity obj = categoriaProductoS.update(id, value);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("categoriaProducto/delete/{id}")
    public ResponseEntity<CategoriaProductoEntity> delete(@PathVariable Short id) {
        CategoriaProductoEntity obj = categoriaProductoS.delete(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("categoriaProducto/findByTipoProducto/{value}")
    public ResponseEntity<List<CategoriaProductoEntity>> findByTipoProducto(@PathVariable Short value) {
        return categoriaProductoS.findByTipoProducto(value);
    }
}
