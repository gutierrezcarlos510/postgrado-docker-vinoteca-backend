package com.casavieja.business.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class AlmacenDistribuidorM {
    @Id
    private Integer productoId;
    private String xproducto;
    private Short unidadPorCaja;
    private BigDecimal pvUnit;
    private BigDecimal pvCaja;
    private Integer cantidad;
    private String foto;
    public int getCantidadUnitaria() {
        if(unidadPorCaja != null && cantidad != null) {
            if(cantidad > 0 && unidadPorCaja > 0) {
                return cantidad % unidadPorCaja;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }
    public int getCantidadCaja() {
        if(unidadPorCaja != null && cantidad != null) {
            if(cantidad > 0 && unidadPorCaja > 0) {
                return cantidad / unidadPorCaja;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }
}
