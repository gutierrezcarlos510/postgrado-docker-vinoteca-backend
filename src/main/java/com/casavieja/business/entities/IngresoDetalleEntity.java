package com.casavieja.business.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "ingreso_detalle", schema = "business", catalog = "casa_vieja")
@IdClass(IngresoDetalleEntityPK.class)
public class IngresoDetalleEntity {
    private short id;
    private long ingresoId;
    private int productoId;
    private String lote;
    private Date fechaElaboracion;
    private Date fechaVencimiento;
    private Integer cantidadUnitaria;
    private int cantidadCaja;
    private int cantidadTotalUnitaria;
    private Long almacenId;

    @Id
    @Column(name = "id")
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Id
    @Column(name = "ingreso_id")
    public long getIngresoId() {
        return ingresoId;
    }

    public void setIngresoId(long ingresoId) {
        this.ingresoId = ingresoId;
    }

    @Basic
    @Column(name = "producto_id")
    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
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
    @Column(name = "cantidad_unitaria")
    public Integer getCantidadUnitaria() {
        return cantidadUnitaria;
    }

    public void setCantidadUnitaria(Integer cantidadUnitaria) {
        this.cantidadUnitaria = cantidadUnitaria;
    }

    @Basic
    @Column(name = "cantidad_caja")
    public int getCantidadCaja() {
        return cantidadCaja;
    }

    public void setCantidadCaja(int cantidadCaja) {
        this.cantidadCaja = cantidadCaja;
    }

    @Basic
    @Column(name = "cantidad_total_unitaria")
    public int getCantidadTotalUnitaria() {
        return cantidadTotalUnitaria;
    }

    public void setCantidadTotalUnitaria(int cantidadTotalUnitaria) {
        this.cantidadTotalUnitaria = cantidadTotalUnitaria;
    }

    @Basic
    @Column(name = "almacen_id")
    public Long getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(Long almacenId) {
        this.almacenId = almacenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngresoDetalleEntity that = (IngresoDetalleEntity) o;
        return id == that.id && ingresoId == that.ingresoId && productoId == that.productoId && cantidadCaja == that.cantidadCaja && cantidadTotalUnitaria == that.cantidadTotalUnitaria && Objects.equals(lote, that.lote) && Objects.equals(fechaElaboracion, that.fechaElaboracion) && Objects.equals(fechaVencimiento, that.fechaVencimiento) && Objects.equals(cantidadUnitaria, that.cantidadUnitaria) && Objects.equals(almacenId, that.almacenId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingresoId, productoId, lote, fechaElaboracion, fechaVencimiento, cantidadUnitaria, cantidadCaja, cantidadTotalUnitaria, almacenId);
    }
}
