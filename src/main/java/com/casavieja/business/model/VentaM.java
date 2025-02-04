package com.casavieja.business.model;

import com.casavieja.business.enums.FormaPagoE;
import com.casavieja.business.enums.FormaPagoEnumConverter;
import com.casavieja.business.enums.TipoVentaE;
import com.casavieja.business.enums.TipoVentaEnumConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
public class VentaM {
    @Id
    private Long id;
    private Timestamp fecha;
    private Long clienteId;
    @Convert(converter = FormaPagoEnumConverter.class)
    private FormaPagoE formaPago;
    @Convert(converter = TipoVentaEnumConverter.class)
    private TipoVentaE tipoVenta;
    private BigDecimal impuesto;
    private BigDecimal descuento;
    private BigDecimal subtotal;
    private BigDecimal total;
    private Boolean status;
    private Long salidaId;
    private Integer branchOfficeId;
    private Long usuarioId;
    private String xcliente;
    @Transient
    private Long pedidoId;
//    private String xusuario;
    @Transient
    private List<VentaDetalleM> detalles;


}
