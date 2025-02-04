package com.casavieja.business.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "movimiento_inventario", schema = "business", catalog = "casa_vieja")
public class MovimientoInventarioEntity {
    @Id
    @GeneratedValue(generator = "business.movimiento_inventario_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.movimiento_inventario_id_seq", sequenceName = "business.movimiento_inventario_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    private Long usuarioId;
    private String tipo;
    private String motivo;
    private Date fecha;
    private Long createdBy;
    private Timestamp createdAt;
    private Long updatedBy;
    private Timestamp updatedAt;
    private Boolean status;
    private Integer sucursalOrigen;
    private Integer sucursalDestino;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    @Column(name = "tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Basic
    @Column(name = "motivo")
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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
    @Column(name = "sucursal_origen")
    public Integer getSucursalOrigen() {
        return sucursalOrigen;
    }

    public void setSucursalOrigen(Integer sucursalOrigen) {
        this.sucursalOrigen = sucursalOrigen;
    }

    @Basic
    @Column(name = "sucursal_destino")
    public Integer getSucursalDestino() {
        return sucursalDestino;
    }

    public void setSucursalDestino(Integer sucursalDestino) {
        this.sucursalDestino = sucursalDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovimientoInventarioEntity that = (MovimientoInventarioEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(usuarioId, that.usuarioId) && Objects.equals(tipo, that.tipo) && Objects.equals(motivo, that.motivo) && Objects.equals(fecha, that.fecha) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedBy, that.updatedBy) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(status, that.status) && Objects.equals(sucursalOrigen, that.sucursalOrigen) && Objects.equals(sucursalDestino, that.sucursalDestino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuarioId, tipo, motivo, fecha, createdBy, createdAt, updatedBy, updatedAt, status, sucursalOrigen, sucursalDestino);
    }
}
