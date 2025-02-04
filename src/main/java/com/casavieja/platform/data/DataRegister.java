package com.casavieja.platform.data;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class DataRegister implements Serializable{

	private static final long serialVersionUID = 535362079375178183L;
	@NotEmpty
	@Size(min = 2, max = 50)
	private String name;
	@NotEmpty
	@Size(min = 2, max = 50)
	private String firstLastname;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	@Size(min = 2, max = 1024)
	private String valueAccess;
	@NotEmpty
	@Size(min = 2, max = 1024)
	private String valueAccess2;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstLastname() {
		return firstLastname;
	}
	public void setFirstLastname(String firstLastname) {
		this.firstLastname = firstLastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getValueAccess() {
		return valueAccess;
	}
	public void setValueAccess(String valueAccess) {
		this.valueAccess = valueAccess;
	}
	public String getValueAccess2() {
		return valueAccess2;
	}
	public void setValueAccess2(String valueAccess2) {
		this.valueAccess2 = valueAccess2;
	}
}
