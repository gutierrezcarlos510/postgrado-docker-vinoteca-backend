package com.casavieja.business.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "visita", schema = "business", catalog = "casa_vieja")
public class VisitaEntity {
    @Id
    @GeneratedValue(generator = "business.visita_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.visita_id_seq", sequenceName = "business.visita_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    private Long salidaId;
    private Timestamp fecha;
    private Long clienteId;
    private String motivo;
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    @Column(name = "motivo")
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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

        VisitaEntity that = (VisitaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (salidaId != null ? !salidaId.equals(that.salidaId) : that.salidaId != null) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        if (clienteId != null ? !clienteId.equals(that.clienteId) : that.clienteId != null) return false;
        if (motivo != null ? !motivo.equals(that.motivo) : that.motivo != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (salidaId != null ? salidaId.hashCode() : 0);
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (clienteId != null ? clienteId.hashCode() : 0);
        result = 31 * result + (motivo != null ? motivo.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
