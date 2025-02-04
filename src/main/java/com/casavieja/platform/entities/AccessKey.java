package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Tabla que guarda todas las claves de acceso al sistema
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
@Table(name = "ACCESS_KEYS")
public class AccessKey implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "access_key_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "access_key_seq", sequenceName = "access_key_seq", allocationSize = 1)
    @Column(name = "id", columnDefinition = "int8")
    private Long id;

    /**
     * Tipo de acceso: USER_PASS:usuario y clave, BIOMETRIC biometrico, EMAIL
     * gmail
     */
    @Basic
    @Column(name = "type_access", columnDefinition = "varchar(15)")
    private String typeAccess;
    @Basic
    @Column(name = "value_access", columnDefinition = "varchar(1024)")
    private String valueAccess;
    @Basic
    @Column(name = "system_user_id", columnDefinition = "int8")
    private Long systemUserId;
    @Basic
    @Column(name = "status", columnDefinition = "boolean")
    private Boolean status;
    @Column(name = "is_verified_code")
    private Boolean isVerifiedCode;
    @Column(name = "code_verification")
    private String codeVerification;
}