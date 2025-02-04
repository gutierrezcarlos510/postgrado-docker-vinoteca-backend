package com.casavieja.business.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class VentaDetalleEntityPK implements Serializable {
    private Long ventaId;
    private Integer productoId;
    private String tipoCantidad;

    @Column(name = "venta_id", nullable = false)
    @Id
    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    @Column(name = "producto_id", nullable = false)
    @Id
    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    @Column(name = "tipo_cantidad", nullable = false, length = 1)
    @Id
    public String getTipoCantidad() {
        return tipoCantidad;
    }

    public void setTipoCantidad(String tipoCantidad) {
        this.tipoCantidad = tipoCantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VentaDetalleEntityPK that = (VentaDetalleEntityPK) o;

        if (ventaId != null ? !ventaId.equals(that.ventaId) : that.ventaId != null) return false;
        if (productoId != null ? !productoId.equals(that.productoId) : that.productoId != null) return false;
        if (tipoCantidad != null ? !tipoCantidad.equals(that.tipoCantidad) : that.tipoCantidad != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ventaId != null ? ventaId.hashCode() : 0;
        result = 31 * result + (productoId != null ? productoId.hashCode() : 0);
        result = 31 * result + (tipoCantidad != null ? tipoCantidad.hashCode() : 0);
        return result;
    }
}
