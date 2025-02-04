package com.casavieja.business.entities;

import javax.persistence.*;

@Entity
@Table(name = "venta_subdetalle", schema = "business", catalog = "casa_vieja")
@IdClass(VentaSubdetalleEntityPK.class)
public class VentaSubdetalleEntity {
    private Long ventaId;
    private Integer productoId;
    private String tipoCantidad;
    private Short id;
    private Long almacenId;
    private Integer cantidadUnitaria;
    private Long salidaId;
    private Short salidaEntregaId;
    private Short salidaEntregaDetalleId;
    private Long distribuidorId;

    @Id
    @Column(name = "venta_id", nullable = false)
    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    @Id
    @Column(name = "producto_id", nullable = false)
    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    @Id
    @Column(name = "tipo_cantidad", nullable = false, length = 1)
    public String getTipoCantidad() {
        return tipoCantidad;
    }

    public void setTipoCantidad(String tipoCantidad) {
        this.tipoCantidad = tipoCantidad;
    }

    @Id
    @Column(name = "id", nullable = false)
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "almacen_id", nullable = false)
    public Long getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(Long almacenId) {
        this.almacenId = almacenId;
    }

    @Basic
    @Column(name = "cantidad_unitaria", nullable = false)
    public Integer getCantidadUnitaria() {
        return cantidadUnitaria;
    }

    public void setCantidadUnitaria(Integer cantidadUnitaria) {
        this.cantidadUnitaria = cantidadUnitaria;
    }

    @Basic
    @Column(name = "salida_id", nullable = true)
    public Long getSalidaId() {
        return salidaId;
    }

    public void setSalidaId(Long salidaId) {
        this.salidaId = salidaId;
    }

    @Basic
    @Column(name = "salida_entrega_id", nullable = true)
    public Short getSalidaEntregaId() {
        return salidaEntregaId;
    }

    public void setSalidaEntregaId(Short salidaEntregaId) {
        this.salidaEntregaId = salidaEntregaId;
    }

    @Basic
    @Column(name = "salida_entrega_detalle_id", nullable = true)
    public Short getSalidaEntregaDetalleId() {
        return salidaEntregaDetalleId;
    }

    public void setSalidaEntregaDetalleId(Short salidaEntregaDetalleId) {
        this.salidaEntregaDetalleId = salidaEntregaDetalleId;
    }

    @Basic
    @Column(name = "distribuidor_id", nullable = true)
    public Long getDistribuidorId() {
        return distribuidorId;
    }

    public void setDistribuidorId(Long distribuidorId) {
        this.distribuidorId = distribuidorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VentaSubdetalleEntity that = (VentaSubdetalleEntity) o;

        if (ventaId != null ? !ventaId.equals(that.ventaId) : that.ventaId != null) return false;
        if (productoId != null ? !productoId.equals(that.productoId) : that.productoId != null) return false;
        if (tipoCantidad != null ? !tipoCantidad.equals(that.tipoCantidad) : that.tipoCantidad != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (almacenId != null ? !almacenId.equals(that.almacenId) : that.almacenId != null) return false;
        if (cantidadUnitaria != null ? !cantidadUnitaria.equals(that.cantidadUnitaria) : that.cantidadUnitaria != null)
            return false;
        if (salidaId != null ? !salidaId.equals(that.salidaId) : that.salidaId != null) return false;
        if (salidaEntregaId != null ? !salidaEntregaId.equals(that.salidaEntregaId) : that.salidaEntregaId != null)
            return false;
        if (salidaEntregaDetalleId != null ? !salidaEntregaDetalleId.equals(that.salidaEntregaDetalleId) : that.salidaEntregaDetalleId != null)
            return false;
        if (distribuidorId != null ? !distribuidorId.equals(that.distribuidorId) : that.distribuidorId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ventaId != null ? ventaId.hashCode() : 0;
        result = 31 * result + (productoId != null ? productoId.hashCode() : 0);
        result = 31 * result + (tipoCantidad != null ? tipoCantidad.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (almacenId != null ? almacenId.hashCode() : 0);
        result = 31 * result + (cantidadUnitaria != null ? cantidadUnitaria.hashCode() : 0);
        result = 31 * result + (salidaId != null ? salidaId.hashCode() : 0);
        result = 31 * result + (salidaEntregaId != null ? salidaEntregaId.hashCode() : 0);
        result = 31 * result + (salidaEntregaDetalleId != null ? salidaEntregaDetalleId.hashCode() : 0);
        result = 31 * result + (distribuidorId != null ? distribuidorId.hashCode() : 0);
        return result;
    }
}
