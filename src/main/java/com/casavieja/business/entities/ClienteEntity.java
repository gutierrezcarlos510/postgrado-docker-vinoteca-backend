package com.casavieja.business.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cliente", schema = "business", catalog = "casa_vieja")
public class ClienteEntity {
    private long id;
    private String alias;
    private String direccion;
    private String email;
    private String nombreNegocio;
    private String descripcionNegocio;
    private short tipoCliente;
    private short barrioId;
    private boolean status;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "direccion")
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "nombre_negocio")
    public String getNombreNegocio() {
        return nombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        this.nombreNegocio = nombreNegocio;
    }

    @Basic
    @Column(name = "descripcion_negocio")
    public String getDescripcionNegocio() {
        return descripcionNegocio;
    }

    public void setDescripcionNegocio(String descripcionNegocio) {
        this.descripcionNegocio = descripcionNegocio;
    }

    @Basic
    @Column(name = "tipo_cliente")
    public short getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(short tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    @Basic
    @Column(name = "barrio_id")
    public short getBarrioId() {
        return barrioId;
    }

    public void setBarrioId(short barrioId) {
        this.barrioId = barrioId;
    }

    @Basic
    @Column(name = "status")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteEntity that = (ClienteEntity) o;
        return id == that.id && tipoCliente == that.tipoCliente && barrioId == that.barrioId && status == that.status && Objects.equals(alias, that.alias) && Objects.equals(direccion, that.direccion) && Objects.equals(email, that.email) && Objects.equals(nombreNegocio, that.nombreNegocio) && Objects.equals(descripcionNegocio, that.descripcionNegocio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, alias, direccion, email, nombreNegocio, descripcionNegocio, tipoCliente, barrioId, status);
    }

    @PrePersist
    public void prePersist() {
        status = true;
    }
}
