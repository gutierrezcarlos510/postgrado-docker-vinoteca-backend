package com.casavieja.business.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "salida", schema = "business", catalog = "casa_vieja")
public class SalidaEntity {
    private Long id;
    private Date fecha;
    private Long distribuidorId;
    private String obs;
    private String estadoSalida;
    private Integer branchOfficeId;
    private String obsAlFinalizar;
    private Long createdBy;
    private Timestamp createdAt;
    private Long updatedBy;
    private Timestamp updatedAt;
    private Boolean status;
    private BigDecimal totalVentaContado;
    private BigDecimal totalOtroTipoPago;
    private BigDecimal totalGastos;
    private BigDecimal totalEfectivoEntregado;
    private BigDecimal totalDescuento;
    private BigDecimal totalImpuesto;
    private BigDecimal totalGeneral;

    @Id
    @GeneratedValue(generator = "business.salida_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.salida_id_seq", sequenceName = "business.salida_id_seq", allocationSize = 1)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "fecha")
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
    @Column(name = "obs")
    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Basic
    @Column(name = "estado_salida")
    public String getEstadoSalida() {
        return estadoSalida;
    }

    public void setEstadoSalida(String estadoSalida) {
        this.estadoSalida = estadoSalida;
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
    @Column(name = "obs_al_finalizar")
    public String getObsAlFinalizar() {
        return obsAlFinalizar;
    }

    public void setObsAlFinalizar(String obsAlFinalizar) {
        this.obsAlFinalizar = obsAlFinalizar;
    }

    @Basic
    @Column(name = "created_by")
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_by")
    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "updated_at")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
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
    @Column(name = "total_venta_contado")
    public BigDecimal getTotalVentaContado() {
        return totalVentaContado;
    }

    public void setTotalVentaContado(BigDecimal totalVentaContado) {
        this.totalVentaContado = totalVentaContado;
    }

    @Basic
    @Column(name = "total_otro_tipo_pago")
    public BigDecimal getTotalOtroTipoPago() {
        return totalOtroTipoPago;
    }

    public void setTotalOtroTipoPago(BigDecimal totalOtroTipoPago) {
        this.totalOtroTipoPago = totalOtroTipoPago;
    }

    @Basic
    @Column(name = "total_gastos")
    public BigDecimal getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(BigDecimal totalGastos) {
        this.totalGastos = totalGastos;
    }

    @Basic
    @Column(name = "total_efectivo_entregado")
    public BigDecimal getTotalEfectivoEntregado() {
        return totalEfectivoEntregado;
    }

    public void setTotalEfectivoEntregado(BigDecimal totalEfectivoEntregado) {
        this.totalEfectivoEntregado = totalEfectivoEntregado;
    }

    @Basic
    @Column(name = "total_descuento")
    public BigDecimal getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(BigDecimal totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    @Basic
    @Column(name = "total_impuesto")
    public BigDecimal getTotalImpuesto() {
        return totalImpuesto;
    }

    public void setTotalImpuesto(BigDecimal totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    @Basic
    @Column(name = "total_general")
    public BigDecimal getTotalGeneral() {
        return totalGeneral;
    }

    public void setTotalGeneral(BigDecimal totalGeneral) {
        this.totalGeneral = totalGeneral;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalidaEntity that = (SalidaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        if (distribuidorId != null ? !distribuidorId.equals(that.distribuidorId) : that.distribuidorId != null)
            return false;
        if (obs != null ? !obs.equals(that.obs) : that.obs != null) return false;
        if (estadoSalida != null ? !estadoSalida.equals(that.estadoSalida) : that.estadoSalida != null) return false;
        if (branchOfficeId != null ? !branchOfficeId.equals(that.branchOfficeId) : that.branchOfficeId != null)
            return false;
        if (obsAlFinalizar != null ? !obsAlFinalizar.equals(that.obsAlFinalizar) : that.obsAlFinalizar != null)
            return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedBy != null ? !updatedBy.equals(that.updatedBy) : that.updatedBy != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (totalVentaContado != null ? !totalVentaContado.equals(that.totalVentaContado) : that.totalVentaContado != null)
            return false;
        if (totalOtroTipoPago != null ? !totalOtroTipoPago.equals(that.totalOtroTipoPago) : that.totalOtroTipoPago != null)
            return false;
        if (totalGastos != null ? !totalGastos.equals(that.totalGastos) : that.totalGastos != null) return false;
        if (totalEfectivoEntregado != null ? !totalEfectivoEntregado.equals(that.totalEfectivoEntregado) : that.totalEfectivoEntregado != null)
            return false;
        if (totalDescuento != null ? !totalDescuento.equals(that.totalDescuento) : that.totalDescuento != null)
            return false;
        if (totalImpuesto != null ? !totalImpuesto.equals(that.totalImpuesto) : that.totalImpuesto != null)
            return false;
        if (totalGeneral != null ? !totalGeneral.equals(that.totalGeneral) : that.totalGeneral != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (distribuidorId != null ? distribuidorId.hashCode() : 0);
        result = 31 * result + (obs != null ? obs.hashCode() : 0);
        result = 31 * result + (estadoSalida != null ? estadoSalida.hashCode() : 0);
        result = 31 * result + (branchOfficeId != null ? branchOfficeId.hashCode() : 0);
        result = 31 * result + (obsAlFinalizar != null ? obsAlFinalizar.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (totalVentaContado != null ? totalVentaContado.hashCode() : 0);
        result = 31 * result + (totalOtroTipoPago != null ? totalOtroTipoPago.hashCode() : 0);
        result = 31 * result + (totalGastos != null ? totalGastos.hashCode() : 0);
        result = 31 * result + (totalEfectivoEntregado != null ? totalEfectivoEntregado.hashCode() : 0);
        result = 31 * result + (totalDescuento != null ? totalDescuento.hashCode() : 0);
        result = 31 * result + (totalImpuesto != null ? totalImpuesto.hashCode() : 0);
        result = 31 * result + (totalGeneral != null ? totalGeneral.hashCode() : 0);
        return result;
    }
    @PrePersist
    public void prePersist() {
        status = true;
        totalVentaContado = BigDecimal.ZERO;
        totalOtroTipoPago = BigDecimal.ZERO;
        totalGastos = BigDecimal.ZERO;
        totalEfectivoEntregado = BigDecimal.ZERO;
        totalDescuento = BigDecimal.ZERO;
        totalImpuesto = BigDecimal.ZERO;
        totalGeneral = BigDecimal.ZERO;
    }
}
