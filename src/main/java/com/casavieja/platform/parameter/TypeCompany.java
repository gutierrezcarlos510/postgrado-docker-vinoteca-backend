/**
 * 
 */
package com.casavieja.platform.parameter;

import java.io.Serializable;

/**
 * @author CARLOS
 * Objeto para el parametro tipo de empresa
 */
public class TypeCompany implements Serializable{
	private static final long serialVersionUID = 1L;
	private String sigla;
	private String descripcion;
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
