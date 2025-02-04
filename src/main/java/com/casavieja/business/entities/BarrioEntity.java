package com.casavieja.business.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "barrio", schema = "business", catalog = "casa_vieja")
public class BarrioEntity {
    private Short id;
    private String nombre;
    private Boolean status;
    private Short zonaId;

    @Id
    @GeneratedValue(generator = "business.barrio_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.barrio_id_seq", sequenceName = "business.barrio_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre", nullable = false, length = 100)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    @Column(name = "zona_id", nullable = false)
    public Short getZonaId() {
        return zonaId;
    }

    public void setZonaId(Short zonaId) {
        this.zonaId = zonaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarrioEntity that = (BarrioEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(status, that.status) && Objects.equals(zonaId, that.zonaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, status, zonaId);
    }

    @PrePersist
    public void prePersist() {
        status = true;
    }
}
