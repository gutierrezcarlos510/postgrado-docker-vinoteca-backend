/**
 *
 */
package com.casavieja.platform.services;

import com.casavieja.business.dao.ClienteDao;
import com.casavieja.business.entities.ClienteEntity;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import com.casavieja.platform.auth.DataToken;
import com.casavieja.platform.dao.*;
import com.casavieja.platform.data.DataAvatar;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.data.FileInput;
import com.casavieja.platform.models.PasswordChangeRequestM;
import com.casavieja.platform.entities.*;
import com.casavieja.platform.enums.TypeAccessEnum;
import com.casavieja.platform.mappers.UserFormMapper;
import com.casavieja.platform.models.SystemUserM;
import com.casavieja.platform.models.form.UserForm;
import com.casavieja.platform.models.form.UserMain;
import com.casavieja.utils.MyConstants;
import com.casavieja.utils.UploadFileS;
import com.casavieja.utils.UtilDataTableS;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CARLOS
 * 	jp10ch10f
 */
@RequiredArgsConstructor
@Service
public class SystemUserImpl implements SystemUserS {

    private static final int AVATARS_DEFAULT = 16;
    private final SystemUserDao systemUserDao;
    private final UploadFileS uploadFileS;
    private final UtilDataTableS utilDataTableS;
    private final PersonDao personDao;
    private final RolDao rolDao;
    private final ManagementDao managementDao;
    private final CompanyDao companyDao;
    private final BranchOfficeDao branchOfficeDao;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AccessKeyDao accessKeyDao;
    private final ClienteDao clienteDao;

    private static final int ROL_PUBLICO = 8;
    private static final int ROL_CLIENTE = 7;

    @Override
    @Transactional(readOnly = true)
    public SystemUser findByEmail(String value) throws DataAccessException {
        SystemUser systemUser = systemUserDao.findByEmailAndStatusTrue(value);
        return systemUser;
    }

    @Override
    @Transactional(readOnly = true)
    public SystemUser findByUsername(String value) throws DataAccessException {
        SystemUser systemUser = systemUserDao.findByUsernameAndStatusTrue(value);
        return systemUser;
    }

    @Override
    public Boolean encontrarusuario(String value){
       return systemUserDao.existsByUsername(value);

    }
    @Transactional
    public ResponseEntity<DataResponse> update(SystemUser value) throws Exception {
        SystemUser entity = systemUserDao.findById(value.getId()).orElse(null);
        if (entity == null) {
            return new DataResponse<>("No se encontro al usuario")
                    .getResult(HttpStatus.BAD_REQUEST);
        }
        entity.setAlias(value.getAlias());
        entity.setAvatar(value.getAvatar());
        entity.setEmail(value.getEmail());
        entity.setTypeSystemUser(value.getTypeSystemUser());
        entity.setUsername(value.getUsername());
        systemUserDao.save(entity);
        return new DataResponse<>("Actualizacion exitosa")
                .getResult(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<DataResponse> changeAvatar(FileInput value, SystemUser user) throws Exception {
        SystemUser userEntity = systemUserDao.findById(user.getId()).orElse(null);
        if (userEntity != null) {
            if (isAvatarExternal(userEntity.getAvatar())) {//Avatar interno no se puede eliminar
                uploadFileS.delete(MyConstants.FILE_USER, userEntity.getAvatar());
            }
            String newAvatar = generateNameAvatar(userEntity) + "." + value.getExtension();
            uploadFileS.save(MyConstants.FILE_USER, null, newAvatar);
            userEntity.setAvatar(newAvatar);
            systemUserDao.save(userEntity);
            return new DataResponse<>("Se logro cambiar el avatar")
                    .getResult(HttpStatus.OK);
        } else {
            return new DataResponse<>("No se encontró al usuario")
                    .getResult(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<DataResponse> changeAvatarInternal(DataAvatar value) throws Exception {
        SystemUser userEntity = systemUserDao.findById(value.getSystemUserId()).orElse(null);
        if (userEntity != null) {
            if (isAvatarExternal(userEntity.getAvatar())) {//Avatar interno no se puede eliminar
                uploadFileS.delete(MyConstants.FILE_USER, userEntity.getAvatar());
            }
            String newAvatar = "avatar_" + value.getAvatarId() + ".png";
            userEntity.setAvatar(newAvatar);
            systemUserDao.save(userEntity);
            return new DataResponse<>("Se logro cambiar el avatar")
                    .getResult(HttpStatus.OK);
        } else {
            return new DataResponse<>("No se encontro al usuario")
                    .getResult(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<DataResponse> deleteAvatar(SystemUser user) throws Exception {
        SystemUser userEntity = systemUserDao.findById(user.getId()).orElse(null);
        if (userEntity != null) {
            if (isAvatarExternal(userEntity.getAvatar())) {
                uploadFileS.delete(MyConstants.FILE_USER, userEntity.getAvatar());
                userEntity.setAvatar(MyConstants.AVATAR_DEFAULT);
                systemUserDao.save(userEntity);
                return new DataResponse<>("Se elimino su avatar, correctamente")
                        .getResult(HttpStatus.OK);
            } else {
                return new DataResponse<>("El avatar actual esta por defecto")
                        .getResult(HttpStatus.OK);
            }
        } else {
            return new DataResponse<>("No se encontro al usuario")
                    .getResult(HttpStatus.BAD_REQUEST);
        }
    }

    public String generateNameAvatar(SystemUser user) {
        String nameAvatar = "user_" + user.getId();
        return nameAvatar;
    }

    private boolean isAvatarExternal(String nameAvatar) throws Exception {
        for (int i = 0; i <= AVATARS_DEFAULT; i++) {
            if (nameAvatar.equals("avatar_" + i + ".png"))
                return false;
        }
        return true;
    }

    @Transactional(readOnly = true)
    public DataTableResults list(HttpServletRequest request) throws Exception {
        SqlBuilder sql = new SqlBuilder();
        sql.setSelect("id,alias,avatar,email,status,type_system_user,username");
        sql.setFrom("systems_users");
        sql.setWhere("status=true");
        DataTableResults<SystemUserM> result = utilDataTableS.list(request, SystemUserM.class, sql);
        return result;
    }

    @Transactional
    public ResponseEntity<DataResponse> save(SystemUser value) throws Exception {
        value.setStatus(MyConstants.ACTIVE);
        systemUserDao.save(value);
        return new DataResponse<>("Registro exitoso")
                .getResult(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<DataResponse> saveWizard(UserForm value) throws Exception {
        UserFormMapper userMapper = new UserFormMapper();
        Person personEntityForm = userMapper.toEntityPerson(value);
        boolean isUpdate = false;
        if(value.getPerson().getPersonId() != null) {//Si ya tiene Id, significa que es actualizar
            isUpdate = true;
        }
        Person personResponse = personDao.save(personEntityForm);
        if (personResponse != null) {
            value.getUser().setSystemUserId(personResponse.getId());
            SystemUser userEntityForm = userMapper.toEntityUser(value);
            SystemUser userResponse = systemUserDao.save(userEntityForm);
            if (userResponse != null) {
                if(!isUpdate) {//Si es un nuevo usuario, se cread datos de acceso por defecto
                    AccessKey accessKey = new AccessKey();
                    accessKey.setSystemUserId(userResponse.getId());
                    accessKey.setTypeAccess(TypeAccessEnum.USER_PASS.value);
                    accessKey.setStatus(true);
                    accessKey.setIsVerifiedCode(true);
                    accessKey.setValueAccess(passwordEncoder.encode(MyConstants.PASS_DEFAULT));
                    accessKeyDao.save(accessKey);
                }
                if(value.getCliente() != null) {
                    ClienteEntity cliente = new ClienteEntity();
                    cliente.setId(personResponse.getId());
                    cliente.setAlias(value.getCliente().getAlias());
                    cliente.setDireccion(value.getCliente().getDireccion());
                    cliente.setEmail(value.getCliente().getEmail());
                    cliente.setNombreNegocio(value.getCliente().getNombreNegocio());
                    cliente.setDescripcionNegocio(value.getCliente().getDescripcionNegocio());
                    cliente.setTipoCliente(value.getCliente().getTipoCliente());
                    cliente.setBarrioId(value.getCliente().getBarrioId());
                    clienteDao.save(cliente);
                }
                //Eliminamos todas las asignaciones y volvemos a asignar, en el caso de no tener se lo deja sin asignacion
                rolDao.eliminarAsignacionRolPorUsuario(personResponse.getId());
                if (value.getRoles() != null && value.getRoles().length > 0) {
                    for (int i = 0; i < value.getRoles().length; i++) {
                        rolDao.assignUserRol(userResponse.getId(), value.getRoles()[i]);
                    }
                } else {
                    throw new RuntimeException("No se encontro datos para registrar los roles");
                }
                if (value.getEmpresas() != null && value.getEmpresas().length > 0) {
                    List<Company> empresasRegistradas = companyDao.findBySystemUser(personResponse.getId());
                    for (int i = 0; i < value.getEmpresas().length; i++) {
                        int companyId = value.getEmpresas()[i];
                        Company existeAsignacion = empresasRegistradas.stream().filter(it -> it.getId().intValue() == companyId).findAny().orElse(null);
                        if (existeAsignacion == null) {
                            List<Management> managementList = managementDao.findByCompany(value.getEmpresas()[i]);
                            if (managementList != null && !managementList.isEmpty()) {
                                for (Management management : managementList) {
                                    companyDao.addUserManagement(management.getId(), personResponse.getId());
                                }
                            }
                        }
                    }
                } else {
                    throw new RuntimeException("No se encontro datos para la asignacion de empresas");
                }
                UserForm response = userMapper.fromEntity(personResponse, userResponse, null, null);
                response.setRoles(value.getRoles());
                response.setEmpresas(value.getEmpresas());

                return new DataResponse(response, "Se realizo con exito el registro de usuario.").getResult(HttpStatus.OK);
            } else {
                throw new RuntimeException("Error al registrar Usuario");
            }
        } else {
            throw new RuntimeException("Error al registrar Persona");
        }
    }

    @Override
    public ResponseEntity obtenerPorUsuarioWizard(Long systemUserId) throws Exception {
        Person personEntity = personDao.findById(systemUserId).get();
        SystemUser userEntity = systemUserDao.findById(systemUserId).get();
        List<Rol> roles = rolDao.findBySystemUser(systemUserId);
        List<Company> empresas = companyDao.findBySystemUser(systemUserId);
        UserFormMapper mapper = new UserFormMapper();
        List<UserRol> userRolList = new ArrayList<>();
        if (roles != null && !roles.isEmpty()) {
            for (Rol rol : roles) {
                UserRol obj = new UserRol();
                obj.setRolId(rol.getId());
                userRolList.add(obj);
            }
        }

        UserForm userForm = mapper.fromEntity(personEntity, userEntity, userRolList, empresas);
        return new DataResponse(userForm, "Se realizo con exito").getResult(HttpStatus.OK);
    }

    public ResponseEntity<UserMain> obtenerUsuarioParaPrincipal(Long systemUserId) throws Exception {
        Person personEntity = personDao.findById(systemUserId).get();
        SystemUser userEntity = systemUserDao.findById(systemUserId).get();
        List<Rol> rolList = rolDao.findBySystemUser(systemUserId);
        UserMain userMain = new UserMain();
        userMain.setPersona(personEntity);
        userMain.setUsuario(userEntity);
        userMain.setRoles(rolList);
        userMain.setRolActivo(rolList.get(0));
        //Si es primera vez, que se inicia sesio, se activa por defecto la primera empresa y sucursal
        if(rolList.get(0).getId() != ROL_PUBLICO && rolList.get(0).getId() != ROL_CLIENTE) {
            List<Company> empresas = companyDao.findBySystemUser(systemUserId);
            for (Company empresa : empresas) {
                empresa.setSucursalList(branchOfficeDao.findByCompanyIdAndStatusTrue(empresa.getId()));
            }
            userMain.setEmpresas(empresas);
            userMain.setEmpresaActiva(empresas.get(0));
            userMain.setSucursalActiva(empresas.get(0).getSucursalList().get(0));
        }


        return new DataResponse(userMain, "").getResult(HttpStatus.OK);
    }

    @Override
    public DataToken obtenerParaToken(String username) throws Exception {
        DataToken dataToken = new DataToken();
        SystemUser systemUser = systemUserDao.findByEmailOrUsernameOrCelularAndStatusTrue(username, username, username).get(0);
        List<Rol> rolList = rolDao.findBySystemUser(systemUser.getId());
        if(rolList.get(0).getId() != ROL_PUBLICO && rolList.get(0).getId() != ROL_CLIENTE) {
            List<Company> empresas = companyDao.findBySystemUser(systemUser.getId());
            for (Company empresa : empresas) {
                empresa.setSucursalList(branchOfficeDao.findByCompanyIdAndStatusTrue(empresa.getId()));
            }
            dataToken.setEmpresaActiva(empresas.get(0).getId());
            dataToken.setSucursalActiva(empresas.get(0).getSucursalList().get(0).getId());
        }
        dataToken.setUserId(systemUser.getId());
        dataToken.setRolActivo(rolList.get(0).getId());

        return dataToken;
    }

    @Transactional
    public ResponseEntity<DataResponse> delete(SystemUser value) throws Exception {
        systemUserDao.deleteLogic(value.getId());
        return new DataResponse<>("Eliminacion exitosa")
                .getResult(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<DataResponse> saveAvatar(SystemUser value) throws Exception {
        SystemUser userEntity = systemUserDao.findById(value.getId()).orElse(null);
        userEntity.setAvatar(value.getAvatar());
        systemUserDao.save(userEntity);
        return new DataResponse<>("Registro exitoso")
                .getResult(HttpStatus.OK);
    }
    @Override
    public List<Person> listarUsuariosSistema(){
        return personDao.listarUsuariosSistema();
    }

    //encuentra al usuario autenticado
    private SystemUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Nombre de usuario autenticado
        return systemUserDao.findByUsername(username); // Buscar usuario en la BD
    }
    @Transactional
    @Override
    public ResponseEntity<DataResponse> changePassword(PasswordChangeRequestM passwordChangeRequest) throws Exception {
        SystemUser currentUser = getCurrentUser(); // Obtener el usuario autenticado

        // Obtener la clave de acceso correspondiente al usuario
        List<AccessKey> accessKeys = accessKeyDao.findBySystemUserIdAndStatusTrue(currentUser.getId());

        // Asegurarse de que hay al menos una clave de acceso válida
        if (accessKeys.isEmpty()) {
            return new DataResponse<>("No se encontró ninguna clave de acceso válida para el usuario.")
                    .getResult(HttpStatus.BAD_REQUEST);
        }

        // Por el momento asumimos que solo hay una clave de acceso válida para el usuario
        AccessKey currentAccessKey = accessKeys.get(0);

        // Validar la contraseña actual
        if (!passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), currentAccessKey.getValueAccess())) {
            return new DataResponse<>("La contraseña actual es incorrecta.")
                    .getResult(HttpStatus.BAD_REQUEST);
        }

        // Validar que la nueva contraseña y la confirmación coincidan
        if (!passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getConfirmPassword())) {
            return new DataResponse<>("La nueva contraseña y la confirmación no coinciden.")
                    .getResult(HttpStatus.BAD_REQUEST);
        }

        // Validar que la nueva contraseña no sea la misma que la actual
        if (passwordEncoder.matches(passwordChangeRequest.getNewPassword(), currentAccessKey.getValueAccess())) {
            return new DataResponse<>("La nueva contraseña no puede ser la misma que la actual.")
                    .getResult(HttpStatus.BAD_REQUEST);
        }

        // Actualizar la contraseña
        currentAccessKey.setValueAccess(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        accessKeyDao.save(currentAccessKey);

        return new DataResponse<>("La contraseña se ha cambiado exitosamente.")
                .getResult(HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<DataResponse> resetPassword(Long systemUserId){
        AccessKey accessKey = accessKeyDao.findBySystemUserIdAndTypeAccessAndStatusTrue(systemUserId, TypeAccessEnum.USER_PASS.value);
        if (accessKey != null) {
            accessKey.setValueAccess(passwordEncoder.encode(MyConstants.PASS_DEFAULT));
            accessKeyDao.save(accessKey);
            return new DataResponse<>("La contraseña se ha cambiado exitosamente.")
                    .getResult(HttpStatus.OK);
        } else {
            throw new RuntimeException("No se encontró una clave de acceso válida para el usuario");
        }

    }
}

