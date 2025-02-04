/**
 * 
 */
package com.casavieja.platform.enums;

/**
 * @author CARLOS
 *
 */
public enum ParameterEnum {
	TYPE_COMPANY("TYPE_COMPANY"),
	TYPE_NOTIFICATION("TYPE_NOTIFICATION"),
	TEMPLATE_NOTIFICATION("TEMPLATE_NOTIFICATION");
	public final String value;
	private ParameterEnum(String value) {
		this.value=value;
	}
}
