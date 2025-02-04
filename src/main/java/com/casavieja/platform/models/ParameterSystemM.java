package com.casavieja.platform.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.casavieja.platform.entities.ModuleSystem;
import com.casavieja.pagination.DataTableRow;

/**
 * Tabla que guarda los parametros del sistema
 *
 * @author CARLOS GUTIERREZ
 */
@Entity
public class ParameterSystemM extends DataTableRow implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    private Long parameterSystemId;
    private String name;
    private String description;
    private Integer moduleSystemId;
    private Boolean status;
    @Transient
    private ModuleSystem moduleSystem;
    private String other;
    public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public ModuleSystem getModuleSystem() {
		return moduleSystem;
	}

	public void setModuleSystem(ModuleSystem moduleSystem) {
		this.moduleSystem = moduleSystem;
	}

	public Long getParameterSystemId() {
		return parameterSystemId;
	}

	public void setParameterSystemId(Long parameterSystemId) {
		this.parameterSystemId = parameterSystemId;
	}

	public Boolean getStatus() {
		return status;
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
        return "Parameter{" + " parameterId=" + parameterSystemId + ", name=" + name + ", description=" + description + ", moduleSystemId=" + moduleSystemId + ", status=" + status + '}';
    }

}