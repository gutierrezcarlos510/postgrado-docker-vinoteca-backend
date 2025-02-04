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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Agrupa los roles de los usuarios
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
@Table(name = "ROLES")
public class Rol implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Codigo identificador
     */
    @Id
    @GeneratedValue(generator = "rol_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "rol_seq", sequenceName = "rol_seq", allocationSize = 1)
    @Column(name = "id", columnDefinition = "int4")
    private Integer id;

    /**
     * Nombre del rol
     */
    @Basic
    @Column(name = "name", columnDefinition = "varchar(50)")
    private String name;

    /**
     * Descripcion del rol
     */
    @Basic
    @Column(name = "description", columnDefinition = "varchar(150)")
    private String description;

    /**
     * icono del rol
     */
    @Basic
    @Column(name = "icon", columnDefinition = "varchar(50)")
    private String icon;

    /**
     * Estado activo o inactivo
     */
    @Basic
    @Column(name = "status", columnDefinition = "boolean")
    private Boolean status;
    @Column(unique = true)
    private String authority;

	@Transient
    private List<Menu> menuList;
    @Transient
    private List<Task> taskList;
    @PrePersist
    public void prePersist() {
    	if(authority!=null)
    		authority=authority.toUpperCase();
    }
    @PreUpdate
    public void preUpdate() {
    	if(authority!=null)
    		authority=authority.toUpperCase();
    }
}