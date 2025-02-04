package com.casavieja.business.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "caracteristica", schema = "business", catalog = "casa_vieja")
public class CaracteristicaEntity {
    private Short id;
    private String nombre;
    private String tipo;
    private Boolean status;

    @Id
    @GeneratedValue(generator = "business.caracteristica_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.caracteristica_id_seq", sequenceName = "business.caracteristica_id_seq", allocationSize = 1)
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
    @Column(name = "tipo", nullable = false, length = 1)
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean estado) {
        this.status = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaracteristicaEntity that = (CaracteristicaEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(tipo, that.tipo) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, tipo, status);
    }

    @PrePersist
    public void prePersist() {
        status = true;
    }
}
