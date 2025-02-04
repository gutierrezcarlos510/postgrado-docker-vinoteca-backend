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

/**
 * Tareas o metodos del controlador
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
@Table(name = "TASKS")
public class Task implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Codigo identificador
     */
    @Id
    @GeneratedValue(generator = "task_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    @Column(name = "task_id", columnDefinition = "int4")
    private Integer taskId;

    /**
     * Nombre de la tarea
     */
    @Basic
    @Column(name = "name", columnDefinition = "varchar(50)")
    private String name;

    /**
     * Descripcion breve de la tarea
     */
    @Basic
    @Column(name = "description", columnDefinition = "varchar(150)")
    private String description;

    /**
     * Descripcion de la direccion a la vista
     */
    @Basic
    @Column(name = "url", columnDefinition = "varchar(50)")
    private String url;

    /**
     * Codigo identificador del grupo de controladores de tareas al que
     * pertenece
     */
    @Basic
    @Column(name = "task_controller_id", columnDefinition = "int4")
    private Integer taskControllerId;

    /**
     * Estado activo o inactivo
     */
    @Basic
    @Column(name = "status", columnDefinition = "boolean")
    private Boolean status;
    @Transient
    private TaskController taskController;
}