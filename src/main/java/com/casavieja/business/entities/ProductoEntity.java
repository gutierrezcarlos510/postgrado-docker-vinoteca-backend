package com.casavieja.business.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "producto", schema = "business", catalog = "casa_vieja")
public class ProductoEntity {
    private Integer id;
    private String nombreGenerico;
    private String nombreComercial;
    private String descripcion;
    private String caracteristica;
    private Short categoriaId;
    private String foto;
    private Short unidadPorCaja;
    private Short stockMedio;
    private Short stockAlto;
    private BigDecimal pcUnit;
    private BigDecimal pcCaja;
    private BigDecimal pvUnit;
    private BigDecimal pvCaja;
    private BigDecimal pvUnitDescuento;
    private BigDecimal pvCajaDescuento;
    private BigDecimal pvUnitPromo;
    private BigDecimal pvCajaPromo;
    private Boolean status;
    private Short presentacionUnitId;
    private Short presentacionCajaId;

    @Id
    @GeneratedValue(generator = "business.producto_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.producto_id_seq", sequenceName = "business.producto_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre_generico", nullable = false, length = 150)
    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public void setNombreGenerico(String nombreGenerico) {
        this.nombreGenerico = nombreGenerico;
    }

    @Basic
    @Column(name = "nombre_comercial", nullable = false, length = 150)
    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    @Basic
    @Column(name = "descripcion", nullable = true, length = 1500)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Basic
    @Column(name = "caracteristica", nullable = false, length = 250)
    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    @Basic
    @Column(name = "categoria_id", nullable = false)
    public Short getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Short categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Basic
    @Column(name = "foto", nullable = false, length = 30)
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Basic
    @Column(name = "unidad_por_caja", nullable = true)
    public Short getUnidadPorCaja() {
        return unidadPorCaja;
    }

    public void setUnidadPorCaja(Short unidadPorCaja) {
        this.unidadPorCaja = unidadPorCaja;
    }

    @Basic
    @Column(name = "stock_medio", nullable = false)
    public Short getStockMedio() {
        return stockMedio;
    }

    public void setStockMedio(Short stockMedio) {
        this.stockMedio = stockMedio;
    }

    @Basic
    @Column(name = "stock_alto", nullable = false)
    public Short getStockAlto() {
        return stockAlto;
    }

    public void setStockAlto(Short stockAlto) {
        this.stockAlto = stockAlto;
    }

    @Basic
    @Column(name = "pc_unit", nullable = false, precision = 2)
    public BigDecimal getPcUnit() {
        return pcUnit;
    }

    public void setPcUnit(BigDecimal pcUnit) {
        this.pcUnit = pcUnit;
    }

    @Basic
    @Column(name = "pc_caja", nullable = true, precision = 2)
    public BigDecimal getPcCaja() {
        return pcCaja;
    }

    public void setPcCaja(BigDecimal pcCaja) {
        this.pcCaja = pcCaja;
    }

    @Basic
    @Column(name = "pv_unit", nullable = false, precision = 2)
    public BigDecimal getPvUnit() {
        return pvUnit;
    }

    public void setPvUnit(BigDecimal pvUnit) {
        this.pvUnit = pvUnit;
    }

    @Basic
    @Column(name = "pv_caja", nullable = false, precision = 2)
    public BigDecimal getPvCaja() {
        return pvCaja;
    }

    public void setPvCaja(BigDecimal pvCaja) {
        this.pvCaja = pvCaja;
    }

    @Basic
    @Column(name = "pv_unit_descuento", nullable = false, precision = 2)
    public BigDecimal getPvUnitDescuento() {
        return pvUnitDescuento;
    }

    public void setPvUnitDescuento(BigDecimal pvUnitDescuento) {
        this.pvUnitDescuento = pvUnitDescuento;
    }

    @Basic
    @Column(name = "pv_caja_descuento", nullable = false, precision = 2)
    public BigDecimal getPvCajaDescuento() {
        return pvCajaDescuento;
    }

    public void setPvCajaDescuento(BigDecimal pvCajaDescuento) {
        this.pvCajaDescuento = pvCajaDescuento;
    }

    @Basic
    @Column(name = "pv_unit_promo", nullable = false, precision = 2)
    public BigDecimal getPvUnitPromo() {
        return pvUnitPromo;
    }

    public void setPvUnitPromo(BigDecimal pvUnitPromo) {
        this.pvUnitPromo = pvUnitPromo;
    }

    @Basic
    @Column(name = "pv_caja_promo", nullable = false, precision = 2)
    public BigDecimal getPvCajaPromo() {
        return pvCajaPromo;
    }

    public void setPvCajaPromo(BigDecimal pvCajaPromo) {
        this.pvCajaPromo = pvCajaPromo;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Basic
    @Column(name = "presentacion_unit_id", nullable = false)
    public Short getPresentacionUnitId() {
        return presentacionUnitId;
    }

    public void setPresentacionUnitId(Short presentacionUnitId) {
        this.presentacionUnitId = presentacionUnitId;
    }

    @Basic
    @Column(name = "presentacion_caja_id", nullable = false)
    public Short getPresentacionCajaId() {
        return presentacionCajaId;
    }

    public void setPresentacionCajaId(Short presentacionCajaId) {
        this.presentacionCajaId = presentacionCajaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductoEntity that = (ProductoEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nombreGenerico, that.nombreGenerico) && Objects.equals(nombreComercial, that.nombreComercial) && Objects.equals(descripcion, that.descripcion) && Objects.equals(caracteristica, that.caracteristica) && Objects.equals(categoriaId, that.categoriaId) && Objects.equals(foto, that.foto) && Objects.equals(unidadPorCaja, that.unidadPorCaja) && Objects.equals(stockMedio, that.stockMedio) && Objects.equals(stockAlto, that.stockAlto) && Objects.equals(pcUnit, that.pcUnit) && Objects.equals(pcCaja, that.pcCaja) && Objects.equals(pvUnit, that.pvUnit) && Objects.equals(pvCaja, that.pvCaja) && Objects.equals(pvUnitDescuento, that.pvUnitDescuento) && Objects.equals(pvCajaDescuento, that.pvCajaDescuento) && Objects.equals(pvUnitPromo, that.pvUnitPromo) && Objects.equals(pvCajaPromo, that.pvCajaPromo) && Objects.equals(status, that.status) && Objects.equals(presentacionUnitId, that.presentacionUnitId) && Objects.equals(presentacionCajaId, that.presentacionCajaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreGenerico, nombreComercial, descripcion, caracteristica, categoriaId, foto, unidadPorCaja, stockMedio, stockAlto, pcUnit, pcCaja, pvUnit, pvCaja, pvUnitDescuento, pvCajaDescuento, pvUnitPromo, pvCajaPromo, status, presentacionUnitId, presentacionCajaId);
    }
    @PrePersist
    public void prePersist() {
        status = true;
    }
}
