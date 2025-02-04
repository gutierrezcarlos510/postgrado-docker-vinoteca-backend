/**
 * 
 */
package com.casavieja.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author CARLOS
 * Utilitario para fechas
 */
public class UtilDate {
	public LocalDate getDateNow(String pattern) {
		LocalDate now = LocalDate.now();
		return now;
	}
	public String getDateNowString(String pattern) {
		if(pattern!=null) {
			if(pattern.isEmpty()) {
				pattern = MyConstants.DATE;
			}
		}else
			pattern = MyConstants.DATE;
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return now.format(formatter);
	}
	public int getYearNow() {
		return LocalDate.now().getYear();
	}
}
