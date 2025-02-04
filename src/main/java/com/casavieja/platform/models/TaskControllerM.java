package com.casavieja.platform.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.casavieja.platform.entities.ModuleSystem;
import com.casavieja.pagination.DataTableRow;

/**
 * Modelo que agrupa todos los controladores del sistema
 *
 * @author CARLOS
 */
@Entity
public class TaskControllerM extends DataTableRow implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    private Integer taskControllerId;
    private String name;
    private String description;
    private Integer moduleSystemId;
    private Boolean status;
    @Transient
    private ModuleSystem moduleSystem;

    public ModuleSystem getModuleSystem() {
		return moduleSystem;
	}

	public void setModuleSystem(ModuleSystem moduleSystem) {
		this.moduleSystem = moduleSystem;
	}

	public Boolean getStatus() {
		return status;
	}

	public Integer getTaskControllerId() {
        return taskControllerId;
    }

    public void setTaskControllerId(Integer taskControllerId) {
        this.taskControllerId = taskControllerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getModuleSystemId() {
        return moduleSystemId;
    }

    public void setModuleSystemId(Integer moduleSystemId) {
        this.moduleSystemId = moduleSystemId;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskController{" + " taskControllerId=" + taskControllerId + ", name=" + name + ", description=" + description + ", status=" + status + '}';
    }

}