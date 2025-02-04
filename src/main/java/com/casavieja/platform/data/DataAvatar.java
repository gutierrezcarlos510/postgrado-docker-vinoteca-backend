package com.casavieja.platform.data;

import java.io.Serializable;

public class DataAvatar implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long systemUserId;
	private Integer avatarId;
	public Long getSystemUserId() {
		return systemUserId;
	}
	public void setSystemUserId(Long systemUserId) {
		this.systemUserId = systemUserId;
	}
	public Integer getAvatarId() {
		return avatarId;
	}
	public void setAvatarId(Integer avatarId) {
		this.avatarId = avatarId;
	}
	@Override
	public String toString() {
		return "DataAvatar [systemUserId=" + systemUserId + ", avatarId=" + avatarId + "]";
	}
}
