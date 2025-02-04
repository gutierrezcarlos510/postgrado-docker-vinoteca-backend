package com.casavieja.business.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "venta", schema = "business", catalog = "casa_vieja")
public class VentaEntity {
    @Id
    @GeneratedValue(generator = "business.venta_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.venta_id_seq", sequenceName = "business.venta_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    private Timestamp fecha;
    private Long clienteId;
    private Short formaPago;
    private String tipoVenta;
    private BigDecimal impuesto;
    private BigDecimal descuento;
    private BigDecimal subtotal;
    private BigDecimal total;
    private Boolean status;
    private Long salidaId;
    private Integer branchOfficeId;
    private Long usuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "fecha")
    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    @Basic
    @Column(name = "cliente_id")
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    @Basic
    @Column(name = "forma_pago")
    public Short getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(Short formaPago) {
        this.formaPago = formaPago;
    }

    @Basic
    @Column(name = "tipo_venta")
    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    @Basic
    @Column(name = "impuesto")
    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    @Basic
    @Column(name = "descuento")
    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    @Basic
    @Column(name = "subtotal")
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Basic
    @Column(name = "total")
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Basic
    @Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Basic
    @Column(name = "salida_id")
    public Long getSalidaId() {
        return salidaId;
    }

    public void setSalidaId(Long salidaId) {
        this.salidaId = salidaId;
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
    @Column(name = "usuario_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VentaEntity that = (VentaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        if (clienteId != null ? !clienteId.equals(that.clienteId) : that.clienteId != null) return false;
        if (formaPago != null ? !formaPago.equals(that.formaPago) : that.formaPago != null) return false;
        if (tipoVenta != null ? !tipoVenta.equals(that.tipoVenta) : that.tipoVenta != null) return false;
        if (impuesto != null ? !impuesto.equals(that.impuesto) : that.impuesto != null) return false;
        if (descuento != null ? !descuento.equals(that.descuento) : that.descuento != null) return false;
        if (subtotal != null ? !subtotal.equals(that.subtotal) : that.subtotal != null) return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (salidaId != null ? !salidaId.equals(that.salidaId) : that.salidaId != null) return false;
        if (branchOfficeId != null ? !branchOfficeId.equals(that.branchOfficeId) : that.branchOfficeId != null)
            return false;
        if (usuarioId != null ? !usuarioId.equals(that.usuarioId) : that.usuarioId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (clienteId != null ? clienteId.hashCode() : 0);
        result = 31 * result + (formaPago != null ? formaPago.hashCode() : 0);
        result = 31 * result + (tipoVenta != null ? tipoVenta.hashCode() : 0);
        result = 31 * result + (impuesto != null ? impuesto.hashCode() : 0);
        result = 31 * result + (descuento != null ? descuento.hashCode() : 0);
        result = 31 * result + (subtotal != null ? subtotal.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (salidaId != null ? salidaId.hashCode() : 0);
        result = 31 * result + (branchOfficeId != null ? branchOfficeId.hashCode() : 0);
        result = 31 * result + (usuarioId != null ? usuarioId.hashCode() : 0);
        return result;
    }
}
