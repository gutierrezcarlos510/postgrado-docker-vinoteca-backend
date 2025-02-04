package com.casavieja.business.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ResumenDetalleSalidaPorFinalizarM {
    @Id
    private int productoId;
    private String xproducto;
    private int devuelto;
    private int entregado;
    private int unidadPorCaja;
    public int getCantidadEntregadaUnidad() {
        return obtenerEnUnidades(entregado);
    }
    public int getCantidadEntregadaCaja() {
        return obtenerEnCajas(entregado);
    }
    public int getCantidadDevueltaUnidad() {
        return obtenerEnUnidades(devuelto);
    }
    public int getCantidadDevueltaCaja() {
        return obtenerEnCajas(devuelto);
    }
    public int getCantidadVendidaUnidad() {
        int vendido = entregado - devuelto;
        return obtenerEnUnidades(vendido);
    }
    public int getCantidadVendidaCaja() {
        int vendido = entregado - devuelto;
        return obtenerEnCajas(vendido);
    }
    public int obtenerEnUnidades(int cantidad) {
        if(cantidad > 0 && unidadPorCaja > 0) {
            return cantidad % unidadPorCaja;
        } else {
            return 0;
        }
    }
    public int obtenerEnCajas(int cantidad) {
        if(cantidad > 0 && unidadPorCaja > 0) {
            return cantidad / unidadPorCaja;
        } else {
            return 0;
        }
    }
}
