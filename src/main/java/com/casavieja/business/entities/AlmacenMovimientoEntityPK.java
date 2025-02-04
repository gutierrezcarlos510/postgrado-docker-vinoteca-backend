package com.casavieja.business.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AlmacenMovimientoEntityPK implements Serializable {
    private long almacenId;
    private long movimientoInventarioId;
    private short movimientoInventarioDetalleId;

    @Column(name = "almacen_id")
    @Id
    public long getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(long almacenId) {
        this.almacenId = almacenId;
    }

    @Column(name = "movimiento_inventario_id")
    @Id
    public long getMovimientoInventarioId() {
        return movimientoInventarioId;
    }

    public void setMovimientoInventarioId(long movimientoInventarioId) {
        this.movimientoInventarioId = movimientoInventarioId;
    }

    @Column(name = "movimiento_inventario_detalle_id")
    @Id
    public short getMovimientoInventarioDetalleId() {
        return movimientoInventarioDetalleId;
    }

    public void setMovimientoInventarioDetalleId(short movimientoInventarioDetalleId) {
        this.movimientoInventarioDetalleId = movimientoInventarioDetalleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlmacenMovimientoEntityPK that = (AlmacenMovimientoEntityPK) o;
        return almacenId == that.almacenId && movimientoInventarioId == that.movimientoInventarioId && movimientoInventarioDetalleId == that.movimientoInventarioDetalleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(almacenId, movimientoInventarioId, movimientoInventarioDetalleId);
    }
}
