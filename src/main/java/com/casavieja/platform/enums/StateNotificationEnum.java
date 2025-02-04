/**
 * 
 */
package com.casavieja.platform.enums;

/**
 * @author CARLOS
 *
 */
public enum StateNotificationEnum {
	PENDIENTE("P"),
	LEIDO("L"),
	ARCHIVADO("A"),
	ELIMINADO("E");
	public final String value;
	private StateNotificationEnum(String value) {
		this.value=value;
	}
}
