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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Tabla que agrupa todos los controladores del sistema
 *
 * @author CARLOS
 */
@Getter
@Setter
@Entity
@Table(name = "TASKS_CONTROLLERS")
public class TaskController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Codigo del controlador
     */
    @Id
    @GeneratedValue(generator = "task_controller_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "task_controller_seq", sequenceName = "task_controller_seq", allocationSize = 1)
    @Column(name = "task_controller_id", columnDefinition = "int4")
    private Integer taskControllerId;

    /**
     * Nombre del controlador
     */
    @Basic
    @Column(name = "name", columnDefinition = "varchar(50)")
    private String name;

    /**
     * Descripcion del controlador
     */
    @Basic
    @Column(name = "description", columnDefinition = "varchar(150)")
    private String description;

    /**
     * Modulo del sistema al que pertenece
     */
    @Basic
    @Column(name = "module_system_id", columnDefinition = "int4")
    @NotNull(message = "El campo no puede ser nulo.")
    private Integer moduleSystemId;

    /**
     * Estado activo o inactivo
     */
    @Basic
    @Column(name = "status", columnDefinition = "boolean")
    private Boolean status;
    @Transient
    private ModuleSystem moduleSystem;

}