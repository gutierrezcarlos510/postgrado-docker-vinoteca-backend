/**
 * 
 */
package com.casavieja.platform.enums;

/**
 * @author CARLOS
 *
 */
public enum TypeOperationManagementEnum {
	READONLY("READONLY"),
	READ_WRITE("READ_WRITE");
	public final String value;
	private TypeOperationManagementEnum(String value) {
		this.value = value;
	}
}
