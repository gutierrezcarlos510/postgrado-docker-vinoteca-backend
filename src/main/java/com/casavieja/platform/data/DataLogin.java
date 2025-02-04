package com.casavieja.platform.data;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class DataLogin implements Serializable{

	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "El login no puede estar vacia")
	private String username;
	@NotEmpty(message = "La contraseña no puede estar vacia")
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "DataLogin [login=" + username + ", password=" + password + "]";
	}
}
