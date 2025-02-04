package com.casavieja.business.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
public class IngresoM {
    @Id
    private long id;
    private long numero;
    private Date fecha;
    private long usuarioRecepcionId;
    private long usuarioEntregaId;
    private String observacion;
    private Integer branchOfficeId;
    private Timestamp createdAt;
    private long createdBy;
    private Timestamp updatedAt;
    private Long updatedBy;
    private boolean status;
    private String xusuarioRecepcion;
    private String xusuarioEntrega;
    @Transient
    private List<IngresoDetalleM> detalles;
}
