package com.casavieja.platform.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.casavieja.pagination.DataTableRow;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "notifications")
public class NotificationM extends DataTableRow implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    private Long id;
    private String title;
    private String description;
    private String url;
    private String paramTypeNotification;
    private String statusNotification;
    @Temporal(TemporalType.DATE)
    private Date dateRegister;
    private Boolean status;
    private Long systemUserId;
}