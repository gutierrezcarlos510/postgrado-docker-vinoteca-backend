package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Tabla que guarda todos los modulos desarrollados en el sistema
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
@Table(name = "MODULES_SYSTEMS")
public class ModuleSystem implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Codigo identificador del modulo del sistema
     */
    @Id
    @GeneratedValue(generator = "module_system_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "module_system_seq", sequenceName = "module_system_seq", allocationSize = 1)
    @Column(name = "id", columnDefinition = "int4")
    private Integer id;

    /**
     * Codigo corto que ve el usuario
     */
    @Basic
    @Column(name = "code_module", columnDefinition = "varchar(25)")
    private String codeModule;

    /**
     * nombre del modulo del sistema
     */
    @Basic
    @Column(name = "name", columnDefinition = "varchar(50)")
    private String name;

    /**
     * Descripcion del modulo del sistema
     */
    @Basic
    @Column(name = "description", columnDefinition = "varchar(150)")
    private String description;

    /**
     * Estado activo o inactivo
     */
    @Basic
    @Column(name = "status", columnDefinition = "boolean")
    private Boolean status;

}