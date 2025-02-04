package com.casavieja.business.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class MovimientoInventarioDetalleEntityPK implements Serializable {
    private Short id;
    private Long movimientoInventarioId;

    @Column(name = "id")
    @Id
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Column(name = "movimiento_inventario_id")
    @Id
    public Long getMovimientoInventarioId() {
        return movimientoInventarioId;
    }

    public void setMovimientoInventarioId(Long movimientoInventarioId) {
        this.movimientoInventarioId = movimientoInventarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovimientoInventarioDetalleEntityPK that = (MovimientoInventarioDetalleEntityPK) o;
        return Objects.equals(id, that.id) && Objects.equals(movimientoInventarioId, that.movimientoInventarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movimientoInventarioId);
    }
}
