package com.innobiz.orca.appconfig.entity;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted = 'false'")
public class UOMCategory extends Auditable {
	
	private static final long serialVersionUID = 2L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String code;
	private String name;

}
