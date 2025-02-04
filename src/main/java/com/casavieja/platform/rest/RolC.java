/**
 *
 */
package com.casavieja.platform.rest;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.data.RolAssignForm;
import com.casavieja.platform.entities.Menu;
import com.casavieja.platform.entities.Rol;
import com.casavieja.platform.models.RolAccesoM;
import com.casavieja.platform.services.RolS;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Roles", description = "Gestiona los roles del sistema")
@RequiredArgsConstructor
@RestController
@RequestMapping("/rol/*")
public class RolC {

    private final RolS rolS;

    @Operation(
            summary = "Listar Roles Activos",
            description = "Devuelve todos los roles activos dentro del sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation =  Rol.class)))})
    })
    @GetMapping("list")
    public ResponseEntity<List<Rol>> listAllActive() {
        return rolS.listActive();
    }
    @Operation(
            summary = "Guardar o actualizar rol",
            description = "Guarda o actualiza los datos de un rol"
    )
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseEntity<Rol> save(@Valid @RequestBody Rol value) {
        return rolS.save(value);
    }
    @Operation(
            summary = "Elimina un rol",
            description = "Realiza la eliminacion logica de un rol, enviando solo el id"
    )
    @PostMapping("delete")
    public ResponseEntity delete(@RequestBody Rol value) {
        return rolS.delete(value);
    }
    @Operation(
            summary = "Asignar menus a un rol",
            description = "Asigna una lista de menus a un rol"
    )
    @PostMapping("saveAssign")
    public ResponseEntity<DataResponse> saveAssign(@RequestBody RolAssignForm value) {
        return rolS.saveAssign(value.getRolId(), value.getMenuList());
    }
    @Operation(
            summary = "Asigna tareas a un rol",
            description = "Asigna una lista de tareas a un rol"
    )
    @PostMapping("saveAssignTask")
    public ResponseEntity<DataResponse> saveAssignTask(@RequestBody Rol value, @RequestParam(defaultValue = "") List<Integer> vecTask) {
        return rolS.saveAssignTask(value, vecTask);
    }
    @Operation(
            summary = "Obtiene roles por usuario",
            description = "Obtiene una lista de roles asignados a un usuario"
    )
    @GetMapping("findBySystemUser/{value}")
    public ResponseEntity<List<Rol>> findBySystemUser(@PathVariable Long value) {
        return rolS.findBySystemUser(value);
    }
    @Operation(
            summary = "Lista Paginador Rol",
            description = "Listas los roles segun paginador del sistema"
    )
    @RequestMapping("listPage")
    public ResponseEntity<DataTableResults> listPage(HttpServletRequest request) {
        return ResponseEntity.ok(rolS.list(request));
    }
    @Operation(
            summary = "Listar Menus y submenus de un rol",
            description = "Lista los menus y submenus anidados con relacion respectiva entre submenu y rol"
    )
    @GetMapping("listMenuByRol/{rolId}")
    public @ResponseBody ResponseEntity<List<Menu>> listarMenuPorRol(@PathVariable Integer rolId) {
        return rolS.listarMenuPorRol(rolId);
    }
    @Operation(
            summary = "Asignar rol - acceso submenu",
            description = "Asigna la relacion de rol, menu y submenu en sistema"
    )
    @PostMapping("saveAssignRolAcceso")
    public ResponseEntity<DataResponse> saveAssignRolAcceso(@RequestBody RolAccesoM obj) {
        return rolS.saveRolAcceso(obj);
    }
}
