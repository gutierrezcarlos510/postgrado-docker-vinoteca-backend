package com.casavieja.platform.mappers;

import com.casavieja.platform.entities.Company;
import com.casavieja.platform.entities.Person;
import com.casavieja.platform.entities.SystemUser;
import com.casavieja.platform.entities.UserRol;
import com.casavieja.platform.models.form.UserForm;

import java.io.IOException;
import java.util.List;

public class UserFormMapper {
    public SystemUser toEntityUser(UserForm req) throws IOException {
        SystemUser resp = new SystemUser();
        resp.setId(req.getUser().getSystemUserId());
        resp.setAvatar(req.getUser().getAvatar());
        resp.setAlias(req.getUser().getAlias());
        resp.setEmail(req.getUser().getEmail());
        resp.setUsername(req.getUser().getUsername());
        resp.setTypeSystemUser(req.getUser().getTypeSystemUser());
        resp.setStatus(req.getUser().isStatus());
        return resp;
    }

    public Person toEntityPerson(UserForm req) throws IOException {
        Person resp = new Person();
        resp.setCi(req.getPerson().getCi());
        resp.setId(req.getPerson().getPersonId());
        resp.setName(req.getPerson().getName());
        resp.setFirstLastname(req.getPerson().getFirstLastname());
        resp.setSecondLastname(req.getPerson().getSecondLastname());
        resp.setGender(req.getPerson().getGender());
        resp.setNumeroCelular(req.getPerson().getNumeroCelular());
        resp.setStatus(req.getPerson().isStatus());
        return resp;
    }
    public UserForm fromEntity(Person reqPerson, SystemUser reqUser, List<UserRol> reqUserRol, List<Company> companyList){
        UserForm userForm = new UserForm();
        userForm.getPerson().setCi(reqPerson.getCi());
        userForm.getPerson().setPersonId(reqPerson.getId());
        userForm.getPerson().setName(reqPerson.getName());
        userForm.getPerson().setFirstLastname(reqPerson.getFirstLastname());
        userForm.getPerson().setSecondLastname(reqPerson.getSecondLastname());
        userForm.getPerson().setGender(reqPerson.getGender());
        userForm.getPerson().setStatus(reqPerson.getStatus());

        userForm.getUser().setSystemUserId(reqUser.getId());
        userForm.getUser().setAlias(reqUser.getAlias());
        userForm.getUser().setUsername(reqUser.getUsername());
        userForm.getUser().setAvatar(reqUser.getAvatar());
        userForm.getUser().setEmail(reqUser.getEmail());
        userForm.getUser().setTypeSystemUser(reqUser.getTypeSystemUser());
        userForm.getUser().setStatus(reqUser.getStatus());

        if(reqUserRol != null && !reqUserRol.isEmpty()) {
            int roles[]= reqUserRol.stream().mapToInt(it->it.getRolId()).toArray();
            userForm.setRoles(roles);
        }
        if(companyList != null && !companyList.isEmpty()) {
            int empresas[] = companyList.stream().mapToInt(it->it.getId()).toArray();
            userForm.setEmpresas(empresas);
        }
        return userForm;
    }
}
