package com.casavieja.business.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "movimiento_inventario_detalle", schema = "business", catalog = "casa_vieja")
@IdClass(MovimientoInventarioDetalleEntityPK.class)
public class MovimientoInventarioDetalleEntity {
    private Short id;
    private Long movimientoInventarioId;
    private Integer productoId;
    private String lote;
    private Date fechaElaboracion;
    private Date fechaVencimiento;
    private Integer cantidadUnidad;
    private Integer cantidadCaja;
    private Integer cantidadTotalUnidad;

    @Id
    @Column(name = "id")
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Id
    @Column(name = "movimiento_inventario_id")
    public Long getMovimientoInventarioId() {
        return movimientoInventarioId;
    }

    public void setMovimientoInventarioId(Long movimientoInventarioId) {
        this.movimientoInventarioId = movimientoInventarioId;
    }

    @Basic
    @Column(name = "producto_id")
    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    @Basic
    @Column(name = "lote")
    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    @Basic
    @Column(name = "fecha_elaboracion")
    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    @Basic
    @Column(name = "fecha_vencimiento")
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Basic
    @Column(name = "cantidad_unidad")
    public Integer getCantidadUnidad() {
        return cantidadUnidad;
    }

    public void setCantidadUnidad(Integer cantidadUnidad) {
        this.cantidadUnidad = cantidadUnidad;
    }

    @Basic
    @Column(name = "cantidad_caja")
    public Integer getCantidadCaja() {
        return cantidadCaja;
    }

    public void setCantidadCaja(Integer cantidadCaja) {
        this.cantidadCaja = cantidadCaja;
    }

    @Basic
    @Column(name = "cantidad_total_unidad")
    public Integer getCantidadTotalUnidad() {
        return cantidadTotalUnidad;
    }

    public void setCantidadTotalUnidad(Integer cantidadTotalUnidad) {
        this.cantidadTotalUnidad = cantidadTotalUnidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovimientoInventarioDetalleEntity that = (MovimientoInventarioDetalleEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(movimientoInventarioId, that.movimientoInventarioId) && Objects.equals(productoId, that.productoId) && Objects.equals(lote, that.lote) && Objects.equals(fechaElaboracion, that.fechaElaboracion) && Objects.equals(fechaVencimiento, that.fechaVencimiento) && Objects.equals(cantidadUnidad, that.cantidadUnidad) && Objects.equals(cantidadCaja, that.cantidadCaja) && Objects.equals(cantidadTotalUnidad, that.cantidadTotalUnidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movimientoInventarioId, productoId, lote, fechaElaboracion, fechaVencimiento, cantidadUnidad, cantidadCaja, cantidadTotalUnidad);
    }
}
