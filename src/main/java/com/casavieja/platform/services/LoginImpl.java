package com.casavieja.platform.services;

import com.casavieja.platform.auth.JWTService;
import com.casavieja.platform.dao.*;
import com.casavieja.platform.data.DataRegister;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.*;
import com.casavieja.platform.enums.RolEnum;
import com.casavieja.platform.enums.TypeAccessEnum;
import com.casavieja.platform.enums.TypeOperationManagementEnum;
import com.casavieja.platform.enums.TypeUserEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.casavieja.utils.MyConstants.*;

/**
 * @author CARLOS
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LoginImpl implements LoginS {

    private static final String SIN_CARNET = "0";
    private static final String ADDRESS_DEFAULT = "Zona Gran Poder, Calle Leon de la Barra, entre Eloy Salmon";
    private static final String DESCRIPTION_DEFAULT = "Sucursal para desarrollo de pruebas.";

    private final PersonDao personDao;
    private final SystemUserDao systemUserDao;
    private final AccessKeyDao accessKeyDao;
    private final UserRolDao userRolDao;
    private final BranchOfficeDao branchOfficeDao;
    private final ManagementDao managementDao;
    private final UserManagementDao userManagementDao;
    private final JWTService jwtService;
    // Services
    private final UtilResponseS utilResponseS;
    private final PasswordEncoder passwordEncoder;

    public boolean isIdenticalValueAccess(DataRegister value) {
        if (value.getValueAccess() != null && value.getValueAccess2() != null) {
            if (value.getValueAccess().equals(value.getValueAccess2()))
                return true;
        }
        return false;
    }

    //Validacion
    //La clave y su repeticion no coinciden
    //El correo electronico ya existe, solicitar recuperacion de cuenta
    @Override
    @Transactional
    public ResponseEntity<DataResponse> saveRegisterUserSystem(DataRegister value) {
        try {
            //Validacion
            if (!isIdenticalValueAccess(value)) {
                throw new RuntimeException("Formulario invalido, revise los datos.");
            }
            Person personEntity = createPerson(value);
            if (personEntity == null) {
                throw new RuntimeException("No se logro guardar");
            }
            SystemUser systemUserEntity = createSystemUser(value, personEntity);
            if (systemUserEntity == null) {
                throw new RuntimeException("No se logro guardar");
            }
            BranchOffice branchOfficeEntity = createAutomaticBranchOffice(personEntity);
            if (branchOfficeEntity == null) {
                throw new RuntimeException("No se logro guardar");
            }
            Management managementEntity = createAutomaticManagement(branchOfficeEntity, personEntity.getId());
            if (managementEntity == null) {
                throw new RuntimeException("No se logro guardar");
            }
            UserManagement userManagementEntity = createAutomaticUserManagement(personEntity.getId(), managementEntity.getId());
            if (userManagementEntity == null) {
                throw new RuntimeException("No se logro guardar");
            }
            AccessKey accessKeyEntity = createAccessKey(value, systemUserEntity);
            if (accessKeyEntity == null) {
                throw new RuntimeException("No se logro guardar");
            }
            UserRol userRolEntity = createUserRol(systemUserEntity);
            if (userRolEntity == null) {
                throw new RuntimeException("No se logro guardar");
            }
            return new DataResponse<>("Se realizo con exito el registro").getResult(HttpStatus.OK);
        } catch (Exception e) {
            return utilResponseS.getExceptionService(e);
        }
    }

    private Person createPerson(DataRegister value) throws IllegalArgumentException {
        Person personEntity = new Person();
        personEntity.setName(value.getName());
        personEntity.setCi(SIN_CARNET);
        setLastnames(personEntity, value.getFirstLastname());
        try {
            return personDao.save(personEntity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al crear persona" + e.getMessage());
        }
    }

    private SystemUser createSystemUser(DataRegister value, Person personEntity) {
        SystemUser systemUserEntity = new SystemUser();
        systemUserEntity.setAvatar(AVATAR_DEFAULT);
        systemUserEntity.setEmail(value.getEmail());
        systemUserEntity.setTypeSystemUser(TypeUserEnum.CLIENT.value);
        systemUserEntity.generateAlias();
        systemUserEntity.setUsername(value.getEmail());
        systemUserEntity.setId(personEntity.getId());
        try {
            return systemUserDao.save(systemUserEntity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al crear usuario" + e.getMessage());
        }
    }

    private AccessKey createAccessKey(DataRegister value, SystemUser systemUserEntity) {
        AccessKey accessKeyEntity = new AccessKey();
        accessKeyEntity.setSystemUserId(systemUserEntity.getId());
        accessKeyEntity.setTypeAccess(TypeAccessEnum.USER_PASS.value);
        accessKeyEntity.setValueAccess(passwordEncoder.encode(value.getValueAccess()));
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");
        String dateRegister = format.format(new Date());
        accessKeyEntity.setCodeVerification(passwordEncoder.encode(dateRegister));
        accessKeyEntity.setIsVerifiedCode(false);
        try {
            return accessKeyDao.save(accessKeyEntity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al crear Llave de acceso: " + e.getMessage());
        }
    }

    private UserRol createUserRol(SystemUser systemUserEntity) {
        UserRol userRol = new UserRol();
        userRol.setRolId(RolEnum.CLIENTE_DEMO.value);
        userRol.setSystemUserId(systemUserEntity.getId());
        try {
            return userRolDao.save(userRol);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al crear al asignar rol al usuario: " + e.getMessage());
        }

    }

    private BranchOffice createAutomaticBranchOffice(Person person) throws IllegalArgumentException {
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setAddress(ADDRESS_DEFAULT);
        branchOffice.setCompanyId(COMPANY_DEMO_ID);
        branchOffice.setDescription(DESCRIPTION_DEFAULT);
        String nameBranchOffice = "Sucursal de " + person.getName() + " " + person.getFirstLastname() + " "
                + person.getSecondLastname();
        branchOffice.setName(nameBranchOffice.trim());
        branchOffice.setStatus(ACTIVE);
        try {
            return branchOfficeDao.save(branchOffice);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al crear sucursal: " + e.getMessage());
        }
    }

    private Management createAutomaticManagement(BranchOffice branchOffice, Long personId) {
        Management management = new Management();
        management.setBranchOfficeId(branchOffice.getId());
        management.setStartDate(new Date());
        management.setYearNumber((short) LocalDate.now().getYear());
        management.setStatus(ACTIVE);
        try {
            return managementDao.save(management);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al crear gestion para la sucursal: " + e.getMessage());
        }

    }

    @Transactional
    public UserManagement createAutomaticUserManagement(Long systemUserId, Integer managementId)
            throws IllegalArgumentException {
        UserManagement userManagement = new UserManagement();
        userManagement.setSystemUserId(systemUserId);
        userManagement.setManagementId(managementId);
        userManagement.setStatus(ACTIVE);
        userManagement.setTypeOperation(TypeOperationManagementEnum.READ_WRITE.value);
        try {
            return userManagementDao.save(userManagement);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al crear relacion usuario - gestion " + e.getMessage());
        }
    }

    private void setLastnames(Person personEntity, String lastnames) {
        lastnames = lastnames.trim();
        if (lastnames != null && lastnames.length() > 0) {
            int indexSpace = lastnames.indexOf(" ");
            if (indexSpace > -1) {
                personEntity.setFirstLastname(lastnames.substring(0, indexSpace));
                personEntity.setSecondLastname(lastnames.substring(indexSpace + 1));
            } else {
                personEntity.setFirstLastname(lastnames);
                personEntity.setSecondLastname("");
            }
        }
    }

    @Override
    public ResponseEntity<DataResponse> refreshtoken(HttpServletRequest request) throws Exception {
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
        if(claims == null || (claims!= null && claims.isEmpty())) {
            try {
                Claims claimActive = jwtService.getClaims(request);
                if(claimActive != null) {
                    Map<String, Object> expectedActive = getMapFromIoJsonwebtokenClaims(claimActive);
                    String tokenNew = jwtService.generateRefreshToken(expectedActive, claimActive.getSubject());
                    return new DataResponse(tokenNew, "TOKEN_ACTIVO_REFRESCADO").getResult(HttpStatus.OK);
                } else {
                    return new DataResponse("TOKEN_INVALIDO").getResult(HttpStatus.BAD_REQUEST);
                }
            } catch (SignatureException ex) {
                return new DataResponse("FIRMA_TOKEN_INVALIDO").getResult(HttpStatus.BAD_REQUEST);
            }
        }
        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtService.generateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        return new DataResponse(token, "TOKEN_EXPIRADO_REFRESCADO").getResult(HttpStatus.OK);
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(Claims claims) {
        Map<String, Object> expectedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }
}