/**
 * 
 */
package com.casavieja.platform.enums;

/**
 * @author CARLOS
 *
 */
public enum TypeMailEnum {
	PENDIENTE("P"),
	LEIDO("L"),
	RESPONDIDO("R"),
	ENVIADO("E"),
	RESPONDIDO_PENDIENTE("S");
	
	public final String value;
	private TypeMailEnum(String value) {
		this.value = value;
	}
}
