package com.casavieja.business.dto.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductoForm {
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
    private List<MultipartFile> archivos;
    private List<String> filesSave;//auxiliar para cargar los nombres de los nuevos archivos
}
