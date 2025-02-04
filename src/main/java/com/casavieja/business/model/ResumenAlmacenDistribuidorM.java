package com.casavieja.business.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class ResumenAlmacenDistribuidorM {
    @Id
    private Integer productoId;
    private Long salidaId;
    private String xproducto;
    private Short unidadPorCaja;
    private Integer cantidadEntregada;
    private Integer cantidadVendida;
    private Integer cantidadActual;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal total;

    public String getCantidadTotalEntregada() {
        return new StringBuilder("").append(calcularCajas(cantidadEntregada, unidadPorCaja)).append("c ").append(calcularUnidades(cantidadEntregada, unidadPorCaja)).append("u").toString();
    }
    public String getCantidadTotalVendida() {
        return new StringBuilder("").append(calcularCajas(cantidadVendida, unidadPorCaja)).append("c ").append(calcularUnidades(cantidadVendida, unidadPorCaja)).append("u").toString();
    }
    public String getCantidadTotalActual() {
        return new StringBuilder("").append(calcularCajas(cantidadActual, unidadPorCaja)).append("c ").append(calcularUnidades(cantidadActual, unidadPorCaja)).append("u").toString();
    }

    public int calcularUnidades(Integer cantidad, Short unixcaja) {
        if(unixcaja != null && cantidad != null) {
            if(cantidad > 0 && unixcaja > 0) {
                return cantidad % unixcaja;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }
    public int calcularCajas(Integer cantidad, Short unixcaja) {
        if(unixcaja != null && cantidad != null) {
            if(cantidad > 0 && unixcaja > 0) {
                return cantidad / unixcaja;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }
}
