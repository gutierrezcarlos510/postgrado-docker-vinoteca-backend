package com.casavieja.business.entities;

import com.casavieja.business.enums.TipoPedidoE;
import com.casavieja.business.enums.TipoPedidoEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "pedido", schema = "business", catalog = "casa_vieja")
public class PedidoEntity {
    private Long id;
    private Timestamp fecha;
    private Timestamp fechaEntrega;
    private Long clienteId;
    private String observacion;
    private String tipo;
    private BigDecimal subtotal;
    private BigDecimal impuesto;
    private BigDecimal descuento;
    private BigDecimal total;
    private Long distribuidorId;
    private Boolean status;
    private Long usuarioId;
    private String estado;

    @Id
    @GeneratedValue(generator = "business.pedido_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.pedido_id_seq", sequenceName = "business.pedido_id_seq", allocationSize = 1)
    @Column(name = "id")
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
    @Column(name = "fecha_entrega")
    public Timestamp getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Timestamp fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
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
    @Column(name = "observacion")
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Basic
    @Column(name = "tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
    @Column(name = "total")
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Basic
    @Column(name = "distribuidor_id")
    public Long getDistribuidorId() {
        return distribuidorId;
    }

    public void setDistribuidorId(Long distribuidorId) {
        this.distribuidorId = distribuidorId;
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
    @Column(name = "usuario_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Basic
    @Column(name = "estado")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PedidoEntity that = (PedidoEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        if (fechaEntrega != null ? !fechaEntrega.equals(that.fechaEntrega) : that.fechaEntrega != null) return false;
        if (clienteId != null ? !clienteId.equals(that.clienteId) : that.clienteId != null) return false;
        if (observacion != null ? !observacion.equals(that.observacion) : that.observacion != null) return false;
        if (tipo != null ? !tipo.equals(that.tipo) : that.tipo != null) return false;
        if (subtotal != null ? !subtotal.equals(that.subtotal) : that.subtotal != null) return false;
        if (impuesto != null ? !impuesto.equals(that.impuesto) : that.impuesto != null) return false;
        if (descuento != null ? !descuento.equals(that.descuento) : that.descuento != null) return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;
        if (distribuidorId != null ? !distribuidorId.equals(that.distribuidorId) : that.distribuidorId != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (usuarioId != null ? !usuarioId.equals(that.usuarioId) : that.usuarioId != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (fechaEntrega != null ? fechaEntrega.hashCode() : 0);
        result = 31 * result + (clienteId != null ? clienteId.hashCode() : 0);
        result = 31 * result + (observacion != null ? observacion.hashCode() : 0);
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        result = 31 * result + (subtotal != null ? subtotal.hashCode() : 0);
        result = 31 * result + (impuesto != null ? impuesto.hashCode() : 0);
        result = 31 * result + (descuento != null ? descuento.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (distribuidorId != null ? distribuidorId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (usuarioId != null ? usuarioId.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }
}
