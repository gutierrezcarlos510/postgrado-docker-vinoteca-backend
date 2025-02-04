package com.casavieja.platform.models.form;

import com.casavieja.platform.entities.Company;
import com.casavieja.platform.entities.Person;
import com.casavieja.platform.entities.Rol;
import com.casavieja.platform.entities.BranchOffice;
import com.casavieja.platform.entities.SystemUser;

import java.util.List;

public class UserMain {
    private Person persona;
    private SystemUser usuario;
    private List<Company> empresas;
    private List<Rol> roles;
    private Company empresaActiva;
    private BranchOffice sucursalActiva;
    private Rol rolActivo;

    public Company getEmpresaActiva() {
        return empresaActiva;
    }

    public void setEmpresaActiva(Company empresaActiva) {
        this.empresaActiva = empresaActiva;
    }

    public BranchOffice getSucursalActiva() {
        return sucursalActiva;
    }

    public void setSucursalActiva(BranchOffice sucursalActiva) {
        this.sucursalActiva = sucursalActiva;
    }

    public Rol getRolActivo() {
        return rolActivo;
    }

    public void setRolActivo(Rol rolActivo) {
        this.rolActivo = rolActivo;
    }

    public Person getPersona() {
        return persona;
    }

    public void setPersona(Person persona) {
        this.persona = persona;
    }

    public SystemUser getUsuario() {
        return usuario;
    }

    public void setUsuario(SystemUser usuario) {
        this.usuario = usuario;
    }

    public List<Company> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Company> empresas) {
        this.empresas = empresas;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
