package com.casavieja.business.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ingreso", schema = "business", catalog = "casa_vieja")
public class IngresoEntity {
    private Long id;
    private Long numero;
    private Date fecha;
    private Long usuarioRecepcionId;
    private Long usuarioEntregaId;
    private String observacion;
    private Integer branchOfficeId;
    private Timestamp createdAt;
    private Long createdBy;
    private Timestamp updatedAt;
    private Long updatedBy;
    private Boolean status;
    @Transient
    private String xusuarioRecepcionId;
    @Transient
    private String xusuarioEntregaId;

    @Id
    @GeneratedValue(generator = "business.ingreso_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.ingreso_id_seq", sequenceName = "business.ingreso_id_seq", allocationSize = 1)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "numero")
    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
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
    @Column(name = "usuario_recepcion_id")
    public Long getUsuarioRecepcionId() {
        return usuarioRecepcionId;
    }

    public void setUsuarioRecepcionId(Long usuarioRecepcionId) {
        this.usuarioRecepcionId = usuarioRecepcionId;
    }

    @Basic
    @Column(name = "usuario_entrega_id")
    public Long getUsuarioEntregaId() {
        return usuarioEntregaId;
    }

    public void setUsuarioEntregaId(Long usuarioEntregaId) {
        this.usuarioEntregaId = usuarioEntregaId;
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
    @Column(name = "branch_office_id")
    public Integer getBranchOfficeId() {
        return branchOfficeId;
    }

    public void setBranchOfficeId(Integer branchOfficeId) {
        this.branchOfficeId = branchOfficeId;
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
    @Column(name = "created_by")
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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
    @Column(name = "updated_by")
    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngresoEntity that = (IngresoEntity) o;
        return id == that.id && numero == that.numero && usuarioRecepcionId == that.usuarioRecepcionId && usuarioEntregaId == that.usuarioEntregaId && createdBy == that.createdBy && status == that.status && Objects.equals(fecha, that.fecha) && Objects.equals(observacion, that.observacion) && Objects.equals(branchOfficeId, that.branchOfficeId) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(updatedBy, that.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero, fecha, usuarioRecepcionId, usuarioEntregaId, observacion, branchOfficeId, createdAt, createdBy, updatedAt, updatedBy, status);
    }
    @PrePersist
    public void prePersist() {
        status = true;
    }
}
