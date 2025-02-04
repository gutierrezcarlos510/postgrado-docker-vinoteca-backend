/**
 * 
 */
package com.casavieja.platform.enums;

/**
 * @author CARLOS
 *
 */
public enum TypeUserEnum {
	ROOT("ROOT"),
	ADMIN("ADMIN"),
	USER("USER"),
	CLIENT("CLIENT");
	public final String value;
	private TypeUserEnum(String value) {
		this.value = value;
	}
}
