package com.casavieja.platform.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RolM implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    private Integer rolId;
    private String name;
    private String description;
    private String icon;
    private Boolean status;
	public Integer getRolId() {
		return rolId;
	}
	public void setRolId(Integer rolId) {
		this.rolId = rolId;
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
