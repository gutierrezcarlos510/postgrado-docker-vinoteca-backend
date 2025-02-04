package com.casavieja.business.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "venta_detalle", schema = "business", catalog = "casa_vieja")
@IdClass(VentaDetalleEntityPK.class)
public class VentaDetalleEntity {
    private Long ventaId;
    private Integer productoId;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal total;
    private Integer cantidadUnitariaTotal;
    private String tipoCantidad;

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

    @Basic
    @Column(name = "cantidad", nullable = false)
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Basic
    @Column(name = "precio", nullable = false, precision = 2)
    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @Basic
    @Column(name = "subtotal", nullable = false, precision = 2)
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Basic
    @Column(name = "descuento", nullable = false, precision = 2)
    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    @Basic
    @Column(name = "total", nullable = false, precision = 2)
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Basic
    @Column(name = "cantidad_unitaria_total", nullable = false)
    public Integer getCantidadUnitariaTotal() {
        return cantidadUnitariaTotal;
    }

    public void setCantidadUnitariaTotal(Integer cantidadUnitariaTotal) {
        this.cantidadUnitariaTotal = cantidadUnitariaTotal;
    }

    @Id
    @Column(name = "tipo_cantidad", nullable = false, length = 1)
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

        VentaDetalleEntity that = (VentaDetalleEntity) o;

        if (ventaId != null ? !ventaId.equals(that.ventaId) : that.ventaId != null) return false;
        if (productoId != null ? !productoId.equals(that.productoId) : that.productoId != null) return false;
        if (cantidad != null ? !cantidad.equals(that.cantidad) : that.cantidad != null) return false;
        if (precio != null ? !precio.equals(that.precio) : that.precio != null) return false;
        if (subtotal != null ? !subtotal.equals(that.subtotal) : that.subtotal != null) return false;
        if (descuento != null ? !descuento.equals(that.descuento) : that.descuento != null) return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;
        if (cantidadUnitariaTotal != null ? !cantidadUnitariaTotal.equals(that.cantidadUnitariaTotal) : that.cantidadUnitariaTotal != null)
            return false;
        if (tipoCantidad != null ? !tipoCantidad.equals(that.tipoCantidad) : that.tipoCantidad != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ventaId != null ? ventaId.hashCode() : 0;
        result = 31 * result + (productoId != null ? productoId.hashCode() : 0);
        result = 31 * result + (cantidad != null ? cantidad.hashCode() : 0);
        result = 31 * result + (precio != null ? precio.hashCode() : 0);
        result = 31 * result + (subtotal != null ? subtotal.hashCode() : 0);
        result = 31 * result + (descuento != null ? descuento.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (cantidadUnitariaTotal != null ? cantidadUnitariaTotal.hashCode() : 0);
        result = 31 * result + (tipoCantidad != null ? tipoCantidad.hashCode() : 0);
        return result;
    }
}
