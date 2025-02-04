package com.casavieja.platform.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Tabla que agrupa los submenus con las url de los procesos
 *
 * @author Carlos Gutierrez
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SUBMENUS")
public class Submenu implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Codigo Identificador del submenu
     */
    @Id
    @GeneratedValue(generator = "submenu_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "submenu_seq", sequenceName = "submenu_seq", allocationSize = 1)
    @Column(name = "id", columnDefinition = "int4")
    private Integer id;

    /**
     * Nombre del submenu
     */
    @Basic
    @Column(name = "name", columnDefinition = "varchar(50)")
    @NotEmpty(message = "El campo no puede estar vacio.")
    @Size(min = 3, max = 50, message = "El texto debe estar entre 3 y 50 caracteres")
    private String name;

    /**
     * Descripcion del menu
     */
    @Basic
    @Column(name = "description", columnDefinition = "varchar(150)")
    @NotEmpty(message = "El campo no puede estar vacio.")
    @Size(min = 3, max = 150, message = "El texto debe estar entre 3 y 150 caracteres")
    private String description;

    /**
     * Url al controlador que se mapea
     */
    @Basic
    @Column(name = "url", columnDefinition = "varchar(50)")
    @NotEmpty(message = "El campo no puede estar vacio.")
    @Size(min = 3, max = 50, message = "El texto debe estar entre 3 y 150 caracteres")
    private String url;

    /**
     * Icono del submenu
     */
    @Basic
    @Column(name = "icon", columnDefinition = "varchar(50)")
    @NotEmpty(message = "El campo no puede estar vacio.")
    @Size(min = 3, max = 50, message = "El campo debe estar entre 3 y 50 caracteres")
    private String icon;

    /**
     * Estado activo o inactivo del submenu
     */
    @Basic
    @Column(name = "status", nullable = false, columnDefinition = "boolean")
    private Boolean status;
    @Transient
    private Boolean tieneAcceso;

}