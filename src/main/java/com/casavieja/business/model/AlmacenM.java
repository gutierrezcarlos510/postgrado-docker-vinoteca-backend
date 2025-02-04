package com.casavieja.business.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class AlmacenM {
    @Id
    private Integer productoId;
    private String xproducto;
    private Short unidadPorCaja;
    private Short stockMedio;
    private Short stockAlto;
    private Integer cantidad;
    public String getStockInventario() {
        if(this.stockMedio != null && this.cantidad >= this.stockMedio) {
            if(this.stockAlto != null && this.cantidad >= this.stockAlto) {
                return "Alto";
            } else {
                return "Medio";
            }
        }
        return "bajo";
    }
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
