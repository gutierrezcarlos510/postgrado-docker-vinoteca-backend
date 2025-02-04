package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author CARLOS
 */
@Getter
@Setter
@Entity
@Table(name = "ROL_TASK")
@IdClass(RolTaskPK.class)
public class RolTask implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(name = "rol_id", columnDefinition = "int4")
    private Integer rolId;

    @Id
    @Column(name = "task_id", columnDefinition = "int4")
    private Integer taskId;

}