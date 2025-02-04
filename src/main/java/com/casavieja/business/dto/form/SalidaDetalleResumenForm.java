package com.casavieja.business.dto.form;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class SalidaDetalleResumenForm {
    @Id
    private Integer productoId;
    private String xproducto;
    private Short unidadPorCaja;
    private Integer total;
    public int getCantidadUnitaria() {
        if(unidadPorCaja != null && total != null) {
            if(total > 0 && unidadPorCaja > 0) {
                return total % unidadPorCaja;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }
    public int getCantidadCaja() {
        if(unidadPorCaja != null && total != null) {
            if(total > 0 && unidadPorCaja > 0) {
                return total / unidadPorCaja;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }
}
