/**
 *
 */
package com.casavieja.business.controller;

import com.casavieja.business.dto.form.ClienteForm;
import com.casavieja.business.entities.ClienteEntity;
import com.casavieja.business.services.ClienteS;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.auth.JWTService;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.enums.TypeUserEnum;
import com.casavieja.platform.models.form.UserForm;
import com.casavieja.platform.services.SystemUserS;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author CARLOS
 *
 */
@RestController
@RequiredArgsConstructor
public class ClienteC {

    private final ClienteS clienteS;
    private final SystemUserS systemUserS;
    private final JWTService jwtService;

    @RequestMapping("cliente/listPage")
    public ResponseEntity<DataTableResults> listPage(HttpServletRequest request) {
        return ResponseEntity.ok(clienteS.list(request));

    }

    @GetMapping("cliente/findById/{clienteId}")
    public ResponseEntity<ClienteForm> findbyId(@PathVariable Long clienteId) {
        return ResponseEntity.ok(clienteS.findById(clienteId));
    }

    @PostMapping("cliente/save")
    public ResponseEntity<DataResponse> save(HttpServletRequest req,@RequestBody UserForm userForm) throws Exception {
        Integer empresaActiva = jwtService.getEmpresaActiva(req);
        userForm.getUser().setUsername("cli-"+ System.currentTimeMillis());
        userForm.getUser().setAlias(userForm.getCliente().getAlias());
        userForm.getUser().setEmail(userForm.getCliente().getEmail());
        userForm.getUser().setAvatar("avatarDefualt");
        userForm.getUser().setTypeSystemUser(TypeUserEnum.CLIENT.value);
        int roles[] = new int[]{5};
        userForm.setRoles(roles);
        int sucursal[] = new int[]{empresaActiva};
        userForm.setEmpresas(sucursal);
        return systemUserS.saveWizard(userForm);
    }

    @PatchMapping("cliente/update/{id}")
    public ResponseEntity<ClienteForm> update(@PathVariable Long id, @Valid @RequestBody ClienteForm value) throws Exception {
        ClienteForm obj = clienteS.update(id, value);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("cliente/delete/{id}")
    public ResponseEntity<ClienteEntity> delete(@PathVariable Long id) {
        ClienteEntity obj = clienteS.delete(id);
        return ResponseEntity.ok(obj);
    }
}
