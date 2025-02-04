package com.casavieja.business.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Entity
public class ProductoM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private String nombreGenerico;
    private String nombreComercial;
    private String descripcion;
    private String caracteristica;
    private Short categoriaId;
    private String foto;
    private Short unidadPorCaja;
    private Short stockMedio;
    private Short stockAlto;
    private BigDecimal pcUnit;
    private BigDecimal pcCaja;
    private BigDecimal pvUnit;
    private BigDecimal pvCaja;
    private BigDecimal pvUnitDescuento;
    private BigDecimal pvCajaDescuento;
    private BigDecimal pvUnitPromo;
    private BigDecimal pvCajaPromo;
    private Boolean status;
    private Short presentacionUnitId;
    private Short presentacionCajaId;
    private String xcategoria;
}
