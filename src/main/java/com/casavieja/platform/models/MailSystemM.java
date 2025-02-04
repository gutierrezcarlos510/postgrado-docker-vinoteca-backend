/**
 * 
 */
package com.casavieja.platform.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.casavieja.pagination.DataTableRow;

/**
 * @author CARLOS
 *
 */
@Entity
public class MailSystemM extends DataTableRow implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private Long mailSystemId;
	private String toUser;
	private String fromUser;
	private String subject;
	private String message;
	private String typeMail;
	private Boolean isMailSystem;
	private Long systemUserId;
	private Boolean status;
	private Long mailSystemIdResponse;
	@Transient
	private String messageResponse;
	public String getMessageResponse() {
		return messageResponse;
	}
	public void setMessageResponse(String messageResponse) {
		this.messageResponse = messageResponse;
	}
	public Long getMailSystemIdResponse() {
		return mailSystemIdResponse;
	}
	public void setMailSystemIdResponse(Long mailSystemIdResponse) {
		this.mailSystemIdResponse = mailSystemIdResponse;
	}
	public Long getMailSystemId() {
		return mailSystemId;
	}
	public void setMailSystemId(Long mailSystemId) {
		this.mailSystemId = mailSystemId;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTypeMail() {
		return typeMail;
	}
	public void setTypeMail(String typeMail) {
		this.typeMail = typeMail;
	}
	public Boolean getIsMailSystem() {
		return isMailSystem;
	}
	public void setIsMailSystem(Boolean isMailSystem) {
		this.isMailSystem = isMailSystem;
	}
	public Long getSystemUserId() {
		return systemUserId;
	}
	public void setSystemUserId(Long systemUserId) {
		this.systemUserId = systemUserId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
