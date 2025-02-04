/**
 *
 */
package com.casavieja.platform.rest;

import com.casavieja.pagination.DataTableResults;
import com.casavieja.platform.data.DataAvatar;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.data.FileInput;
import com.casavieja.platform.models.PasswordChangeRequestM;
import com.casavieja.platform.entities.Person;
import com.casavieja.platform.entities.SystemUser;
import com.casavieja.platform.models.form.RegistroPublicoForm;
import com.casavieja.platform.models.form.UserForm;
import com.casavieja.platform.services.AccessKeyS;
import com.casavieja.platform.services.CodeVerifyS;
import com.casavieja.platform.services.SystemUserS;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * @author CARLOS
 *
 */
@Tag(name = "Usuarios del sistema", description = "Gestiona los diferentes usuarios del sistema")
@RequiredArgsConstructor
@RestController
@RequestMapping("systemUser/*")
public class SystemUserC {
    private final SystemUserS systemUserS;
    private final AccessKeyS accessKeyS;
    private final CodeVerifyS codeVerifyS;

    @PostMapping("changeAvatar")
    public ResponseEntity<DataResponse> changeAvatar(MultipartFile valueFile, SystemUser user) throws Exception {
        FileInput dataFileInput = new FileInput();
        dataFileInput.setFileBlob(valueFile);
        return systemUserS.changeAvatar(dataFileInput, user);
    }

    @PostMapping("changeAvatarInternal")
    public ResponseEntity<DataResponse> changeAvatarInternal(DataAvatar value) throws Exception {
        return systemUserS.changeAvatarInternal(value);
    }

    @PostMapping("deleteAvatar")
    public ResponseEntity<DataResponse> deleteAvatar(@RequestBody SystemUser user) throws Exception {
        return systemUserS.deleteAvatar(user);
    }

    @PostMapping("save")
    public ResponseEntity<DataResponse> save(@RequestBody SystemUser value) throws Exception {
        return systemUserS.save(value);
    }

    @PostMapping("saveWizard")
    public ResponseEntity<DataResponse> saveWizard(@Valid @RequestBody UserForm userForm) throws Exception {
        return systemUserS.saveWizard(userForm);
    }

    @PostMapping("saveAvatar")
    public ResponseEntity<DataResponse> saveAvatar(@RequestBody SystemUser value) throws Exception {
        return systemUserS.saveAvatar(value);
    }

    @PostMapping("generatePass")
    public ResponseEntity<DataResponse> generatePass(@RequestParam Long systemUserId) throws Exception {
        return accessKeyS.generatePass(systemUserId);
    }

    @PostMapping("delete")
    public ResponseEntity<DataResponse> delete(@RequestBody SystemUser value) throws Exception {
        return systemUserS.delete(value);
    }

    @RequestMapping("listPage")
    public ResponseEntity<DataTableResults> listPage(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(systemUserS.list(request));
    }

    @RequestMapping("findUserWizard/{id}")
    public ResponseEntity<DataResponse> findUserWizard(@PathVariable(name = "id") Long systemUserId) throws Exception {
        return systemUserS.obtenerPorUsuarioWizard(systemUserId);
    }

    @PostMapping("findByEmail")
    public ResponseEntity<DataResponse> findByEmail(@RequestBody SystemUser systemUser) throws DataAccessException {
        SystemUser resp = systemUserS.findByEmail(systemUser.getEmail());
        return new DataResponse<>(resp, "").getResult(HttpStatus.OK);
    }

    @PostMapping("findByUsername")
    public ResponseEntity<DataResponse> findByUsername(@RequestBody SystemUser systemUser) throws DataAccessException {
        SystemUser resp = systemUserS.findByUsername(systemUser.getUsername());
        return new DataResponse<>(resp, "").getResult(HttpStatus.OK);
    }

    @GetMapping("encontrarUsuario/{username}")
    public Boolean findByUsername(@PathVariable String username) throws DataAccessException {
   return  systemUserS.encontrarusuario(username);

    }

    @PostMapping("enviarCodigoVerificacion")
    public ResponseEntity<DataResponse> enviarCodigoVerificacion(@RequestBody RegistroPublicoForm cellObj) throws DataAccessException {
        return codeVerifyS.generarCodigo(cellObj.getCodigoCelular() + cellObj.getCelular());
    }

    @GetMapping("listarUsuariosSistema")
    public ResponseEntity<List<Person>> listarUsuariosSistema() throws DataAccessException {
        List<Person> resp = systemUserS.listarUsuariosSistema();
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<DataResponse> changePassword(@Valid @RequestBody PasswordChangeRequestM passwordChangeRequest, BindingResult result) {
        if (result.hasErrors()) {
            return new DataResponse<>(Objects.requireNonNull(result.getFieldError()).getDefaultMessage())
                    .getResult(HttpStatus.BAD_REQUEST);
        }

        try {
            systemUserS.changePassword(passwordChangeRequest);
            return new DataResponse<>("Contraseña cambiada con éxito")
                    .getResult(HttpStatus.OK);
        } catch (Exception e) {
            return new DataResponse<>(e.getMessage())
                    .getResult(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/resetPassword/{id}")
    public ResponseEntity<DataResponse> resetPassword(@PathVariable Long id){
        try {
        systemUserS.resetPassword(id);
            return new DataResponse<>("Se restablecio la contraseña").getResult(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new DataResponse<>(e.getMessage()).getResult(HttpStatus.NOT_FOUND);
        }
    }
}
