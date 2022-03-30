package com.innobiz.orca.appconfig.entity;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted = 'false'")
public class UOM extends Auditable {
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String uomCode;
	private String uomName;
	@Enumerated
	private UOMType uomType;
	private Integer roundPrescition;
	@ManyToOne
	@JoinColumn(name = "uomCategory", referencedColumnName = "id")
	private UOMCategory uomCategory;


}