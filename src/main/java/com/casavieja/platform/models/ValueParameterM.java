package com.casavieja.platform.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.casavieja.pagination.DataTableRow;

/**
 * Valor del parametro
 *
 * @author CARLOS GUTIERREZ
 */
@Entity
public class ValueParameterM extends DataTableRow implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    private Long valueParameterId;
    private String name;
    private Boolean status;
    private Long parameterSystemId;
    private String otherValue;
	public Long getValueParameterId() {
		return valueParameterId;
	}
	public void setValueParameterId(Long valueParameterId) {
		this.valueParameterId = valueParameterId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Long getParameterSystemId() {
		return parameterSystemId;
	}
	public void setParameterSystemId(Long parameterSystemId) {
		this.parameterSystemId = parameterSystemId;
	}
	public String getOtherValue() {
		return otherValue;
	}
	public void setOtherValue(String otherValue) {
		this.otherValue = otherValue;
	}
}