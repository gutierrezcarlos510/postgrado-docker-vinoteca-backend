package com.casavieja.business.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Tabla que guarda las categorias de productos
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
public class VisitaM implements Serializable {

    @Id
    private Long id;
    private Long salidaId;
    private Timestamp fecha;
    private Long clienteId;
    private String motivo;
    private Boolean status;
    private String xcliente;
}