package com.innobiz.orca.appconfig.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable implements Serializable {
	private static final long serialVersionUID = 2L;

	@CreatedBy
	private String createdBy;

	@LastModifiedBy
	private String lastModifiedBy;

	@CreatedDate
	@Column(name = "createdAt")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@LastModifiedDate
	@Column(name = "updatedAt")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(name = "isDeleted", columnDefinition = "Boolean default false")
	private Boolean isDeleted = false;

}