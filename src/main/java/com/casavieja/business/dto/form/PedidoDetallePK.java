package com.casavieja.business.dto.form;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class PedidoDetallePK implements Serializable {
    @Id
    private long pedidoId;
    @Id
    private int productoId;
    @Id
    private String tipoCantidad;
}
