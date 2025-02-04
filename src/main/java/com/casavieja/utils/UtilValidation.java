package com.casavieja.utils;

import java.util.List;

/**
 * Utilitario de validacion general
 * @author CARLOS
 *
 */
public class UtilValidation {
	/**
	 * Devuelve verdadero si existe datos de una lista enviada como parametro
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> boolean  exist(List<T> list) {
		return list != null && !list.isEmpty();
	}
	/**
	 * Devuelve verdadero si un objeto existe o es diferente de nulo
	 * @param <T>
	 * @param obj
	 * @return
	 */
	public static <T> boolean  exist(T obj) {
		return obj != null;
	}
}
