/**
 * 
 */
package com.casavieja.platform.data;

import java.io.Serializable;

/**
 * @author CARLOS
 *
 */
public class DataOtherValue implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "DataOtherValue [name=" + name + "]";
	}
}
