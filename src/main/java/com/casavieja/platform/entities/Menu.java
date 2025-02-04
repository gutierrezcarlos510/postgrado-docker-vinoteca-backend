package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Tabla que agrupa los submenus con las url de los procesos
 *
 * @author Carlos Gutierrez
 */
@Getter
@Setter
@Entity
@Table(name = "MENUS")
public class Menu implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Codigo Identificador del menu
     */
    @Id
    @GeneratedValue(generator = "menu_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "menu_seq", sequenceName = "menu_seq", allocationSize = 1)
    @Column(name = "id", columnDefinition = "int4")
    private Integer id;

    /**
     * Nombre del menu
     */
    @Basic
    @Column(name = "name", columnDefinition = "varchar(50)")
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    /**
     * Descripcion del menu
     */
    @Basic
    @Column(name = "description", columnDefinition = "varchar(150)")
    @NotEmpty
    @Size(min = 3, max = 150)
    private String description;

    /**
     * Url al controlador que se mapea
     */
    @Basic
    @Column(name = "url", columnDefinition = "varchar(50)")
    @NotEmpty
    @Size(min = 1, max = 50)
    private String url;

    /**
     * Icono del menu
     */
    @Basic
    @Column(name = "icon", columnDefinition = "varchar(50)")
    @NotEmpty
    @Size(min = 3, max = 50)
    private String icon;

    /**
     * Estado activo o inactivo del menu
     */
    @Basic
    @Column(name = "status", nullable = false, columnDefinition = "boolean")
    @NotNull
    private Boolean status;
    @Transient
    private List<Submenu> submenuList;

}