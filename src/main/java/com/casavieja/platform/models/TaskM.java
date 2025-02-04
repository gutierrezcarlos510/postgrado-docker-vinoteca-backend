package com.casavieja.platform.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.casavieja.platform.entities.TaskController;
import com.casavieja.pagination.DataTableRow;
import lombok.Getter;
import lombok.Setter;

/**
 * Modelo de tareas o metodos del controlador
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
public class TaskM extends DataTableRow implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private String name;
    private String description;
    private String url;
    private Integer taskControllerId;
    private Boolean status;
    @Transient
    private TaskController taskController;


}