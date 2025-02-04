/**
 * 
 */
package com.casavieja.platform.enums;

/**
 * @author CARLOS
 *
 */
public enum TypeAccessEnum {
	USER_PASS("USER_PASS"),
	CELULAR("CELULAR"),
	GMAIL("GMAIL");
	public final String value;
	private TypeAccessEnum(String value) {
		this.value=value;
	}
}
