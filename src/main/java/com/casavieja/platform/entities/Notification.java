package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "notification_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 1)
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