package com.casavieja.business.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tipo_producto", schema = "business", catalog = "casa_vieja")
public class TipoProductoEntity {
    private Short id;
    private String nombre;
    private String descripcion;
    private Boolean status;

    @Id
    @Column(name = "id", nullable = false)
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre", nullable = false, length = 150)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "descripcion", nullable = false, length = 500)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoProductoEntity that = (TipoProductoEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, status);
    }
}
