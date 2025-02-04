package com.casavieja.business.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "almacen", schema = "business", catalog = "casa_vieja")
public class AlmacenEntity {
    private Long id;
    private Integer branchOfficeId;
    private Integer cantidad;
    private Integer productoId;
    private Date fechaVencimiento;
    private String lote;

    @Id
    @GeneratedValue(generator = "business.almacen_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.almacen_id_seq", sequenceName = "business.almacen_id_seq", allocationSize = 1)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "branch_office_id")
    public Integer getBranchOfficeId() {
        return branchOfficeId;
    }

    public void setBranchOfficeId(Integer branchOfficeId) {
        this.branchOfficeId = branchOfficeId;
    }

    @Basic
    @Column(name = "cantidad")
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
    @Column(name = "fecha_vencimiento")
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Basic
    @Column(name = "lote")
    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlmacenEntity that = (AlmacenEntity) o;
        return id == that.id && branchOfficeId == that.branchOfficeId && cantidad == that.cantidad && Objects.equals(productoId, that.productoId) && Objects.equals(fechaVencimiento, that.fechaVencimiento) && Objects.equals(lote, that.lote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, branchOfficeId, cantidad, productoId, fechaVencimiento, lote);
    }
}
