package com.casavieja.business.dto.form;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
public class SalidaEntregaForm {
    @Id
    private Short id;
    private Integer createdBy;
    private Timestamp createdAt;
    private String obs;
    private Long salidaId;
    @Transient
    private List<SalidaEntregaDetalleForm> detalles;
}
