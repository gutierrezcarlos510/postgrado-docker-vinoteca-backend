package com.casavieja.business.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Tabla que guarda las categorias de productos
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
public class CaracteristicaM implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    private Short id;
    private String nombre;
    private Boolean status;
    private Short tipo;
}