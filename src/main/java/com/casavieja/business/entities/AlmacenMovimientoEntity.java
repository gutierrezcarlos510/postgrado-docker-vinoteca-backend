package com.casavieja.business.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "almacen_movimiento", schema = "business", catalog = "casa_vieja")
@IdClass(AlmacenMovimientoEntityPK.class)
public class AlmacenMovimientoEntity {
    private long almacenId;
    private long movimientoInventarioId;
    private short movimientoInventarioDetalleId;
    private Integer cantidad;

    @Id
    @Column(name = "almacen_id")
    public long getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(Long almacenId) {
        this.almacenId = almacenId;
    }

    public void setAlmacenId(long almacenId) {
        this.almacenId = almacenId;
    }

    @Id
    @Column(name = "movimiento_inventario_id")
    public long getMovimientoInventarioId() {
        return movimientoInventarioId;
    }

    public void setMovimientoInventarioId(Long movimientoInventarioId) {
        this.movimientoInventarioId = movimientoInventarioId;
    }

    public void setMovimientoInventarioId(long movimientoInventarioId) {
        this.movimientoInventarioId = movimientoInventarioId;
    }

    @Id
    @Column(name = "movimiento_inventario_detalle_id")
    public short getMovimientoInventarioDetalleId() {
        return movimientoInventarioDetalleId;
    }

    public void setMovimientoInventarioDetalleId(Short movimientoInventarioDetalleId) {
        this.movimientoInventarioDetalleId = movimientoInventarioDetalleId;
    }

    public void setMovimientoInventarioDetalleId(short movimientoInventarioDetalleId) {
        this.movimientoInventarioDetalleId = movimientoInventarioDetalleId;
    }

    @Basic
    @Column(name = "cantidad")
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlmacenMovimientoEntity that = (AlmacenMovimientoEntity) o;
        return almacenId == that.almacenId && movimientoInventarioId == that.movimientoInventarioId && movimientoInventarioDetalleId == that.movimientoInventarioDetalleId && Objects.equals(cantidad, that.cantidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(almacenId, movimientoInventarioId, movimientoInventarioDetalleId, cantidad);
    }
}
