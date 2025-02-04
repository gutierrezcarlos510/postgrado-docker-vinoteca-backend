package com.casavieja.business.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "categoria_producto", schema = "business", catalog = "casa_vieja")
public class CategoriaProductoEntity {
    private Short id;
    private String nombre;
    private String descripcion;
    private Boolean status;
    private Short tipoProductoId;

    @Id
    @GeneratedValue(generator = "business.categoria_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.categoria_id_seq", sequenceName = "business.categoria_id_seq", allocationSize = 1)
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

    public void setStatus(Boolean estado) {
        this.status = estado;
    }

    @Basic
    @Column(name = "tipo_producto_id", nullable = false)
    public Short getTipoProductoId() {
        return tipoProductoId;
    }

    public void setTipoProductoId(Short tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaProductoEntity that = (CategoriaProductoEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(status, that.status) && Objects.equals(tipoProductoId, that.tipoProductoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, status, tipoProductoId);
    }

    @PrePersist
    public void prePersist() {
        status = true;
    }
}
