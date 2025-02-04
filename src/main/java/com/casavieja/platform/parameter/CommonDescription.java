/**
 * 
 */
package com.casavieja.platform.parameter;

import java.io.Serializable;

/**
 * @author CARLOS
 * Objeto para el parametro comun que de descripcion
 */
public class CommonDescription implements Serializable{
	private static final long serialVersionUID = 1L;
	private String descripcion;
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
