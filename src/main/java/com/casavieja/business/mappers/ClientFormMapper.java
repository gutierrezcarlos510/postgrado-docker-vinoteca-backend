package com.casavieja.business.mappers;

import com.casavieja.business.dto.form.ClienteForm;
import com.casavieja.business.entities.ClienteEntity;
import com.casavieja.platform.entities.Person;

public class ClientFormMapper {
//    public ClienteEntity toEntityCliente(FormCliente req) {
//        ClienteEntity resp = new ClienteEntity();
//        resp.setId(req.getId());
//        resp.setAlias(req.getAlias());
//        resp.setDireccion(req.getDireccion());
//        resp.setEmail(req.getEmail());
//        resp.setNombreNegocio(req.getNombreNegocio());
//        resp.setDescripcionNegocio(req.getDescripcionNegocio());
//        resp.setTipoCliente(req.getTipoCliente());
//        resp.setBarrioId(req.getBarrioId());
//        return resp;
//    }
//
//    public Person toEntityPerson(FormCliente req) {
//        Person resp = new Person();
//        resp.setId(req.getId());
//        resp.setName(req.getName());
//        resp.setFirstLastname(req.getFirstLastname());
//        resp.setGender(req.getGender());
//        resp.setNumeroCelular(req.getNumeroCelular());
//        return resp;
//    }
    public ClienteForm fromEntity(Person reqPerson, ClienteEntity reqCliente){
        ClienteForm userForm = new ClienteForm();
        userForm.setId(reqCliente.getId());
        userForm.setAlias(reqCliente.getAlias());
        userForm.setDireccion(reqCliente.getDireccion());
        userForm.setEmail(reqCliente.getEmail());
        userForm.setNombreNegocio(reqCliente.getNombreNegocio());
        userForm.setDescripcionNegocio(reqCliente.getDescripcionNegocio());
        userForm.setTipoCliente(reqCliente.getTipoCliente());
        userForm.setBarrioId(reqCliente.getBarrioId());
        userForm.setName(reqPerson.getName());
        userForm.setFirstLastname(reqPerson.getFirstLastname());
        userForm.setNumeroCelular(reqPerson.getNumeroCelular());
        userForm.setGender(reqPerson.getGender());
        return userForm;
    }
}
