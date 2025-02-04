package com.casavieja.business.model;

import com.casavieja.business.dto.form.PedidoDetallePK;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;

@Data
@Entity
@IdClass(PedidoDetallePK.class)
public class PedidoDetalleM {
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
    public String getXtipoCantidad() {
        return tipoCantidad.equals("1") ? "Unidad" : "Caja";
    }
}
