package com.casavieja.business.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class IngresoDetalleEntityPK implements Serializable {
    private short id;
    private long ingresoId;

    @Column(name = "id")
    @Id
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Column(name = "ingreso_id")
    @Id
    public long getIngresoId() {
        return ingresoId;
    }

    public void setIngresoId(long ingresoId) {
        this.ingresoId = ingresoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngresoDetalleEntityPK that = (IngresoDetalleEntityPK) o;
        return id == that.id && ingresoId == that.ingresoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingresoId);
    }
}
