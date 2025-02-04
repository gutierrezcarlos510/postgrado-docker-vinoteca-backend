package com.casavieja.business.dto.form;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;

@Data
@Entity
@IdClass(PedidoDetallePK.class)
public class PedidoDetalleForm {
    @Id
    private Long pedidoId;
    @Id
    private Integer productoId;
    @Id
    private String tipoCantidad;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal subtotal;
    private String xproducto;
}
