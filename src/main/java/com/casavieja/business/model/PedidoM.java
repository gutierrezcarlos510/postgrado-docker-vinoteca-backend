package com.casavieja.business.model;

import com.casavieja.business.enums.PedidoE;
import com.casavieja.business.enums.PedidoEnumConverter;
import com.casavieja.business.enums.TipoPedidoE;
import com.casavieja.business.enums.TipoPedidoEnumConverter;
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
public class PedidoM {
    @Id
    private Long id;
    private Timestamp fecha;
    private Timestamp fechaEntrega;
    private Long clienteId;
    private String observacion;
    @Convert(converter = PedidoEnumConverter.class)
    private PedidoE estado;
    @Convert(converter = TipoPedidoEnumConverter.class)
    private TipoPedidoE tipo;
    private BigDecimal subtotal;
    private BigDecimal impuesto;
    private BigDecimal descuento;
    private BigDecimal total;
    private Long distribuidorId;
    private Boolean status;
    private String xdistribuidor;
    private String xcliente;
    @Transient
    private List<PedidoDetalleM> detalles;
}
