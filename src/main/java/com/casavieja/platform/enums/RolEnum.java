/**
 * 
 */
package com.casavieja.platform.enums;

/**
 * @author CARLOS
 *
 */
public enum RolEnum {
	SUPER_ADMIN(1),
	ADMIN(2),
	CLIENTE_DEMO(3);
	public final Integer value;
	private  RolEnum(Integer value) {
		this.value = value;
	}
}
