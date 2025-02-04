/**
 * 
 */
package com.casavieja.platform.enums;

/**
 * @author CARLOS
 *
 */
public enum TypeValidationEnum {
	IS_NULL(1),
	IS_NOT_NULL(2),
	IS_NOT_EMPTY(3),
	IS_EMPTY(4),
	IS_NOT_LENGTH(5),
	IS_NOT_RANGE(6),
	WHEN(7);
	public final Integer value;
	private TypeValidationEnum(Integer value) {
		this.value = value;
	}
}
