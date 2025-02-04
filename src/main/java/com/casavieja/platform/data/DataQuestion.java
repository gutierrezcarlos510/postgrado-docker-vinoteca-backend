/**
 * 
 */
package com.casavieja.platform.data;

import java.io.Serializable;

/**
 * @author CARLOS
 *
 */
public class DataQuestion implements Serializable{
	private static final long serialVersionUID = 1L;
	private String fullnameQuestion;
	private String emailQuestion;
	private String descriptionQuestion;
	public String getFullnameQuestion() {
		return fullnameQuestion;
	}
	public void setFullnameQuestion(String fullnameQuestion) {
		this.fullnameQuestion = fullnameQuestion;
	}
	public String getEmailQuestion() {
		return emailQuestion;
	}
	public void setEmailQuestion(String emailQuestion) {
		this.emailQuestion = emailQuestion;
	}
	public String getDescriptionQuestion() {
		return descriptionQuestion;
	}
	public void setDescriptionQuestion(String descriptionQuestion) {
		this.descriptionQuestion = descriptionQuestion;
	}
}
