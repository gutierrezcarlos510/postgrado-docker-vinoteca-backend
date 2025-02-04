/**
 * 
 */
package com.casavieja.platform.data;

import java.io.Serializable;

/**
 * @author CARLOS
 *
 */
public class DataSessionNew implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer rolId;
	private Integer managementId;
	public Integer getRolId() {
		return rolId;
	}
	public void setRolId(Integer rolId) {
		this.rolId = rolId;
	}
	public Integer getManagementId() {
		return managementId;
	}
	public void setManagementId(Integer managementId) {
		this.managementId = managementId;
	}
	@Override
	public String toString() {
		return "DataSessionNew [rolId=" + rolId + ", managementId=" + managementId + "]";
	}
}
