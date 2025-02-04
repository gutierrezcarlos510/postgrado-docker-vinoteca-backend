package com.casavieja.business.dto.form;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class SalidaEntregaDetallePK implements Serializable {
    @Id
    private long salidaId;
    @Id
    private short salidaEntregaId;
    @Id
    private short id;
}
