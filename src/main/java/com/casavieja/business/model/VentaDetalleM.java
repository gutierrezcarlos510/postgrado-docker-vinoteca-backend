package com.casavieja.business.model;

import com.casavieja.business.entities.VentaDetalleEntityPK;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@IdClass(VentaDetalleEntityPK.class)
public class VentaDetalleM {
    @Id
    private Long ventaId;
    @Id
    private Integer productoId;
    @Id
    private String tipoCantidad;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal total;
    private Integer cantidadUnitariaTotal;
    private String xproducto;
    public String getXtipoCantidad() {
        return tipoCantidad.equals("1") ? "Unidad" : "Caja";
    }
}
